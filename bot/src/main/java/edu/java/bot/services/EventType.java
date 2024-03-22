package edu.java.bot.services;

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
}
