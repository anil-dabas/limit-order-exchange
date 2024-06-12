package com.exchange.test;


import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.OrderProcessor;
import com.exchange.test.trade.TradeRecorder;
import com.exchange.test.util.ConsolePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

import static com.exchange.test.constant.Constants.DELIMITER;

public class ExchangeApplication {

    public static void main(String[] args) {
        TradeRecorder tradeRecorder = new TradeRecorder();
        OrderBook orderBook = new OrderBook();
        OrderProcessor processor = new OrderProcessor(orderBook, tradeRecorder);

        // Create a thread pool with a fixed number of threads based on available processors
        int numProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);

        // Use a blocking queue to manage incoming orders
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ExecutorService orderProcessorExecutor = new ThreadPoolExecutor(numProcessors, numProcessors,
                0L, TimeUnit.MILLISECONDS, queue);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String currentLine = line;
                final long timestamp = System.nanoTime();
                orderProcessorExecutor.submit(() -> {
                    Order order = getOrder(currentLine, timestamp);
                    processor.processOrder(order);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            shutdownExecutor(orderProcessorExecutor);
        }

        // Wait for all orders to be processed before printing trades and order book
        try {
            orderProcessorExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Print trades and order book after all orders are processed
        ConsolePrinter.printTrades(tradeRecorder);
        ConsolePrinter.printOrderBook(orderBook);

        executor.shutdown();
    }

    private static Order getOrder(String currentLine, long timestamp) {
        String[] parts = currentLine.split(DELIMITER);
        String id = parts[0];
        char side = parts[1].charAt(0);
        int price = Integer.parseInt(parts[2]);
        int volume = Integer.parseInt(parts[3]);
        return new Order(id, side, price, volume, timestamp);
    }

    private static void shutdownExecutor(ExecutorService orderProcessorExecutor) {
        orderProcessorExecutor.shutdown();
        try {
            if (!orderProcessorExecutor.awaitTermination(1, TimeUnit.HOURS)) {
                orderProcessorExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            orderProcessorExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
