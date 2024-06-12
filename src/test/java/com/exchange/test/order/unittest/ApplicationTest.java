package com.exchange.test.order.unittest;

import com.exchange.test.ExchangeApplication;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.OrderProcessor;
import com.exchange.test.trade.TradeRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.exchange.test.order.constant.TestConstants.ORDER_INPUT;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApplicationTest {

    private OrderBook orderBook;
    private OrderProcessor orderProcessor;
    private TradeRecorder tradeRecorder;

    @BeforeEach
    public void setUp() {
        orderBook = new OrderBook();
        tradeRecorder = new TradeRecorder();
        orderProcessor = new OrderProcessor(orderBook, tradeRecorder);
    }

    @Test
    public void testOrderProcessing() {
        InputStream in = new ByteArrayInputStream(ORDER_INPUT.getBytes());
        System.setIn(in);

        ExchangeApplication.main(new String[]{});

        // Assertions
        assertEquals(0, orderBook.getBuyOrders().size());
        assertEquals(0, orderBook.getSellOrders().size());
    }
}
