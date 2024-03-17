package edu.java.services.updater.jdbc;

@SuppressWarnings("checkstyle:MultipleStringLiterals")
public enum EventType {
    GITHUB_REPOS_PULL_REQUEST_REVIEW("PullRequestReviewEvent"),
    GITHUB_REPOS_PUSH("PushEvent"),
    REMOVE("REMOVE"),
    DEFAULT("DEFAULT");

    EventType(String event) {
    }

    public static EventType resolve(String type) {
        return switch (type) {
            case "PullRequestReviewEvent" -> EventType.GITHUB_REPOS_PULL_REQUEST_REVIEW;
            case "PushEvent" -> EventType.GITHUB_REPOS_PUSH;
            default -> DEFAULT;
        };
    }
}
