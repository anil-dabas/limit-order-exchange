package com.exchange.test.order;

import java.util.Iterator;
import java.util.PriorityQueue;

public class OrderBook {
    private final PriorityQueue<Order> buyOrders;
    private final PriorityQueue<Order> sellOrders;

    public OrderBook() {
        buyOrders = new PriorityQueue<>((a, b) -> Integer.compare(b.getPrice(), a.getPrice()));
        sellOrders = new PriorityQueue<>();
    }

    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }

    public void addBuyOrder(Order order) {
        buyOrders.offer(order);
    }

    public void addSellOrder(Order order) {
        sellOrders.offer(order);
    }

    public void printOrderBook() {
        Iterator<Order> buyIterator = buyOrders.iterator();
        Iterator<Order> sellIterator = sellOrders.iterator();

        while (buyIterator.hasNext() || sellIterator.hasNext()) {
            if (buyIterator.hasNext()) {
                Order buyOrder = buyIterator.next();
                System.out.printf("%9d %6d | ", buyOrder.getVolume(), buyOrder.getPrice());
            } else {
                System.out.print("                 | ");
            }
            if (sellIterator.hasNext()) {
                Order sellOrder = sellIterator.next();
                System.out.printf("%6d %9d%n", sellOrder.getPrice(), sellOrder.getVolume());
            } else {
                System.out.println();
            }
        }
    }
}
