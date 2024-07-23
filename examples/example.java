import java.util.Map;
import com.carbon.webhooks.WebhookVerifier;

public class Example {
    public static void main(String[] args) {
        try {
            String SIGNING_SECRET = "aa76aee859f223451fd9bfb37ce893a0";  // Replace with your actual signing key
            WebhookVerifier verifier = new WebhookVerifier(SIGNING_SECRET);

            Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "Carbon-Signature", "t=1721392406,v1=aa2273ab64bb9162e7e7983a9cd7ab9f90d686691b1fd25c577991ad42c53fc1",
                "Carbon-Signature-Compact", "t=1721392406,v2=42a86d4083fee090b5a0800a91e82fb389f0bed4da757d07ee8ba97485194e59"
            );

            String carbonSignature = headers.get("Carbon-Signature");
            String payload_v1 = "{\"payload\": \"{\\\"webhook_type\\\": \\\"FILES_CREATED\\\", \\\"obj\\\": {\\\"object_type\\\": \\\"FILE_LIST\\\", \\\"object_id\\\": [\\\"46654\\\"], \\\"additional_information\\\": \\\"null\\\"}, \\\"customer_id\\\": \\\"satvik\\\", \\\"timestamp\\\": \\\"1721392406\\\"}\"}";

            Map<String, String> headerMap = WebhookVerifier.extractSignatureHeader(carbonSignature);
            String timestamp = headerMap.get("t");
            String receivedSignature = headerMap.get("v1");

            if (verifier.validateSignature(receivedSignature, timestamp, payload_v1)) {
                System.out.println("Signature is valid");
            } else {
                System.out.println("Signature is invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
