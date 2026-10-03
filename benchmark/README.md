# Product cache benchmark

Measures `GET /products/{id}` latency with the Redis cache-aside layer
disabled vs enabled (`PRODUCT_CACHE_ENABLED`), plus the cost of the
token-validation call to user-service that every request makes.

## Steps

1. `docker compose up -d mysql redis` (from the repo root)
2. Start user-service, then seed roles and a seller (one-time):
   ```sql
   -- in the userservice schema
   INSERT INTO role(role) VALUES ('Admin'),('Seller'),('NormalUser');
   ```
   ```bash
   curl -X POST localhost:9000/auth/signup -H 'Content-Type: application/json' \
        -d '{"userName":"seller1","password":"seller1pass"}'
   ```
   ```sql
   INSERT INTO user_roles(user_id, roles_id)
   SELECT u.id, r.id FROM user u, role r
   WHERE u.user_name='seller1' AND r.role IN ('Seller','Admin');
   ```
3. Start product-service with `PRODUCT_CACHE_ENABLED=false`, then
   `python bench_product_cache.py seed` and `python bench_product_cache.py run cache_disabled`
4. Restart product-service with `PRODUCT_CACHE_ENABLED=true`, then
   `python bench_product_cache.py run cache_enabled`

Results are written to `results.json`.

## Results (2026-10-03)

Single sequential client with HTTP keep-alive, 200 warm-up + 2000 measured
requests over 100 products. Windows 10 laptop, Java 17, MySQL 8.0 and Redis 7
in Docker.

| GET /products/{id}  | Cache disabled | Cache enabled | Change |
|---------------------|---------------:|--------------:|-------:|
| Mean                | 26.11 ms       | 17.06 ms      | -35%   |
| p50                 | 25.17 ms       | 14.44 ms      | -43%   |
| p95                 | 41.40 ms       | 28.04 ms      | -32%   |
| p99                 | 55.48 ms       | 40.37 ms      | -27%   |
| Throughput          | 38.3 req/s     | 58.6 req/s    | +53%   |

Token validation alone (user-service `/auth/validate`) takes ~9-10 ms in
both runs, so product-service's own share of the request drops from
~17 ms to ~7 ms. Redis reported 2199 keyspace hits vs 3 misses during the
cached run.
