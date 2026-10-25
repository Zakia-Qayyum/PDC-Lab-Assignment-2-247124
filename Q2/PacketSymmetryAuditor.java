package Q2;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String payload = "RADAR";
        System.out.println("=== API GATEWAY PACKET SYMMETRY AUDITOR ===");
        System.out.println("Ingesting Network Packet Payload: " + payload);

        TransactionStack stack = new TransactionStack(payload.length());
        RequestQueue queue = new RequestQueue(payload.length());

        for (int i = 0; i < payload.length(); i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean isSymmetrical = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            if (stack.pop() != queue.dequeue()) {
                isSymmetrical = false;
                break;
            }
        }

        if (isSymmetrical) {
            System.out.println("VERIFICATION SUCCESS: Packet exhibits bi-directional payload symmetry (Palindrome Verified).");
        } else {
            System.out.println("VERIFICATION FAILED: Packet payload is asymmetrical!");
        }
    }
}
