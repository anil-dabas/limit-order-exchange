package com.exchange.test.order.constant;

public class TestConstants {
    public static final String ORDER_ID_1 = "1";
    public static final String ORDER_ID_2 = "2";
    public static final char BUY_SIDE = 'B';
    public static final char SELL_SIDE = 'S';
    public static final int PRICE_100 = 100;
    public static final int VOLUME_10 = 10;
    public static final long TIMESTAMP_1 = 123456789L;
    public static final long TIMESTAMP_2 = 123456790L;

    public static final String ORDER_INPUT = "1,B,100,10\n2,S,100,10\n";

    public static final String BUY_ORDER_ID = "123";
    public static final int BUY_ORDER_PRICE = 100;
    public static final int BUY_ORDER_VOLUME = 10;
    public static final long BUY_ORDER_TIMESTAMP = 123456789L;

    public static final String SELL_ORDER_ID = "456";
    public static final int SELL_ORDER_PRICE = 100;
    public static final int SELL_ORDER_VOLUME = 10;
    public static final long SELL_ORDER_TIMESTAMP = 123456790L;

    public static final String TRADE_1 = "trade 1,2,100,10";
    public static final String TRADE_2 = "trade 3,4,150,5";
    public static final String EXPECTED_TRADE_OP = "trade 1,2,100,10\ntrade 3,4,150,5\n";
}
