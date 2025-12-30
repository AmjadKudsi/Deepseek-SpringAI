// implement session deletion and practice retrieving session history

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
            String studentId = "final_task_student";
            String sessionId = studentService.createSession(studentId);

            // TODO: List sessions before deletion
            System.out.println("Sessions before deletion: " + studentService.listSessions(studentId));

            // TODO: Print conversation history before deletion
            System.out.println("Conversation history for session " + sessionId + ":");
            for (Message message : studentService.getHistory(studentId, sessionId)) {
                System.out.println(message);
            }            

            // TODO: Delete the session
            studentService.deleteSession(studentId, sessionId);

            // TODO: List sessions after deletion
            System.out.println("Sessions after deletion: " + studentService.listSessions(studentId));

            SpringApplication.exit(ctx, () -> 0);
        };
    }
}