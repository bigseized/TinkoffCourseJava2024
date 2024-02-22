package edu.java.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.java.parsers.StackOverflowLinkParse.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackOverflowLinkParseTest {

    @ParameterizedTest
    @CsvSource(value = {
        "https://stackoverflow.com/questions/11227809/array, /questions/11227809/?site=stackoverflow",
        "https://stackoverflow.com/questions/11227809/array/?queryParams, /questions/11227809/?site=stackoverflow",
        "https://stackoverflow.com/questions/11227809/array?queryParams, /questions/11227809/?site=stackoverflow"
    })
    @DisplayName("Проверка на корректную работу метода parse() StackOverflow")
    public void parseStackOverflowLinkTest(String input, String output) {
        String parsed = parse(input);
        assertEquals(parsed, output);
    }

}
