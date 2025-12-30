// explore and understand how these new tutor personas (quizmaster and math_tutor) affect the AI’s responses

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
            // Session 1: Explainer
            String studentId1 = "student1";
            String sessionId1 = studentService.createSession(studentId1, "explainer");
            System.out.println("[Explainer] Q: What is photosynthesis?");
            System.out.println("[Explainer] A: " + studentService.askQuestion(
                    studentId1, sessionId1, "What is photosynthesis?"));

            // Session 2: Quizmaster
            String studentId2 = "student2";
            // TODO: Create the session with prompt "quizmaster"
            String sessionId2 = studentService.createSession(studentId2, "quizmaster");
            // TODO: Ask a question in this session
            System.out.println("[Quizmaster] Q: What is the capital of France?");
            System.out.println("[Quizmaster] A: " + studentService.askQuestion(
                studentId2, sessionId2, "What is the capital of France?"
            ));

            // Session 3: Math Tutor
            String studentId3 = "student3";
            // TODO: Create the session with prompt "math_tutor"
            String sessionId3 = studentService.createSession(studentId3, "math_tutor");
            // TODO: Ask a question in this session
            System.out.println("[Math Tutor] Q: How do you solve 2x + 3 = 11?");
            System.out.println("[Math Tutor] A: " + studentService.askQuestion(
                    studentId3, sessionId3, "How do you solve 2x + 3 = 11?"));

            SpringApplication.exit(ctx, () -> 0);
        };
    }

}