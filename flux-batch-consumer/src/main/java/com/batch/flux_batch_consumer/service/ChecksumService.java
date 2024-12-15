package com.batch.flux_batch_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ChecksumService {


    @Autowired
    private SuviElimentationService suviElimentationService;

    public String generateChecksum(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (DigestInputStream dis = new DigestInputStream(Files.newInputStream(Paths.get(filePath)), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

    public boolean checksumExists(String checksum) {
        // query to check if the checksum exists in the database
        if(suviElimentationService.existsByShsum(checksum)){
            return true;
        }

        return false;
    }
}