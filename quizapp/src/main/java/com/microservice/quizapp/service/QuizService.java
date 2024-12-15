package com.microservice.quizapp.service;

import com.microservice.quizapp.dao.QuizRepository;
import com.microservice.quizapp.feign.QuizInterface;
import com.microservice.quizapp.model.QuestionWrapper;
import com.microservice.quizapp.model.Quiz;
import com.microservice.quizapp.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);


//    public ResponseEntity<String> createQuiz(String category, int number, String title){
//        try{
//            return new ResponseEntity<>(quizRepository.createQuiz(category, number, title), HttpStatus.CREATED);
//        }
//        catch (Exception ex){
//            logger.error(ex.getMessage());
//        }
//        return new ResponseEntity<>("Failed to create quiz", HttpStatus.BAD_REQUEST);
//    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }

//    public ResponseEntity<List<Quiz>> getAllQuiz(){
//
//    }


//    public ResponseEntity<Integer> calculateResult(Integer id, List<Res>)
//    generateQuestions
//    getQuestions
//        getScore
}
