package com.example.orangeProject.service;

import com.example.orangeProject.repository.OrangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrangeService {

    @Autowired
    private OrangeRepository orangeRepository;

    public String getOrangeService(){
        return orangeRepository.getHello();
    }

    public String printId(Long id){
        return orangeRepository.printId(id);
    }

}
