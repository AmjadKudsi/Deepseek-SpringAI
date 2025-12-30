// implement the askQuestion method in the StudentService class and demonstrate its usage in the CommandLineRunner

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
    public CommandLineRunner runner(StudentService studentService,
                                    ApplicationContext ctx) {
        return args -> {
            String studentId = "test_student";
            String sessionId = studentService.createSession(studentId);

            // TODO: Create a test question (e.g., "Hello, can you help me with basic algebra?")
            String question = "Hello, I need help with geometry.";

            // TODO: Call askQuestion on studentService using the studentId, sessionId, and test question
            String response = studentService.askQuestion(studentId, sessionId, question);

            // TODO: Print the assistant's response
            System.out.println("Assistant: " + response);

            // Exit the application
            SpringApplication.exit(ctx, () -> 0);
        };
    }
}