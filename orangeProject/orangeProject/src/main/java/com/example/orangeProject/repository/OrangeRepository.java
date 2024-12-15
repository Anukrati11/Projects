package com.example.orangeProject.repository;

import org.springframework.stereotype.Component;


@Component
public class OrangeRepository {

    public String getHello(){
        return "Hello";
    }

    public String printId(Long id){
        System.out.println("Orange Id: "+id);
        return "id";
    }

}
