# payment-service

Generates payment links and tracks payment status (port **8080**).

## Endpoints

| Method | Path | Description |
|---|---|---|
| POST | `/payments/` | `{"orderId","productName","name","email","phoneNo","amount"}` → payment link URL |
| GET | `/payments/thankyou` | Redirect target after payment |
| POST | `/stripepaymenthook/` | Stripe webhook; when the `Stripe-Signature` header is present it is verified with `STRIPE_ENDPOINT_SECRET` |

`amount` is in the smallest currency unit (paise).

## Design notes

* `PaymentGateway` interface with `StripePaymentGateway` and
  `RazorpayPaymentGateway`; `PaymentGatewayChooserStrategy` selects one
  (currently Stripe).
* Each step is recorded as a `PaymentEvent` (`orderId`, `stripePaymentId`,
  `status`) with statuses `PAYMENT_LINK_REQUESTED`, `PAYMENT_LINK_GENERATED`,
  `PAYMENT_SUCCEEDED`, `PAYMENT_FAILED`, …
* The webhook updates the event for the payment link when Stripe reports
  completion.

## Configuration

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `STRIPE_KEY_ID`, `STRIPE_KEY_SECRET`,
`STRIPE_ENDPOINT_SECRET`, `RAZORPAY_KEY_ID`, `RAZORPAY_KEY_SECRET`.
Use Stripe **test-mode** keys locally; forward webhooks with
`stripe listen --forward-to localhost:8080/stripepaymenthook/`.

```bash
./mvnw spring-boot:run
```
