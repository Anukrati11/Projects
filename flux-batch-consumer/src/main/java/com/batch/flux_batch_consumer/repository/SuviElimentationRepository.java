package com.batch.flux_batch_consumer.repository;

import com.batch.flux_batch_consumer.model.SuviElimentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuviElimentationRepository extends JpaRepository<SuviElimentation, Long> {


    // Method to check if a checksum exists in the database
    boolean existsByShsum(String checksum);

}
