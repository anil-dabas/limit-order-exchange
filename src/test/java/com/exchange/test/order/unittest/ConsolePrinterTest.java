package com.exchange.test.order.unittest;

import com.exchange.test.order.Order;
import com.exchange.test.order.OrderBook;
import com.exchange.test.order.constant.TestConstants;
import com.exchange.test.trade.TradeRecorder;
import com.exchange.test.util.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.exchange.test.constant.Constants.BUY_SIDE_FORMAT;
import static com.exchange.test.constant.Constants.SELL_SIDE_FORMAT;
import static com.exchange.test.order.constant.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsolePrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testPrintTrades() {
        TradeRecorder tradeRecorder = new TradeRecorder();
        tradeRecorder.recordTrade(TRADE_1);
        tradeRecorder.recordTrade(TRADE_2);

        ConsolePrinter.printTrades(tradeRecorder);

        // Assertions

        assertEquals(EXPECTED_TRADE_OP, outContent.toString());
    }

    @Test
    void testPrintOrderBook() {
        OrderBook orderBook = new OrderBook();
        Order buyOrder = new Order(TestConstants.ORDER_ID_1, TestConstants.BUY_SIDE, TestConstants.PRICE_100, TestConstants.VOLUME_10, TestConstants.TIMESTAMP_1);
        Order sellOrder = new Order(TestConstants.ORDER_ID_2, TestConstants.SELL_SIDE, TestConstants.PRICE_100, TestConstants.VOLUME_10, TestConstants.TIMESTAMP_2);
        orderBook.addBuyOrder(buyOrder);
        orderBook.addSellOrder(sellOrder);

        ConsolePrinter.printOrderBook(orderBook);

        String expectedOutput = String.format(BUY_SIDE_FORMAT,10,100) + String.format(SELL_SIDE_FORMAT,100,10);
        String actualOutput = outContent.toString();

        // Assertions
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void testPrintOrderBook_EmptyOrderBook() {
        OrderBook orderBook = new OrderBook();

        ConsolePrinter.printOrderBook(orderBook);

        // Assertions
        assertTrue(outContent.toString().isEmpty());
    }
}
