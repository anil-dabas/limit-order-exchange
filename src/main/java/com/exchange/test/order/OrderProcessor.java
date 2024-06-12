package com.exchange.test.order;


import com.exchange.test.trade.TradeRecorder;

public class OrderProcessor {
    private final OrderBook orderBook;
    private final TradeRecorder tradeRecorder;

    public OrderProcessor(OrderBook orderBook, TradeRecorder tradeRecorder) {
        this.orderBook = orderBook;
        this.tradeRecorder = tradeRecorder;
    }

    public void processOrder(Order order) {
        if (order.getSide() == 'B') {
            processBuyOrder(order);
        } else {
            processSellOrder(order);
        }
    }

    private void processBuyOrder(Order order) {
        orderBook.withLock(() -> {
            while (order.getVolume() > 0 && !orderBook.getSellOrders().isEmpty()) {
                Order sellOrder = orderBook.getSellOrders().poll();
                if (sellOrder.getPrice() > order.getPrice()) {
                    orderBook.getSellOrders().offer(sellOrder);
                    break;
                }
                int tradeVolume = Math.min(order.getVolume(), sellOrder.getVolume());
                String trade = String.format("trade %s,%s,%d,%d", order.getId(), sellOrder.getId(), sellOrder.getPrice(), tradeVolume);
                //System.out.println(trade);
                tradeRecorder.recordTrade(trade);
                order.setVolume(order.getVolume() - tradeVolume);
                sellOrder.setVolume(sellOrder.getVolume() - tradeVolume);
                if (sellOrder.getVolume() > 0) {
                    orderBook.getSellOrders().offer(sellOrder);
                }
            }
            if (order.getVolume() > 0) {
                orderBook.addBuyOrder(order);
            }
        });
    }

    private void processSellOrder(Order order) {
        orderBook.withLock(() -> {
            while (order.getVolume() > 0 && !orderBook.getBuyOrders().isEmpty()) {
                Order buyOrder = orderBook.getBuyOrders().poll();
                if (buyOrder.getPrice() < order.getPrice()) {
                    orderBook.getBuyOrders().offer(buyOrder);
                    break;
                }
                int tradeVolume = Math.min(order.getVolume(), buyOrder.getVolume());
                String trade = String.format("trade %s,%s,%d,%d", order.getId(), buyOrder.getId(), buyOrder.getPrice(), tradeVolume);
               // System.out.println(trade);
                tradeRecorder.recordTrade(trade);
                order.setVolume(order.getVolume() - tradeVolume);
                buyOrder.setVolume(buyOrder.getVolume() - tradeVolume);
                if (buyOrder.getVolume() > 0) {
                    orderBook.getBuyOrders().offer(buyOrder);
                }
            }
            if (order.getVolume() > 0) {
                orderBook.addSellOrder(order);
            }
        });
    }
}
