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

            // TODO: Change the question below to request a discount or custom price
            String studentQuestion = "Can you give me a discount or a custom Premium price for my school?";
            String tutorResponse = tutoringService.askQuestion(sessionId, studentQuestion);

            System.out.println("Student:\n" + studentQuestion + "\n");
            System.out.println("Tutor:\n" + tutorResponse);

            // Exit the application after demonstration
            SpringApplication.exit(ctx, () -> 0);
        };
    }

}