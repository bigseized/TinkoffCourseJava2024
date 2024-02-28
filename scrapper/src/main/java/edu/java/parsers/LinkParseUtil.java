package edu.java.parsers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkParseUtil {

    public static String parseGitHub(String link) {
        String[] keyWords = link
            .substring(0, link.contains("?") ? link.indexOf("?") : link.length())
            .split("/");
        String userName = keyWords[keyWords.length - 2];
        String reposName = keyWords[keyWords.length - 1];
        return String.format("/repos/%s/%s", userName, reposName);
    }

    public static String parseStackOverflow(String link) {
        String[] keyWords = link
            .substring(0, link.contains("?") ? link.indexOf("?") : link.length())
            .split("/");
        String id = keyWords[keyWords.length - 2];
        return String.format("/questions/%s/?site=stackoverflow", id);
    }
}
