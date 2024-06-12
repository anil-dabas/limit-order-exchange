package com.exchange.test.order.unittest;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.OrderProcessor;
import com.exchange.test.trade.TradeRecorder;
import org.junit.jupiter.api.Test;

import static com.exchange.test.order.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;


public class OrderProcessorTest {

    @Test
    void testProcessBuyOrderNoMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, BUY_ORDER_VOLUME,
                BUY_ORDER_TIMESTAMP);
        processor.processOrder(buyOrder);

        // Assertions
        assertTrue(tradeRecorder.getTrades().isEmpty());
        assertTrue(orderBook.getBuyOrders().contains(buyOrder));
    }

    @Test
    void testProcessBuyOrderWithMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, SELL_ORDER_VOLUME,
                SELL_ORDER_TIMESTAMP);
        orderBook.addSellOrder(sellOrder);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, BUY_ORDER_VOLUME,
                BUY_ORDER_TIMESTAMP);
        processor.processOrder(buyOrder);

        // Assertions
        assertEquals(1, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getBuyOrders().isEmpty());
        assertTrue(orderBook.getSellOrders().isEmpty());
    }

    @Test
    void testProcessSellOrderNoMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, SELL_ORDER_VOLUME,
                SELL_ORDER_TIMESTAMP);
        processor.processOrder(sellOrder);

        // Assertions

        assertTrue(tradeRecorder.getTrades().isEmpty());
        assertTrue(orderBook.getSellOrders().contains(sellOrder));
    }

    @Test
    void testProcessSellOrderWithMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, BUY_ORDER_VOLUME,
                BUY_ORDER_TIMESTAMP);
        orderBook.addBuyOrder(buyOrder);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, SELL_ORDER_VOLUME,
                SELL_ORDER_TIMESTAMP);
        processor.processOrder(sellOrder);

        // Assertions
        assertEquals(1, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getBuyOrders().isEmpty());
        assertTrue(orderBook.getSellOrders().isEmpty());
    }

    @Test
    void testProcessBuyOrderPartialMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, 5,
                SELL_ORDER_TIMESTAMP);
        orderBook.addSellOrder(sellOrder);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, BUY_ORDER_VOLUME,
                BUY_ORDER_TIMESTAMP);
        processor.processOrder(buyOrder);

        // Assertions

        assertEquals(1, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getSellOrders().isEmpty());
        assertEquals(1, orderBook.getBuyOrders().size()); // Check remaining buy order
        assertEquals(5, orderBook.getBuyOrders().peek().getVolume()); // Check remaining volume
    }

    @Test
    void testProcessSellOrderPartialMatch() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, 5,
                BUY_ORDER_TIMESTAMP);
        orderBook.addBuyOrder(buyOrder);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, SELL_ORDER_VOLUME,
                SELL_ORDER_TIMESTAMP);
        processor.processOrder(sellOrder);

        // Assertions

        assertEquals(1, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getBuyOrders().isEmpty());
        assertEquals(1, orderBook.getSellOrders().size());
        assertEquals(5, orderBook.getSellOrders().peek().getVolume());
    }

    @Test
    void testProcessBuyOrderWithMultipleMatches() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order sellOrder1 = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, 5,
                SELL_ORDER_TIMESTAMP);
        Order sellOrder2 = new Order("789", 'S', 100, 10, 123456791L);
        orderBook.addSellOrder(sellOrder1);
        orderBook.addSellOrder(sellOrder2);

        Order buyOrder = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, BUY_ORDER_VOLUME,
                BUY_ORDER_TIMESTAMP);
        processor.processOrder(buyOrder);

        // Assertions

        assertEquals(2, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getBuyOrders().isEmpty());
        assertFalse(orderBook.getSellOrders().isEmpty());
    }

    @Test
    void testProcessSellOrderWithMultipleMatches() {
        OrderBook orderBook = new OrderBook();
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        Order buyOrder1 = new Order(BUY_ORDER_ID, BUY_SIDE,
                BUY_ORDER_PRICE, 5,
                BUY_ORDER_TIMESTAMP);
        Order buyOrder2 = new Order("789", 'B', 100, 10, 123456791L);
        orderBook.addBuyOrder(buyOrder1);
        orderBook.addBuyOrder(buyOrder2);

        Order sellOrder = new Order(SELL_ORDER_ID, SELL_SIDE,
                SELL_ORDER_PRICE, SELL_ORDER_VOLUME,
                SELL_ORDER_TIMESTAMP);
        processor.processOrder(sellOrder);

        // Assertions
        assertEquals(2, tradeRecorder.getTrades().size());
        assertTrue(orderBook.getSellOrders().isEmpty());
        assertFalse(orderBook.getBuyOrders().isEmpty());
    }
}