package com.example.AppInstallationSystem.init;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.entity.enumEntity.AppStateEnum;
import com.example.AppInstallationSystem.repository.AppInstallationRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * This service initializes the database with some sample data.
 */
@Service
public class DataInitializationService {

    private final AppInstallationRepository appInstallationRepository;

    public DataInitializationService(AppInstallationRepository appInstallationRepository) {
        this.appInstallationRepository = appInstallationRepository;
    }

    //Executes earlier in the lifecycle, ideal for component-specific initialization.
    @PostConstruct
    public void initializeData() {

        App app1 = new App();
        app1.setName("Google Pay");
        app1.setRetryCount(0);
        app1.setState(AppStateEnum.Scheduled.getValue());
        app1.setVersion("1.0");

        App app2 = new App();
        app2.setName("WhatsApp");
        app2.setRetryCount(0);
        app2.setState(AppStateEnum.PickedUp.getValue());
        app2.setVersion("1.0");

        App app3 = new App();
        app3.setName("Instagram");
        app3.setRetryCount(1);
        app3.setState(AppStateEnum.Scheduled.getValue());
        app3.setVersion("1.0");

        App app4 = new App();
        app4.setName("Facebook");
        app4.setRetryCount(0);
        app4.setState(AppStateEnum.Completed.getValue());
        app4.setVersion("1.0");

        App app5 = new App();
        app5.setName("Twitter");
        app5.setRetryCount(0);
        app5.setState(AppStateEnum.Error.getValue());
        app5.setVersion("1.0");

        App app6 = new App();
        app6.setName("LinkedIn");
        app6.setRetryCount(2);
        app6.setState(AppStateEnum.Scheduled.getValue());
        app6.setVersion("1.0");

        App app7 = new App();
        app7.setName("Snapchat");
        app7.setRetryCount(0);
        app7.setState(AppStateEnum.Scheduled.getValue());
        app7.setVersion("1.0");

        appInstallationRepository.save(app1);
        appInstallationRepository.save(app2);
        appInstallationRepository.save(app3);
        appInstallationRepository.save(app4);
        appInstallationRepository.save(app5);
        appInstallationRepository.save(app6);
        appInstallationRepository.save(app7);

    }
}
