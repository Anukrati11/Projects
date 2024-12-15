package com.example.AppInstallationSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalDateTime;

//An entity class representing an application to be installed.
@Entity
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String version;
    private String state; // SCHEDULED, PICKEDUP, ERROR, COMPLETED
    private int retryCount;
    private LocalDateTime lastUpdated;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getState() {
        return this.state;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public void setRetryCount(final int retryCount) {
        this.retryCount = retryCount;
    }

    public void setLastUpdated(final LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public App() {
    }

    public App(final Long id, final String name, final String version, final String state, final int retryCount, final LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.state = state;
        this.retryCount = retryCount;
        this.lastUpdated = lastUpdated;
    }
}
