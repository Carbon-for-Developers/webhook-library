import hmac
import hashlib
from typing import Any

class WebhookVerifier:
    def __init__(self, signing_key: str):
        self.signing_key = signing_key

    def generate_signature(self, timestamp: str, payload: str) -> str:
        """
        Generate a HMAC SHA-256 signature using the signing key, timestamp, and payload.
        """
        msg = f'{timestamp}.{payload}'
        signature = hmac.new(bytes.fromhex(self.signing_key), msg.encode('utf-8'), hashlib.sha256).hexdigest()
        return signature

    def validate_signature(self, received_sig: str, timestamp: str, payload: str) -> bool:
        """
        Validate the received signature against the expected signature.
        """
        expected_signature = self.generate_signature(timestamp, payload)
        return hmac.compare_digest(received_sig, expected_signature)

    @staticmethod
    def extract_signature_header(header: str) -> Any:
        """
        Extract the timestamp and signature from the Carbon-Signature header.
        """
        try:
            if header.startswith('Carbon-Signature:'):
                header = header[len('Carbon-Signature:'):]
            parts = dict(x.split('=') for x in header.split(','))
            timestamp = parts.get('t')
            received_signature = parts.get('v1')
            return timestamp, received_signature
        except Exception as e:
            raise ValueError("Invalid Carbon-Signature header format") from e