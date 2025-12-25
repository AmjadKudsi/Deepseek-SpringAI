// extend the provided code so that after sending an initial user message to the AI, you send a follow-up question
// The AI should be able to use the context from the first message when answering the follow-up

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
            // TODO: Start a conversation with an initial user message
            // Send the message "My favorite animal is the dolphin." to the AI
            // Print the assistant's response to the initial message
            String message = "My favorite animal is the dolphin";
            System.out.println("Message: " + message);
            String answer = chatService.askQuestion(message);
            System.out.println("Answer: " + answer);

            // TODO: Send the follow-up message "What animal did I say I liked?" to the AI
            // Print the assistant's response to the follow-up message
            String followUpQuery = "What animal did I say I liked?";
            System.out.println("Query: " + followUpQuery);
            String followUp = chatService.askQuestion(followUpQuery);
            System.out.println("Follow-up: " + followUp);
            

            SpringApplication.exit(context, () -> 0);
        };
    }
}