package edu.java.parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.java.utilities.LinkParseUtil.parseGitHubRepos;
import static edu.java.utilities.LinkParseUtil.parseStackOverflowQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkParseUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/bigseized/Poetress, bigseized, Poetress",
        "https://github.com/bigseized/Poetress/?queryParams, bigseized,Poetress",
        "https://github.com/bigseized/Poetress?queryParams, bigseized,Poetress"
    })
    @DisplayName("Проверка на корректную работу метода parse() GitHub")
    public void parseGitHubLinkTest(String input, String user, String reposName) {
        String[] parsed = parseGitHubRepos(input);
        assertThat(parsed).containsExactly(user, reposName);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "https://stackoverflow.com/questions/11227809/array, 11227809",
        "https://stackoverflow.com/questions/11227809/array/?queryParams, 11227809",
        "https://stackoverflow.com/questions/11227809/array?queryParams, 11227809"
    })
    @DisplayName("Проверка на корректную работу метода parse() StackOverflow")
    public void parseStackOverflowLinkTest(String input, String output) {
        String parsed = parseStackOverflowQuestion(input);
        assertEquals(parsed, output);
    }

}
