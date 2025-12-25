// Find and fix the bug in ChatService so that conversation history is properly maintained and the assistant can remember earlier exchanges

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
            // First query
            String firstQuery = "I'm thinking of a number: 5. Can you remember it?";
            System.out.println("Query: " + firstQuery);
            String answer = chatService.askQuestion(firstQuery);
            System.out.println("Answer: " + answer);

            // Now, using the previous information, what is that number multiplied by 2?
            String followUpQuery = "Based on the number I mentioned earlier, what is it multiplied by 2?";
            System.out.println("Query: " + followUpQuery);
            String followUp = chatService.askQuestion(followUpQuery);
            System.out.println("Follow-up: " + followUp);

            SpringApplication.exit(context, () -> 0);
        };
    }
}