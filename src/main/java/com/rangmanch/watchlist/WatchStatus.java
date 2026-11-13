package com.rangmanch.watchlist;

public enum WatchStatus {
    PLAN_TO_WATCH("Plan to watch"),
    WATCHING("Currently watching"),
    WATCHED("Watched");

    private final String label;

    WatchStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
