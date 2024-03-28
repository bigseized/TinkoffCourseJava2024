package edu.java.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkParseUtil {

    public static final String QUERY_PARAMS_DELIMITER = "?";

    public static String[] parseGitHubRepos(String link) {
        String[] keyWords = link
            .substring(0, link.contains(QUERY_PARAMS_DELIMITER) ? link.indexOf(QUERY_PARAMS_DELIMITER) : link.length())
            .split("/");
        String userName = keyWords[keyWords.length - 2];
        String reposName = keyWords[keyWords.length - 1];
        return new String[]{userName, reposName};
    }

    public static String parseStackOverflowQuestion(String link) {
        String[] keyWords = link
            .substring(0, link.contains(QUERY_PARAMS_DELIMITER) ? link.indexOf(QUERY_PARAMS_DELIMITER) : link.length())
            .split("/");
        return keyWords[keyWords.length - 2];
    }
}
