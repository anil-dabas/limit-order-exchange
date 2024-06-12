package com.exchange.test.order.unittest;

import com.exchange.test.trade.TradeRecorder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeRecorderTest {

    @Test
    void testRecordTrade() {
        TradeRecorder tradeRecorder = new TradeRecorder();
        tradeRecorder.recordTrade("Trade1");
        tradeRecorder.recordTrade("Trade2");

        ConcurrentLinkedQueue<String> trades = tradeRecorder.getTrades();
        assertEquals(2, trades.size());
        assertEquals("Trade1", trades.poll());
        assertEquals("Trade2", trades.poll());
    }
}
