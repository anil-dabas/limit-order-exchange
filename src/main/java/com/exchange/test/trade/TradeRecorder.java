package com.exchange.test.trade;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TradeRecorder {
    private final ConcurrentLinkedQueue<String> trades;

    public TradeRecorder() {
        trades = new ConcurrentLinkedQueue<>();
    }

    public void recordTrade(String trade) {
        trades.offer(trade);
    }

    public ConcurrentLinkedQueue<String> getTrades() {
        return trades;
    }
}
