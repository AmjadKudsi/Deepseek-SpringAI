# create a brand new tutor persona by writing a custom system prompt
# update the application to use it in a session and ask a few questions that show off your tutor’s unique approach

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
    public CommandLineRunner runner(StudentService studentService, ApplicationContext ctx) {
        return args -> {
            
            // TODO: Create a session using your custom prompt template (e.g., "custom_tutor")
            String studentId = "student1";
            String sessionId = studentService.createSession(studentId, "custom_tutor");


            // TODO: Ask a few questions that show off your tutor's unique style
            studentService.askQuestion(studentId, sessionId, "Can you explain what a derivative is? I've never taken calculus before.");
            studentService.askQuestion(studentId, sessionId, "Why does E=mc² actually matter in real life?");

            studentService.getHistory(studentId, sessionId).forEach(msg ->
                System.out.println("[" + msg.getMessageType() + "]: " + msg.getText())
            );

            SpringApplication.exit(ctx, () -> 0);
        };
    }

}