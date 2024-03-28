package edu.java.services.link_resolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowResolver extends AbstractLinkResolver {

    private final static Pattern STACKOVERFLOW_QUESTION =
        Pattern.compile("^(https?://)?(www\\.)?stackoverflow\\.com/questions/\\d+/.+$");

    @Override
    public LinkType checkLink(String link) {
        Matcher reposMatcher = STACKOVERFLOW_QUESTION.matcher(link);
        if (reposMatcher.matches()) {
            return LinkType.STACKOVERFLOW_QUESTION;
        } else {
            return checkNext(link);
        }
    }
}
