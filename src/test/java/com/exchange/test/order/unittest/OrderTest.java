package com.exchange.test.order.unittest;


import com.exchange.test.order.Order;
import org.junit.jupiter.api.Test;

import static com.exchange.test.order.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void testOrderCreation() {
        Order order = new Order(ORDER_ID_1, BUY_SIDE, PRICE_100, VOLUME_10, TIMESTAMP_1);
        assertEquals(ORDER_ID_1, order.getId());
        assertEquals(BUY_SIDE, order.getSide());
        assertEquals(PRICE_100, order.getPrice());
        assertEquals(VOLUME_10, order.getVolume());
        assertEquals(TIMESTAMP_1, order.getTimestamp());
    }

    @Test
    public void testSetVolume() {
        Order order = new Order(ORDER_ID_1, BUY_SIDE, PRICE_100, VOLUME_10, TIMESTAMP_1);
        order.setVolume(5);
        assertEquals(5, order.getVolume());
    }
}
