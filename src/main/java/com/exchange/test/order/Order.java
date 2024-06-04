package com.exchange.test.order;


public class Order {
    private final String id;
    private final char side;
    private final int price;
    private int volume;

    public Order(String id, char side, int price, int volume) {
        this.id = id;
        this.side = side;
        this.price = price;
        this.volume = volume;
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
}
