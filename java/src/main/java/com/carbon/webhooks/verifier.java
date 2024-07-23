package com.carbon.webhooks;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class WebhookVerifier {
    private final String signingKey;

    public WebhookVerifier(String signingKey) {
        this.signingKey = signingKey;
    }

    public String generateSignature(String timestamp, String jsonPayload) throws Exception {
        String message = timestamp + "." + jsonPayload;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(signingKey), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(message.getBytes("UTF-8"));
        return bytesToHex(hmacBytes);
    }

    public boolean validateSignature(String receivedSig, String timestamp, String payload) throws Exception {
        String expectedSignature = generateSignature(timestamp, payload);
        return expectedSignature.equals(receivedSig);
    }

    public static Map<String, String> extractSignatureHeader(String header) {
        try {
            if (header.startsWith("Carbon-Signature:")) {
                header = header.substring("Carbon-Signature:".length()).trim();
            }
            Map<String, String> parts = new HashMap<>();
            for (String part : header.split(",")) {
                String[] kv = part.split("=");
                parts.put(kv[0].trim(), kv[1].trim());
            }
            return parts;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Carbon-Signature header format", e);
        }
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
