# notification-service

Accepts notification requests from other services, stores them with a
delivery status and delivers SMS through Twilio (port **4000**).

## Endpoints

| Method | Path | Description |
|---|---|---|
| POST | `/notifications/send` | `{"requestingServiceId","requestingServiceNotificationId","userId","messageTitle","messageContent","notificationChannel":"SMS|EMAIL|PUSH"}` |
| POST | `/notifications/fetch/user/{id}` | Notification history for a user, in chronological order |
| POST | `/user` | Create a (mock) recipient with mobile number / email / device id |

## Design notes

* Tables (Flyway `V1__init.sql`): `notification`, `message`, `mock_user`.
* `Notification` records the requesting service, channel and `DeliveryStatus`
  (`PENDING`, `DELIVERED`, `FAILED`, `UNDELIVERABLE`).
* Only SMS is implemented; EMAIL and PUSH return "coming soon".
* Twilio account SID, auth token and sender number are read from the console
  at send time (development setup) — move to environment variables before
  deploying.

```bash
./mvnw spring-boot:run
```
