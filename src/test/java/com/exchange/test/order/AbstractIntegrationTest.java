package com.exchange.test.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

abstract public class AbstractIntegrationTest {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    public static File[] getFiles(String first, String... more) {
        File[] files = Paths.get(first, more).toFile().listFiles();
        Arrays.sort(files, Comparator.comparingInt(file -> Integer.parseInt(file.getName().split("\\.")[0])));
        return files;
    }

    public static Stream<Arguments> loadTestCases(String basePath) throws FileNotFoundException {
        File[] inputFiles = getFiles(basePath, "input");
        File[] outputFiles = getFiles(basePath, "output");

        List<Arguments> params = new ArrayList<>();
        for (int i = 0; i < inputFiles.length; i++) {
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFiles[i]));
            BufferedReader outputReader = new BufferedReader(new FileReader(outputFiles[i]));

            String input = inputReader.lines().reduce("", (a, b) -> a + b + "\n");
            String output = outputReader.lines().reduce("", (a, b) -> a + b + "\n");

            params.add(Arguments.of(input, output));
        }

        return params.stream();
    }
}
