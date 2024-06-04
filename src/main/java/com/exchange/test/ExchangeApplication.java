package com.exchange.test;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.OrderProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExchangeApplication {
    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("/dev/stdin"));

        OrderBook orderBook = new OrderBook();
        OrderProcessor processor = new OrderProcessor(orderBook);

        lines.forEach(line -> {
            String[] parts = line.split(",");
            String id = parts[0];
            char side = parts[1].charAt(0);
            int price = Integer.parseInt(parts[2]);
            int volume = Integer.parseInt(parts[3]);
            Order order = new Order(id, side, price, volume);
            processor.processOrder(order);
        });

        orderBook.printOrderBook();
    }
}