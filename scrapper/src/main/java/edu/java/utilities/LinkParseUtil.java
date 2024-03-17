package edu.java.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkParseUtil {

    public static String[] parseGitHubRepos(String link) {
        String[] keyWords = link
            .substring(0, link.contains("?") ? link.indexOf("?") : link.length())
            .split("/");
        String userName = keyWords[keyWords.length - 2];
        String reposName = keyWords[keyWords.length - 1];
        return new String[]{userName, reposName};
    }

    public static String parseStackOverflowQuestion(String link) {
        String[] keyWords = link
            .substring(0, link.contains("?") ? link.indexOf("?") : link.length())
            .split("/");
        return keyWords[keyWords.length - 2];
    }
}
