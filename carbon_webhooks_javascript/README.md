# Carbon Webhooks JavaScript Library

A library to verify Carbon webhook events in JavaScript.

## Installation

You can install this library via npm. Make sure you have [Node.js](https://nodejs.org/) and npm installed on your machine.

```bash
npm install carbon-webhooks-javascript
```

### `WebhookVerifier`

#### `new WebhookVerifier(signingKey)`

*   `signingKey`: Your Carbon webhook signing key.

#### `generateSignature(timestamp, jsonPayload)`

Generates a signature for the given timestamp and JSON payload.

*   `timestamp`: The timestamp of the webhook event.
*   `jsonPayload`: The JSON payload of the webhook event.

Returns the generated signature.

#### `validateSignature(receivedSig, timestamp, payload)`

Validates the received signature against the generated signature.

*   `receivedSig`: The received signature to validate.
*   `timestamp`: The timestamp of the webhook event.
*   `payload`: The JSON payload of the webhook event.

Returns `true` if the signature is valid, otherwise `false`.

#### `extractSignatureHeader(header)`

Extracts the signature parts from the Carbon-Signature header.

*   `header`: The Carbon-Signature header.

Returns an object with the extracted signature parts.
