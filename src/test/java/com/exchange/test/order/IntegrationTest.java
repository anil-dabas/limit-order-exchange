package com.exchange.test.order;


import com.exchange.test.ExchangeApplication;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest(name = "{index}")
    @MethodSource("provideTestCases")
    public void testMain(String input, String expectedOutput) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        ExchangeApplication.main(new String[0]);

        assertEquals(expectedOutput, outContent.toString());
    }

    private static Stream<Arguments> provideTestCases() throws FileNotFoundException {
        return AbstractIntegrationTest.loadTestCases("src/test/resources/test_cases");
    }
}
