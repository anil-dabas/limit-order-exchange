package com.exchange.test.order;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderBook {
    private final PriorityQueue<Order> buyOrders;
    private final PriorityQueue<Order> sellOrders;
    private final Lock lock;

    public OrderBook() {
        buyOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice).reversed().thenComparingLong(Order::getTimestamp));
        sellOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice).thenComparingLong(Order::getTimestamp));
        lock = new ReentrantLock();
    }

    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }

    public void addBuyOrder(Order order) {
        lock.lock();
        try {
            buyOrders.offer(order);
        } finally {
            lock.unlock();
        }
    }

    public void addSellOrder(Order order) {
        lock.lock();
        try {
            sellOrders.offer(order);
        } finally {
            lock.unlock();
        }
    }

    public void withLock(Runnable action) {
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
        }
    }
}
