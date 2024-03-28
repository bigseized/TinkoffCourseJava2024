package edu.java.services.link_resolver;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractLinkResolver {
    AbstractLinkResolver nextLinkResolver;

    public static AbstractLinkResolver makeChain(List<AbstractLinkResolver> resolvers) {
        AbstractLinkResolver currentResolver = resolvers.getFirst();
        for (var resolver : resolvers.subList(1, resolvers.size())) {
            currentResolver.nextLinkResolver = resolver;
            currentResolver = resolver;
        }
        return resolvers.getFirst();
    }

    public abstract LinkType checkLink(String link);

    protected LinkType checkNext(String link) {
        if (nextLinkResolver != null) {
            return nextLinkResolver.checkLink(link);
        } else {
            return LinkType.UNRESOLVED;
        }
    }
}
