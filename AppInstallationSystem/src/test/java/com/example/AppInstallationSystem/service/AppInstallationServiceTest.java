package com.example.AppInstallationSystem.service;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.entity.enumEntity.AppStateEnum;
import com.example.AppInstallationSystem.exception.MessagingException;
import com.example.AppInstallationSystem.repository.AppInstallationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppInstallationServiceTest {

    @Mock
    private AppInstallationRepository appInstallationRepository;

    @Mock
    private EmailNotificationService emailService;

    @InjectMocks
    private AppInstallationService appInstallationService;

    @Mock
    private Math math;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessScheduledApps() {
        App app = new App();
        app.setState(AppStateEnum.Scheduled.getValue());
        when(appInstallationRepository.findByState(AppStateEnum.Scheduled.getValue())).thenReturn(Arrays.asList(app));

        appInstallationService.processScheduledApps();

        verify(appInstallationRepository, times(1)).findByState(AppStateEnum.Scheduled.getValue());
        verify(appInstallationRepository, times(2)).save(app);
    }

    @Test
    void testAutoUpdateApps() {
        App app = new App();
        app.setVersion("1.0");
        when(appInstallationRepository.findAll()).thenReturn(Arrays.asList(app));

        appInstallationService.autoUpdateApps(true);

        verify(appInstallationRepository, times(1)).findAll();
        verify(appInstallationRepository, atLeastOnce()).save(app);
    }

    @Test
    void testRescheduleFailedApps() {
        App app = new App();
        app.setState(AppStateEnum.Error.getValue());
        when(appInstallationRepository.findByState(AppStateEnum.Error.getValue())).thenReturn(Arrays.asList(app));

        appInstallationService.rescheduleFailedApps();

        verify(appInstallationRepository, times(1)).findByState(AppStateEnum.Error.getValue());
        verify(appInstallationRepository, times(1)).save(app);
    }

}