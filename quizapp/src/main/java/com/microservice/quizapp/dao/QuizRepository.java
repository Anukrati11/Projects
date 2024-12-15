package com.microservice.quizapp.dao;

import com.microservice.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizRepository extends JpaRepository<Quiz, Integer> {


}
