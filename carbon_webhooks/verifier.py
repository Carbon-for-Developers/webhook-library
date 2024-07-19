import hmac
import hashlib
from typing import Any

class WebhookVerifier:
    def __init__(self, signing_key: str):
        self.signing_key = signing_key
    
    def generate_signature(self, timestamp: str, json_payload: str) -> str:
        msg = f"{timestamp}.{json_payload}".encode("utf-8")
        return hmac.new(
            key=bytes.fromhex(self.signing_key), 
            msg=msg, 
            digestmod=hashlib.sha256
        ).hexdigest()

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
                header = header[len('Carbon-Signature:'):].strip()
            parts = dict(x.strip().split('=') for x in header.split(','))
            timestamp = parts.get('t').strip()
            received_signature = parts.get('v1').strip()
            print()
            print(f"timestamp:{timestamp}")
            print()
            print(f"Carbon-Signature:{received_signature}")
            print()
            return timestamp, received_signature
        except Exception as e:
            raise ValueError("Invalid Carbon-Signature header format") from e