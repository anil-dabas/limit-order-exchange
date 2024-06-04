package com.exchange.test.order;


public class OrderProcessor {
    private final OrderBook orderBook;

    public OrderProcessor(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void processOrder(Order order) {
        if (order.getSide() == 'B') {
            processBuyOrder(order);
        } else {
            processSellOrder(order);
        }
    }

    private void processBuyOrder(Order order) {
        while (order.getVolume() > 0 && !orderBook.getSellOrders().isEmpty()) {
            Order sellOrder = orderBook.getSellOrders().poll();
            if (sellOrder.getPrice() > order.getPrice()) {
                orderBook.getSellOrders().offer(sellOrder);
                break;
            }
            int tradeVolume = Math.min(order.getVolume(), sellOrder.getVolume());
            System.out.printf("trade %s,%s,%d,%d%n", order.getId(), sellOrder.getId(), sellOrder.getPrice(), tradeVolume);
            order.setVolume(order.getVolume() - tradeVolume);
            sellOrder.setVolume(sellOrder.getVolume() - tradeVolume);
            if (sellOrder.getVolume() > 0) {
                orderBook.getSellOrders().offer(sellOrder);
            }
        }
        if (order.getVolume() > 0) {
            orderBook.addBuyOrder(order);
        }
    }

    private void processSellOrder(Order order) {
        while (order.getVolume() > 0 && !orderBook.getBuyOrders().isEmpty()) {
            Order buyOrder = orderBook.getBuyOrders().poll();
            if (buyOrder.getPrice() < order.getPrice()) {
                orderBook.getBuyOrders().offer(buyOrder);
                break;
            }
            int tradeVolume = Math.min(order.getVolume(), buyOrder.getVolume());
            System.out.printf("trade %s,%s,%d,%d%n", order.getId(), buyOrder.getId(), buyOrder.getPrice(), tradeVolume);
            order.setVolume(order.getVolume() - tradeVolume);
            buyOrder.setVolume(buyOrder.getVolume() - tradeVolume);
            if (buyOrder.getVolume() > 0) {
                orderBook.getBuyOrders().offer(buyOrder);
            }
        }
        if (order.getVolume() > 0) {
            orderBook.addSellOrder(order);
        }
    }
}
