package com.example.orangeProject.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
import org.springframework.context.annotation.Primary;

//@Entity
public class OrangeEntity {

//    @Id
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;



    public OrangeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }




}
