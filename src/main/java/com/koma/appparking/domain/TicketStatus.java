package com.koma.appparking.domain;

public enum TicketStatus {
    PAID("Zapłacone"),
    PENDING("W trakcie płatności");

    private final String description;

    TicketStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
