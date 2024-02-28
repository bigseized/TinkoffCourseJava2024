package edu.java.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.java.parsers.LinkParseUtil.parseGitHub;
import static edu.java.parsers.LinkParseUtil.parseStackOverflow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkParseUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/bigseized/Poetress, /repos/bigseized/Poetress",
        "https://github.com/bigseized/Poetress/?queryParams, /repos/bigseized/Poetress",
        "https://github.com/bigseized/Poetress?queryParams, /repos/bigseized/Poetress"
    })
    @DisplayName("Проверка на корректную работу метода parse() GitHub")
    public void parseGitHubLinkTest(String input, String output) {
        String parsed = parseGitHub(input);
        assertEquals(parsed, output);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "https://stackoverflow.com/questions/11227809/array, /questions/11227809/?site=stackoverflow",
        "https://stackoverflow.com/questions/11227809/array/?queryParams, /questions/11227809/?site=stackoverflow",
        "https://stackoverflow.com/questions/11227809/array?queryParams, /questions/11227809/?site=stackoverflow"
    })
    @DisplayName("Проверка на корректную работу метода parse() StackOverflow")
    public void parseStackOverflowLinkTest(String input, String output) {
        String parsed = parseStackOverflow(input);
        assertEquals(parsed, output);
    }

}
