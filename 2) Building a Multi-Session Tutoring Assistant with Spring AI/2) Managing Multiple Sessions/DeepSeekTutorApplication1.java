// implement the createSession method

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
            // TODO: Define a test studentId, e.g., "test_student"
            String studentId = "test_student";

            // TODO: Use createSession to create a new session for the student
            // - Call createSession on studentService with the test studentId
            // - Store the returned sessionId
            String sessionId = studentService.createSession(studentId);

            // TODO: Check if the session was created
            // - If the sessionId is not null or empty, print "Session successfully created!"
            // - Otherwise, print "Failed to create session."
            if (sessionId != null && !sessionId.trim().isEmpty()) {
                System.out.println("Session successfully created!");
                System.out.println("studentId: " + studentId ", sessionId: " + sessionId);
            } else {
                System.out.println("Failed to create session.")
            }

            // Exit the application
            SpringApplication.exit(ctx, () -> 0);
        };
    }
}