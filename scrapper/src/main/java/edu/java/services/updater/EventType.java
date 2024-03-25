package edu.java.services.updater;

public enum EventType {
    GITHUB_REPOS_PULL_REQUEST_REVIEW("PullRequestReviewEvent"),
    GITHUB_REPOS_PULL_REQUEST_COMMENT("PullRequestReviewCommentEvent"),
    GITHUB_REPOS_PULL_REQUEST("PullRequestEvent"),
    GITHUB_REPOS_PUSH("PushEvent"),
    REMOVE("REMOVE"),
    DEFAULT("DEFAULT");

    private final String event;

    EventType(String event) {
        this.event = event;
    }

    public static EventType resolve(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.event.equals(type)) {
                return eventType;
            }
        }
        return DEFAULT;
    }
}
