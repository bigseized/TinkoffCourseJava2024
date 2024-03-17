package edu.java.services.link_resolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class GitHubResolver extends AbstractLinkResolver {

    private final static Pattern GITHUB_REPOS_PATTERN =
        Pattern.compile("^(https?://)?(www\\.)?github\\.com/[a-zA-Z0-9_-]+/[a-zA-Z0-9_-]+$");

    @Override
    public LinkType checkLink(String link) {
        Matcher reposMatcher = GITHUB_REPOS_PATTERN.matcher(link);
        if (reposMatcher.matches()) {
            return LinkType.GITHUB_REPOS;
        } else {
            return checkNext(link);
        }
    }
}
