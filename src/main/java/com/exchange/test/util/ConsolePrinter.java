package com.exchange.test.util;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.trade.TradeRecorder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.exchange.test.constant.Constants.*;


public class ConsolePrinter {

    public static void printTrades(TradeRecorder tradeRecorder) {
        for (String trade : tradeRecorder.getTrades()) {
            System.out.println(trade);
        }
    }

    public static void printOrderBook(OrderBook orderBook) {
        orderBook.withLock(() -> {
            List<Order> sortedBuyOrders = new ArrayList<>(orderBook.getBuyOrders());
            List<Order> sortedSellOrders = new ArrayList<>(orderBook.getSellOrders());

            sortedBuyOrders.sort(Comparator.comparingInt(Order::getPrice).reversed().thenComparingLong(Order::getTimestamp));
            sortedSellOrders.sort(Comparator.comparingInt(Order::getPrice).thenComparingLong(Order::getTimestamp));

            int maxSize = Math.max(sortedBuyOrders.size(), sortedSellOrders.size());

            for (int i = 0; i < maxSize; i++) {
                if (i < sortedBuyOrders.size()) {
                    Order buyOrder = sortedBuyOrders.get(i);
                    System.out.printf(BUY_SIDE_FORMAT, buyOrder.getVolume(), buyOrder.getPrice());
                } else {
                    System.out.printf(BUY_SIDE_EMPTY_FORMAT, EMPTY_STRING);
                }
                if (i < sortedSellOrders.size()) {
                    Order sellOrder = sortedSellOrders.get(i);
                    System.out.printf(SELL_SIDE_FORMAT, sellOrder.getPrice(), sellOrder.getVolume());
                } else {
                    System.out.printf(SELL_SIDE_EMPTY_FORMAT, EMPTY_STRING);
                }
            }
        });
    }
}
