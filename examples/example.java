package com.carbon.webhooks;

import java.util.Map;

public class Example {
    public static void main(String[] args) {
        try {
            String SIGNING_SECRET = "your_signing_secret";  // Replace with your actual signing key
            WebhookVerifier verifier = new WebhookVerifier(SIGNING_SECRET);

            String carbonSignature = "t=1721392406,v1=aa2273ab64bb9162e7e7983a9cd7ab9f90d686691b1fd25c577991ad42c53fc1";
            String payload = "{\"payload\":\"{\\\"webhook_type\\\": \\\"FILES_CREATED\\\", \\\"obj\\\": {\\\"object_type\\\": \\\"FILE_LIST\\\", \\\"object_id\\\": [\\\"46654\\\"], \\\"additional_information\\\": \\\"null\\\"}, \\\"customer_id\\\": \\\"satvik\\\", \\\"timestamp\\\": \\\"1721392406\\\"}\"}";

            Map<String, String> extractedHeader = WebhookVerifier.extractSignatureHeader(carbonSignature);
            String timestamp = extractedHeader.get("t");
            String receivedSignature = extractedHeader.get("v1");

            if (!verifier.validateSignature(receivedSignature, timestamp, payload)) {
                System.out.println("Invalid signature");
            } else {
                System.out.println("Valid signature");
                // Handle the event
                Map<String, Object> data = new ObjectMapper().readValue(payload, Map.class);
                String eventType = data.get("webhook_type").toString();
                if ("FILES_CREATED".equals(eventType)) {
                    // Process the event
                    System.out.println("Processing FILES_CREATED event");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
