// add one more question to the second tutoring session using the existing TutoringService

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
            // Create two independent tutoring sessions
            String session1 = tutoringService.createSession();
            String session2 = tutoringService.createSession();

            // Session 1: Ask two general knowledge questions
            System.out.println("Session 1 → General tutoring questions");
            System.out.println(
                    tutoringService.askQuestion(session1,
                            "Can you explain the water cycle?"));
            System.out.println(
                    tutoringService.askQuestion(session1,
                            "What is the Pythagorean theorem?"));

            // Session 2: Ask two different general knowledge questions
            System.out.println("Session 2 → General tutoring questions");
            System.out.println(
                    tutoringService.askQuestion(session2,
                            "How does photosynthesis work?"));
            System.out.println(
                    tutoringService.askQuestion(session2,
                            "What are Newton's three laws of motion?"));

            // TODO: Ask a third question in session2 and print the response
            System.out.println(
                    tutoringService.askQuestion(session2,
                            "How is the subject of Science divided into different fields? Can you introduce each one?"));
                            
            // Exit the application after demonstration
            SpringApplication.exit(ctx, () -> 0);
        };
    }
}