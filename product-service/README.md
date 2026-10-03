# product-service

Product and category catalogue for the e-commerce backend (port **3000**).

## Endpoints

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/products` | – | List all products |
| GET | `/products/{id}` | token (active session) | Get a product by UUID (Redis cache-aside) |
| POST | `/products` | token, `Seller` role | Create a product |
| PUT | `/products/{id}` | token, `Seller`, creator only | Update a product (evicts cache) |
| DELETE | `/products/{id}` | token, creator only | Delete a product (evicts cache) |
| GET | `/categories` | – | List categories |
| GET | `/categories/{id}` | – | Products in a category |
| POST | `/search` | – | `{"searchText"}` — title contains text (case-insensitive) |
| POST | `/search/paged` | – | `{"searchText","pageNumber","pageSize"}` — paginated, ordered by title |

The token is the JWT returned by user-service login, sent raw in the
`Authorization` header. It is validated on every protected call via
`POST {USER_SERVICE_URL}/auth/validate` (`security/TokenValidator`).

Product body:

```json
{"title":"Poco F3","description":"Phone","image":"https://...","category":"Phones","price":24999,"currency":"INR"}
```

## Design notes

* `ProductService` has two implementations: `SelfProductServiceImpl` (MySQL via
  JPA, used by the controllers) and `FakeStoreProductService` (proxy to
  fakestoreapi.com, with its own Redis cache) — chosen with `@Qualifier`.
* Entities share a `BaseModel` with a UUID primary key stored as `binary(16)`;
  `Product` → `Category` is many-to-one.
* `inheritancedemo/` contains the class exercises on JPA inheritance strategies
  (single table, joined, table-per-class, mapped superclass).

## Configuration

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `REDIS_HOST`, `REDIS_PORT`,
`USER_SERVICE_URL`, `PRODUCT_CACHE_ENABLED` — see the root README.

```bash
./mvnw spring-boot:run
```
