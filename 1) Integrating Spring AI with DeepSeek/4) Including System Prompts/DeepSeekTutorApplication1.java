// change the system prompt to transform the AI into a different character, such as a "cheerful chef" or an "energetic sports commentator"

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

            // Ask a question to test the AI's new persona
            String query = "What do you think is the reason behind India losing the World Cup Final 2023?";
            System.out.println("Query: " + query);
            String answer = chatService.askQuestion(query);
            System.out.println("Answer: " + answer);

            SpringApplication.exit(context, () -> 0);
        };
    }
}