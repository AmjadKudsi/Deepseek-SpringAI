// test the error handling in the application by intentionally providing an incorrect file path for the system prompt

package com.codesignal.deepseektutor;

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
    public CommandLineRunner runner(TutoringService tutoringService,
                                    ApplicationContext ctx) {
        return args -> {
            // Create a single tutoring session
            String sessionId = tutoringService.createSession();

            // Demonstrate the system prompt in action with a sample question
            String studentQuestion = "What is your role?";
            String tutorResponse = tutoringService.askQuestion(sessionId, studentQuestion);

            System.out.println("Student:\n" + studentQuestion + "\n");
            System.out.println("Tutor:\n" + tutorResponse);

            // Exit the application after demonstration
            SpringApplication.exit(ctx, () -> 0);
        };
    }

}