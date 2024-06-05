package com.exchange.test.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderProcessorTest {

    private OrderBook orderBook;
    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
        orderProcessor = new OrderProcessor(orderBook);
    }

    @Test
    void testBuyOrderWithVolumeZero() {
        Order buyOrder = new Order("B1", 'B', 100, 0);
        orderProcessor.processOrder(buyOrder);
        assertEquals(0, buyOrder.getVolume());
    }

    @Test
    void testSellOrderWithVolumeZero() {
        Order sellOrder = new Order("S1", 'S', 100, 0);

        orderProcessor.processOrder(sellOrder);

        assertEquals(0, sellOrder.getVolume());
    }

    @Test
    void testBuyOrderWithPriceZero() {
        Order buyOrder = new Order("B1", 'B', 0, 10);
        Order sellOrder = new Order("S1", 'S', 10, 10);
        orderBook.getSellOrders().offer(sellOrder);

        orderProcessor.processOrder(buyOrder);

        assertEquals(10, buyOrder.getVolume());

    }

    @Test
    void testSellOrderWithPriceZero() {
        Order sellOrder = new Order("S1", 'S', 0, 10);
        Order buyOrder = new Order("B1", 'B', 10, 10);
        orderBook.getBuyOrders().offer(buyOrder);

        orderProcessor.processOrder(sellOrder);

        assertEquals(0, sellOrder.getVolume());

    }

}