public class TradingEngineApp {
    public static void main(String[] args) {
        System.out.println("=== COMPLEX EVENT PROCESSING (CEP) ENGINE ===");
        TradeEvent e1 = new FIXOrderEvent("FIX_ADAPTER_01", System.currentTimeMillis(), 150.00, "ORD-99281");
        TradeEvent e2 = new ReutersQuoteEvent("REUTERS_FEED_A", System.currentTimeMillis(), 150.00, 149.85, 150.15);

        e1.dispatch();
        e2.dispatch();
    }
}