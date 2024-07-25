# Carbon Webhooks Python Library

`carbon_webhooks_python` is a Python library designed to verify Carbon webhook events. This library provides a simple way to validate webhook signatures and ensure the authenticity of incoming requests.

## Features

- **Generate Signature**: Generate HMAC SHA256 signatures for webhook payloads.
- **Validate Signature**: Validate incoming webhook signatures to ensure they match the expected signature.
- **Extract Signature Header**: Parse and extract components from the Carbon-Signature header.

## Installation

You can install the library using pip:

```sh
pip install carbon_webhooks
