package com.example.AppInstallationSystem.controller;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.service.AppInstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle HTTP requests related to app installations.
 */
@RestController
@RequestMapping("/installations")
public class AppInstallationController {

    @Autowired
    private AppInstallationService appInstallationService;

    /**
     * Endpoint to start the installation process for scheduled apps.
     */
    @GetMapping()
    public void startInstallation() {
        // Start the installation process
        appInstallationService.processScheduledApps();
    }

    /**
     * Endpoint to check for and apply automatic updates to apps.
     */
    @GetMapping("/update/{updateAvailable}")
    public void automaticUpdates(@PathVariable boolean updateAvailable) {
        // Check for updates
        appInstallationService.autoUpdateApps(updateAvailable);
    }

    /**
     * Endpoint to reschedule apps that failed to install.
     */
    @GetMapping("/reschedule")
    public void rescheduleFailedApps() {
        // Reschedule failed apps
        appInstallationService.rescheduleFailedApps();
    }
}
