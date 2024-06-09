package com.exchange.test.order;

import java.util.*;

public class OrderBook {
    private final PriorityQueue<Order> buyOrders;
    private final PriorityQueue<Order> sellOrders;

    public OrderBook() {
        buyOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice).reversed().thenComparingLong(Order::getTimestamp));
        sellOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice).thenComparingLong(Order::getTimestamp));
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
        List<Order> sortedBuyOrders = new ArrayList<>(buyOrders);
        List<Order> sortedSellOrders = new ArrayList<>(sellOrders);

        sortedBuyOrders.sort(Comparator.comparingInt(Order::getPrice).reversed().thenComparingLong(Order::getTimestamp));
        sortedSellOrders.sort(Comparator.comparingInt(Order::getPrice).thenComparingLong(Order::getTimestamp));

        int maxSize = Math.max(sortedBuyOrders.size(), sortedSellOrders.size());

        for (int i = 0; i < maxSize; i++) {
            if (i < sortedBuyOrders.size()) {
                Order buyOrder = sortedBuyOrders.get(i);
                System.out.printf("%,11d %,6d | ", buyOrder.getVolume(), buyOrder.getPrice());
            } else {
                System.out.printf("%18s | ","");
            }
            if (i < sortedSellOrders.size()) {
                Order sellOrder = sortedSellOrders.get(i);
                System.out.printf("%6d %,11d%n", sellOrder.getPrice(), sellOrder.getVolume());
            } else {
                System.out.printf("%18s\n","");
            }
        }
    }
}
