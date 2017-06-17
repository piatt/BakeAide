package com.piatt.udacity.bakeaide.model;

import lombok.Getter;

public class FetchStatusEvent {
    @Getter boolean fetching;
    @Getter String message;

    public FetchStatusEvent(boolean fetching) {
        this.fetching = fetching;
    }

    public FetchStatusEvent(String message) {
        this.message = message;
    }

    public boolean hasMessage() {
        return message != null && !message.isEmpty();
    }
}