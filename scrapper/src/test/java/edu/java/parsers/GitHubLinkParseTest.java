package edu.java.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.java.parsers.GitHubLinkParse.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubLinkParseTest {

    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/bigseized/Poetress, /repos/bigseized/Poetress",
        "https://github.com/bigseized/Poetress/?queryParams, /repos/bigseized/Poetress",
        "https://github.com/bigseized/Poetress?queryParams, /repos/bigseized/Poetress"
    })
    @DisplayName("Проверка на корректную работу метода parse() GitHub")
    public void parseGitHubLinkTest(String input, String output) {
        String parsed = parse(input);
        assertEquals(parsed, output);
    }

}
