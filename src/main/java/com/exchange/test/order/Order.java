package com.exchange.test.order;

public class Order {
    private final String id;
    private final char side;
    private final int price;
    private int volume;
    private final long timestamp;

    public Order(String id, char side, int price, int volume, long timestamp) {
        this.id = id;
        this.side = side;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public char getSide() {
        return side;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public long getTimestamp() {
        return timestamp;
    }
}