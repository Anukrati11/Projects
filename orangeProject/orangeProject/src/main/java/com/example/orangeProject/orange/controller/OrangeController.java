package com.example.orangeProject.orange.controller;

import com.example.orangeProject.service.OrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orange")
public class OrangeController {

    @Autowired
    private OrangeService orangeService;

    @GetMapping("/hello")
    public String hello(){
        return orangeService.getOrangeService();
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> post(@PathVariable Long id){
        try{
            orangeService.printId(id);
            return ResponseEntity.ok("Post request with id: "+id);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error in post request");
        }

    }

}
