package com.example.AppInstallationSystem.repository;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.entity.enumEntity.AppStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInstallationRepository extends JpaRepository<App, Long> {
    List<App> findByState(String state);
}
