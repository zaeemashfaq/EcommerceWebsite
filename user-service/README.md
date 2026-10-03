# user-service

Authentication, sessions and role-based access control (port **9000**).

## Endpoints

| Method | Path | Body | Description |
|---|---|---|---|
| POST | `/auth/signup` | `{"userName","password"}` | Create a user with the `NormalUser` role (password BCrypt-hashed) |
| POST | `/auth/login` | `{"userName","password"}` | Returns the user; JWT in `Set-Cookie: auth-token:<jwt>` |
| POST | `/auth/logout` | `{"token","id"}` | Marks the session `ENDED` |
| POST | `/auth/validate` | `{"token"}` | Returns `{userId,userName,roles,status}` — used by other services |
| POST | `/roles` | `{"role"}` + `Authorization` (Admin) | Create a role |
| POST | `/users/{id}` | – | Get user details |
| POST | `/users/roles/{id}` | `{"role"}` + `Authorization` (Admin) | Add a role to a user |

## Design notes

* Login signs a JWT (HS256, `JWT_SECRET`) with user id, user name, roles and
  expiry (3 days) and stores it in the `session` table with status `ACTIVE`.
* Validation checks the stored session (so logout is effective immediately),
  verifies the signature and expires sessions past `expiryAt`.
* Tables: `user`, `role`, `user_roles` (many-to-many), `session`.
* Roles must be seeded once (`Admin`, `Seller`, `NormalUser`) — see root README.

## Configuration

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `PORT`.

```bash
./mvnw spring-boot:run
```
