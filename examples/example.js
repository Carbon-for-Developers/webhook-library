const WebhookVerifier = require('../carbon_webhooks_javascript/verifier');

const SIGNING_SECRET = 'aa76aee859f223451fd9bfb37ce893a0';  // Replace with your actual signing key
const verifier = new WebhookVerifier(SIGNING_SECRET);

function verifyWebhook(headers, payload) {
    const carbonSignature = headers['Carbon-Signature'];
    if (!carbonSignature) {
        return { status: 'error', message: 'Missing Carbon-Signature header' };
    }

    try {
        const { timestamp, receivedSignature } = WebhookVerifier.extractSignatureHeader(carbonSignature);
        if (!verifier.validateSignature(receivedSignature, timestamp, payload)) {
            return { status: 'error', message: 'Invalid signature' };
        }

        const data = JSON.parse(payload);
        console.log('Received webhook data:', data);

        // Handle the event
        const eventType = data.webhook_type;
        if (eventType === 'example_event') {
            // Process the event
            console.log('Processing example_event');
        }

        return { status: 'success' };
    } catch (error) {
        return { status: 'error', message: 'Invalid Carbon-Signature header format' };
    }
}

const payloadV1 = '{"payload": "{\\"webhook_type\\": \\"FILES_CREATED\\", \\"obj\\": {\\"object_type\\": \\"FILE_LIST\\", \\"object_id\\": [\\"46654\\"], \\"additional_information\\": \\"null\\"}, \\"customer_id\\": \\"satvik\\", \\"timestamp\\": \\"1721392406\\"}"}';

const headers = {
    'Content-Type': 'application/json',
    'Carbon-Signature': 't=1721392406,v1=aa2273ab64bb9162e7e7983a9cd7ab9f90d686691b1fd25c577991ad42c53fc1',
    'Carbon-Signature-Compact': 't=1721392406,v2=42a86d4083fee090b5a0800a91e82fb389f0bed4da757d07ee8ba97485194e59'
};

const result = verifyWebhook(headers, payloadV1);
console.log(`Verification Result: ${JSON.stringify(result)}`);
