const crypto = require('crypto');

class WebhookVerifier {
    constructor(signingKey) {
        this.signingKey = signingKey;
    }

    generateSignature(timestamp, jsonPayload) {
        const message = `${timestamp}.${jsonPayload}`;
        return crypto.createHmac('sha256', Buffer.from(this.signingKey, 'hex'))
            .update(message, 'utf8')
            .digest('hex');
    }

    validateSignature(receivedSig, timestamp, payload) {
        const expectedSignature = this.generateSignature(timestamp, payload);
        return crypto.timingSafeEqual(Buffer.from(receivedSig, 'hex'), Buffer.from(expectedSignature, 'hex'));
    }

    static extractSignatureHeader(header) {
        try {
            if (header.startsWith('Carbon-Signature:')) {
                header = header.substring('Carbon-Signature:'.length).trim();
            }
            const parts = header.split(',').reduce((acc, part) => {
                const [key, value] = part.split('=').map(s => s.trim());
                acc[key] = value;
                return acc;
            }, {});
            const timestamp = parts['t'];
            const receivedSignature = parts['v1'];
            return { timestamp, receivedSignature };
        } catch (error) {
            throw new Error("Invalid Carbon-Signature header format");
        }
    }
}

module.exports = WebhookVerifier;
