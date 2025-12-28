// Use the loaded system prompt to set the system prompt for the chat client

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

            // TODO: Ask the question "Who am I speaking with?" and print the response
            String studentQuestion = "Who am I speaking with?";
            String tutorResponse = tutoringService.askQuestion(sessionId, studentQuestion);

            System.out.println("User:\n" + studentQuestion + "\n");
            System.out.println("Chatbot:\n" + tutorResponse);

            // Exit the application after demonstration
            SpringApplication.exit(ctx, () -> 0);
        };
    }

}