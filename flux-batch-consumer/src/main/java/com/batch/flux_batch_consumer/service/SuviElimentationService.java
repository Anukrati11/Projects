package com.batch.flux_batch_consumer.service;

import com.batch.flux_batch_consumer.repository.SuviElimentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuviElimentationService {

    @Autowired
    private SuviElimentationRepository suviElimentationRepository;

    boolean existsByShsum(String checksum){
        return suviElimentationRepository.existsByShsum(checksum);
    }
}
