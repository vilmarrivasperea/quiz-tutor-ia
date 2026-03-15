package com.tuapp.quiz_tutor;

import com.tuapp.quiz_tutor.config.GroqProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GroqProperties.class)
public class QuizTutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizTutorApplication.class, args);
	}

}
