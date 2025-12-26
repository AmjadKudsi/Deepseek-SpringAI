// create two separate ChatService instances, each with a different personality

package com.codesignal.deepseektutor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.ChatClient;

@SpringBootApplication
public class DeepSeekTutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepSeekTutorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ChatClient.Builder chatClientBuilder, ApplicationContext context) {
        return args -> {
            // Create friendly tutor service with default persona
            String friendlyPrompt = "You are an enthusiastic and encouraging tutor who loves to use analogies, stories, and playful language. You make complex topics fun and accessible, always maintaining a positive and supportive tone. You use emojis occasionally and celebrate student progress with excitement.";
            
            // TODO: Pass friendlyPrompt to the ChatService
            ChatService friendlyTutor = new ChatService(chatClientBuilder, friendlyPrompt);

            // TODO: Create a very strict tutor service with a different persona
            // Hint: Define a strict persona prompt and create a new ChatService instance
            String strictPrompt = "You are a very strict, no-nonsense tutor. You give precise, formal, and concise explanations. You do not use emojis, humor, or encouragement. You focus only on correctness, definitions, and logical rigor.";
            
            ChatService strictTutor = new ChatService(chatClientBuilder, strictPrompt);

            // Ask friendly tutor a question
            String query1 = "Can you explain what a derivative is?";
            System.out.println("Query to friendly tutor: " + query1);
            String answer1 = friendlyTutor.askQuestion(query1);
            System.out.println("Friendly tutor answer: " + answer1);

            // TODO: Ask the strict tutor a question and print the response
            String query2 = "Can you explain what a derivative is?";
            System.out.println("Query to strict tutor: " + query2);
            String answer2 = strictTutor.askQuestion(query2);
            System.out.println("Strict tutor answer: " + answer2);            

            SpringApplication.exit(context, () -> 0);
        };
    }
}