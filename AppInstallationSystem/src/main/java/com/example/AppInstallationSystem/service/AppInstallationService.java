package com.example.AppInstallationSystem.service;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.entity.enumEntity.AppStateEnum;
import com.example.AppInstallationSystem.exception.MessagingException;
import com.example.AppInstallationSystem.repository.AppInstallationRepository;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
/**
 * This service handles the core logic for processing scheduled apps, installing apps, handling installation errors, auto-updating apps, and rescheduling failed apps.
 */
public class AppInstallationService {

    @Autowired
    private AppInstallationRepository appInstallationRepository;

    @Autowired
    private EmailNotificationService emailService;

    @Transactional
    public void processScheduledApps() {
        List<App> appList = appInstallationRepository.findByState(AppStateEnum.Scheduled.getValue());
        // Install each Scheduled app
        appList.forEach(this::installApp);
    }

    /**
     * Installs the given app.
     * @param app
     */
    private void installApp(App app) {
        try {
            // Simulate installation logic
            app.setState(AppStateEnum.PickedUp.getValue());
            app.setLastUpdated(LocalDateTime.now());
            appInstallationRepository.save(app);

            // Simulate success or failure
            if (Math.random() > 0.7) {
                throw new Exception("Simulated installation failure");
            }

            app.setState(AppStateEnum.Completed.getValue());
        } catch (Exception e) {
            handleInstallationError(app, e);
        } finally {
            app.setLastUpdated(LocalDateTime.now());
            appInstallationRepository.save(app);
        }
    }

    /**
     * Handles installation errors for the given app.
     * @param app
     * @param e
     */
    void handleInstallationError(App app, Exception e) {
        app.setRetryCount(app.getRetryCount() + 1);
        app.setState(AppStateEnum.Error.getValue());
        if (app.getRetryCount() >= 3) {
            try {
                emailService.sendFailureNotification(app, e.getMessage());

            } catch (MessagingException | jakarta.mail.MessagingException ex) {
                log.error("Failed to send email: " + e.getMessage());
                throw new RuntimeException("Failed to send email notification", ex);
            }
        } else {
            app.setState(AppStateEnum.Scheduled.getValue());
        }
    }

    public void autoUpdateApps(boolean updateAvailable) {
        // Simulate detecting updates and scheduling them
        List<App> apps = appInstallationRepository.findAll();
        apps.forEach(app -> {
            if (updateAvailable) { // Simulate new version availability
                app.setVersion(app.getVersion() + ".1");
                app.setState(AppStateEnum.Scheduled.getValue());
                app.setRetryCount(0);
                app.setLastUpdated(LocalDateTime.now());
                appInstallationRepository.save(app);
            }
        });
    }

    public void rescheduleFailedApps() {
        List<App> failedApps = appInstallationRepository.findByState(AppStateEnum.Error.getValue());
        failedApps.forEach(app -> {
            app.setState(AppStateEnum.Scheduled.getValue());
            app.setRetryCount(0);
            appInstallationRepository.save(app);
        });
    }
}
