"""Latency benchmark for GET /products/{id} with and without the Redis cache.

Prerequisites (see benchmark/README.md):
  * user-service on USER_URL and product-service on PRODUCT_URL
  * a seller account (SELLER_USER / SELLER_PASSWORD) -- created by `seed`

Usage:
  python bench_product_cache.py seed      # create seller + products, write ids.json
  python bench_product_cache.py run LABEL # benchmark, append results to results.json
"""
import http.client
import json
import os
import random
import statistics
import sys
import time
from urllib.parse import urlparse

USER_URL = os.environ.get("USER_URL", "http://localhost:9000")
PRODUCT_URL = os.environ.get("PRODUCT_URL", "http://localhost:3000")
SELLER_USER = os.environ.get("SELLER_USER", "seller1")
SELLER_PASSWORD = os.environ.get("SELLER_PASSWORD", "seller1pass")
NUM_PRODUCTS = int(os.environ.get("NUM_PRODUCTS", "100"))
WARMUP = int(os.environ.get("WARMUP", "200"))
REQUESTS = int(os.environ.get("REQUESTS", "2000"))
HERE = os.path.dirname(os.path.abspath(__file__))


def connect(base):
    u = urlparse(base)
    return http.client.HTTPConnection(u.hostname, u.port, timeout=30)


def call(conn, method, path, body=None, headers=None):
    headers = dict(headers or {})
    payload = None
    if body is not None:
        payload = json.dumps(body)
        headers["Content-Type"] = "application/json"
    try:
        conn.request(method, path, body=payload, headers=headers)
        resp = conn.getresponse()
    except (ConnectionError, http.client.HTTPException):
        # server closed an idle keep-alive connection; reconnect once
        conn.close()
        conn.request(method, path, body=payload, headers=headers)
        resp = conn.getresponse()
    data = resp.read()
    return resp, data


def login(conn):
    resp, _ = call(conn, "POST", "/auth/login",
                   {"userName": SELLER_USER, "password": SELLER_PASSWORD})
    cookie = resp.getheader("Set-Cookie")  # "auth-token:<jwt>"
    if resp.status != 200 or not cookie:
        sys.exit(f"login failed: {resp.status}")
    return cookie.split("auth-token:", 1)[1]


def seed():
    users = connect(USER_URL)
    products = connect(PRODUCT_URL)
    token = login(users)
    categories = ["Electronics", "Books", "Clothing", "Home", "Sports"]
    ids = []
    for i in range(NUM_PRODUCTS):
        resp, data = call(products, "POST", "/products", {
            "title": f"Benchmark product {i}",
            "description": f"Sample product {i} used for latency benchmarking",
            "image": "https://example.com/img.png",
            "category": categories[i % len(categories)],
            "price": round(random.uniform(100, 5000), 2),
            "currency": "INR",
        }, {"Authorization": token})
        if resp.status != 200:
            sys.exit(f"create product failed: {resp.status} {data[:200]}")
        ids.append(json.loads(data)["id"])
    with open(os.path.join(HERE, "ids.json"), "w") as f:
        json.dump(ids, f)
    print(f"seeded {len(ids)} products")


def percentile(sorted_vals, p):
    k = (len(sorted_vals) - 1) * p / 100
    lo, hi = int(k), min(int(k) + 1, len(sorted_vals) - 1)
    return sorted_vals[lo] + (sorted_vals[hi] - sorted_vals[lo]) * (k - lo)


def measure(fn, n):
    samples = []
    for _ in range(n):
        start = time.perf_counter()
        fn()
        samples.append((time.perf_counter() - start) * 1000)
    s = sorted(samples)
    return {
        "requests": n,
        "mean_ms": round(statistics.mean(s), 2),
        "p50_ms": round(percentile(s, 50), 2),
        "p95_ms": round(percentile(s, 95), 2),
        "p99_ms": round(percentile(s, 99), 2),
        "throughput_rps": round(n / (sum(s) / 1000), 1),
    }


def run(label):
    with open(os.path.join(HERE, "ids.json")) as f:
        ids = json.load(f)
    users = connect(USER_URL)
    products = connect(PRODUCT_URL)
    token = login(users)
    rng = random.Random(42)

    def get_product():
        resp, data = call(products, "GET", f"/products/{rng.choice(ids)}",
                          headers={"Authorization": token})
        if resp.status != 200:
            sys.exit(f"GET failed: {resp.status} {data[:200]}")

    def validate_only():
        resp, _ = call(users, "POST", "/auth/validate", {"token": token})
        if resp.status != 200:
            sys.exit(f"validate failed: {resp.status}")

    measure(get_product, WARMUP)
    result = {
        "label": label,
        "get_product": measure(get_product, REQUESTS),
        "token_validation_only": measure(validate_only, REQUESTS),
    }
    path = os.path.join(HERE, "results.json")
    results = json.load(open(path)) if os.path.exists(path) else []
    results = [r for r in results if r["label"] != label] + [result]
    with open(path, "w") as f:
        json.dump(results, f, indent=2)
    print(json.dumps(result, indent=2))


if __name__ == "__main__":
    if len(sys.argv) < 2 or sys.argv[1] not in ("seed", "run"):
        sys.exit(__doc__)
    seed() if sys.argv[1] == "seed" else run(sys.argv[2] if len(sys.argv) > 2 else "run")
