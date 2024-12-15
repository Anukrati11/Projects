package com.example.AppInstallationSystem.entity.enumEntity;

public enum AppStateEnum {

    // SCHEDULED, PICKEDUP, ERROR, COMPLETED
    Scheduled("SCHEDULED"),
    PickedUp("PICKEDUP"),
    Error("ERROR"),
    Completed("COMPLETED");

   public  String value;

    private AppStateEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
