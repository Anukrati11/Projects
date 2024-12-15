package com.example.AppInstallationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    private AppInstallationService appInstallationService;

    //@Scheduled annotation automates periodic task execution
    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void scheduleAppInstallations() {
        appInstallationService.processScheduledApps();
    }

    @Scheduled(cron = "0 0 * * * ?") // Every hour
    public void checkForUpdates() {
        appInstallationService.autoUpdateApps(true);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Daily at midnight
    public void rescheduleFailedApps() {
        appInstallationService.rescheduleFailedApps();
    }
}
