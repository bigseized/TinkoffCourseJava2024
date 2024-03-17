package edu.java.bot.services;

public enum EventType {
    GITHUB_REPOS_PULL_REQUEST_REVIEW("PullRequestReviewEvent"),
    GITHUB_REPOS_PUSH("PushEvent"),
    REMOVE("REMOVE"),
    DEFAULT("DEFAULT");

    EventType(String event) {
    }
}
