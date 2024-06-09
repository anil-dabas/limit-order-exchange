package com.exchange.test;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.OrderProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ExchangeApplication {
    public static void main(String[] args) {

        List<String> lines = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            lines = reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OrderBook orderBook = new OrderBook();
        OrderProcessor processor = new OrderProcessor(orderBook);
        long timestamp = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            String id = parts[0];
            char side = parts[1].charAt(0);
            int price = Integer.parseInt(parts[2]);
            int volume = Integer.parseInt(parts[3]);
            Order order = new Order(id, side, price, volume, timestamp++);
            processor.processOrder(order);
        }

        orderBook.printOrderBook();
    }
}