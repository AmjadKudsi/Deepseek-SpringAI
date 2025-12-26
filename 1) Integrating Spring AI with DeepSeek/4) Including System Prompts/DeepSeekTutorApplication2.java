// find and fix this bug so the AI behaves as Albert Einstein according to the SYSTEM_PROMPT

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
    public CommandLineRunner runner(ChatService chatService, ApplicationContext context) {
        return args -> {

            // Asking a question to the tutor
            String query = "Who are you? And what are you most famous for?";
            System.out.println("Query: " + query);
            String answer = chatService.askQuestion(query);
            System.out.println("Answer: " + answer);

            SpringApplication.exit(context, () -> 0);
        };
    }
}