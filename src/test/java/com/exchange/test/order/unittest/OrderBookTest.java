package com.exchange.test.order.unittest;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static com.exchange.test.order.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderBookTest {

    @Test
    void testAddBuyOrder() {
        OrderBook orderBook = new OrderBook();
        Order order = new Order(BUY_ORDER_ID, BUY_SIDE, BUY_ORDER_PRICE, BUY_ORDER_VOLUME, BUY_ORDER_TIMESTAMP);
        orderBook.addBuyOrder(order);
        PriorityQueue<Order> buyOrders = orderBook.getBuyOrders();

        // Assertions
        assertEquals(1, buyOrders.size());
        assertEquals(order, buyOrders.peek());
    }

    @Test
    void testAddSellOrder() {
        OrderBook orderBook = new OrderBook();
        Order order = new Order(SELL_ORDER_ID, SELL_SIDE, SELL_ORDER_PRICE, SELL_ORDER_VOLUME, SELL_ORDER_TIMESTAMP);
        orderBook.addSellOrder(order);
        PriorityQueue<Order> sellOrders = orderBook.getSellOrders();

        // Assertions
        assertEquals(1, sellOrders.size());
        assertEquals(order, sellOrders.peek());
    }
}
