// Implement the askQuestion method in the ChatService class so that it sends a user message to the AI and returns the assistant's reply as a string. Use the chatClient field to send the prompt and return the assistant's response all while maintaining conversation history

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
            String firstQuery = "My favorite color is blue. Can you remember that?";
            System.out.println("Query: " + firstQuery);
            // TODO: Call chatService.askQuestion(firstQuery) and print the answer
            String answer = chatService.askQuestion(firstQuery);
            System.out.println("Answer: " + answer);

            // Now, using the previous information, what color did I say was my favorite?
            String followUpQuery = "What color did I say was my favorite earlier?";
            System.out.println("Query: " + followUpQuery);
            // TODO: Call chatService.askQuestion(followUpQuery) and print the follow-up answer
            String followUp = chatService.askQuestion(followUpQuery);
            System.out.println("Follow-up: " + followUp);

            SpringApplication.exit(context, () -> 0);
        };
    }
}