// create an AI persona with the character of a wise philosopher.

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

            // TODO: Ask a deep philosophical question to test the AI's new persona
            // Print both the query and the AI's response
            String query = "Who are you? And what are your thoughts on 'What is good? How should we live?'";
            System.out.println("Query: " + query);
            String answer = chatService.askQuestion(query);
            System.out.println("Answer: " + answer);            

            SpringApplication.exit(context, () -> 0);
        };
    }
}