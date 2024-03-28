package edu.java.services.link_resolver;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AbstractLinkResolverTest {
    AbstractLinkResolver resolver = AbstractLinkResolver.makeChain(
        List.of(new GitHubResolver(), new StackOverflowResolver())
    );

    @Test
    public void testGitHubResolver() {
        String validGitHubLink = "https://github.com/username/repository";
        String invalidGitHubLink = "https://invalid.github.com/username/repository";

        assertEquals(LinkType.GITHUB_REPOS, resolver.checkLink(validGitHubLink));
        assertEquals(LinkType.UNRESOLVED, resolver.checkLink(invalidGitHubLink));
    }

    @Test
    public void testStackOverflowResolver() {
        String validStackOverflowLink = "https://stackoverflow.com/questions/12345678/sample-question";
        String invalidStackOverflowLink = "https://invalid.stackoverflow.com/questions/12345678/sample-question";

        assertEquals(LinkType.STACKOVERFLOW_QUESTION, resolver.checkLink(validStackOverflowLink));
        assertEquals(LinkType.UNRESOLVED, resolver.checkLink(invalidStackOverflowLink));
    }
}
