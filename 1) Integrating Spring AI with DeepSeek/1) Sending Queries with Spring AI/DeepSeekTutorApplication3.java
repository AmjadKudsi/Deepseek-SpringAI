// identify and correct the issue that prevents the AI's response from being displayed

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.model.ChatModel;
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
    public CommandLineRunner runner(ChatModel chatModel, ApplicationContext context) {
        return args -> {
            // Define a simple user message to test the API
            String prompt = "If animals could talk, what would a dog say about its day?";

            // Get the AI's response (corrected the syntax here)
            String reply = chatModel.call(prompt);

            // Show both sides of the conversation
            System.out.println("Prompt: " + prompt);
            System.out.println("Response: " + reply);

            // Exit application
            SpringApplication.exit(context, () -> 0);
        };
    }
}