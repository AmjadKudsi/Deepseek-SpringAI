package com.codesignal.deepseektutor;

import org.springframework.ai.chat.messages.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeepSeekTutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepSeekTutorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(StudentService studentService, ApplicationContext ctx) {
        return args -> {
            // The explainer prompt has been fixed to allow direct answers to factual questions.

            String studentId = "student1";
            String sessionId = studentService.createSession(studentId, "explainer");

            String[] questions = {
                "What is the capital of France?",
                "Who wrote Hamlet?",
                "What is photosynthesis?"
            };

            for (String question : questions) {
                System.out.println("[Explainer] Q: " + question);
                String answer = studentService.askQuestion(studentId, sessionId, question);
                System.out.println("[Explainer] A: " + answer);
                System.out.println();
            }

            SpringApplication.exit(ctx, () -> 0);
        };
    }

}