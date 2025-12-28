// Your educational AI assistant’s support hours and contact email have recently changed
// ensure the system prompt includes these details so the tutor provides accurate information to students

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

            // TODO: Ask for the support hours for the Basic Plan and what email to contact.
            String studentQuestion = "What are the support hours for the Basic Plan and what is the email to contact?";
            String tutorResponse = tutoringService.askQuestion(sessionId, studentQuestion);

            System.out.println("Student:\n" + studentQuestion + "\n");
            System.out.println("Tutor:\n" + tutorResponse);

            // Exit the application after demonstration
            SpringApplication.exit(ctx, () -> 0);
        };
    }

}