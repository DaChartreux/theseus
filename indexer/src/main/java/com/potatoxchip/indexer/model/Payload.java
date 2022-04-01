package com.potatoxchip.indexer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {
    private String operation;
    private User before;
    private User after;

    @JsonProperty("op")
    public String getOperation() {
        return operation;
    }

    @JsonProperty("op")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getBefore() {
        return before;
    }

    public void setBefore(User before) {
        this.before = before;
    }

    public User getAfter() {
        return after;
    }

    public void setAfter(User after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return "Payload{" + "operation='" + operation + '\'' + ", before=" + before + ", after=" + after + '}';
    }
}
