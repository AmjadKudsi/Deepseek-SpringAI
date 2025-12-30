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
            String promptName = "math_tutor";

            // Create a new session with the "math_tutor" prompt template
            String sessionId = studentService.createSession(studentId, promptName);

            // Ask a math question in the new session
            String question = "Can you help me understand the Pythagorean theorem?";
            String answer = studentService.askQuestion(studentId, sessionId, question);

            System.out.println("Assistant: " + answer);

            SpringApplication.exit(ctx, () -> 0);
        };
    }
}