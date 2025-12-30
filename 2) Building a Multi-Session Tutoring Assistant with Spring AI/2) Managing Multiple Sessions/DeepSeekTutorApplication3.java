// implement two essential methods in the StudentService class: getHistory, listSessions and demonstrate both methods

package com.codesignal.deepseektutor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.ai.chat.messages.Message;

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

            studentService.askQuestion(studentId, sessionId, "Hi, can you help me with calculus?");
            studentService.askQuestion(studentId, sessionId, "What is the derivative of x^2?");

            // TODO: List and print all sessions for the student
            System.out.println("Sessions for student " + studentId + ":");
            studentService.listSessions(studentId).forEach(System.out::println);

            // TODO: Retrieve and print the conversation history for the created session
            System.out.println("\nConversation history for session " + sessionId + ":");
            for (Message message : studentService.getHistory(studentId, sessionId)) {
                System.out.println(message);
            }

            SpringApplication.exit(ctx, () -> 0);
        };
    }
}