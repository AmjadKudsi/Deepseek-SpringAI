// modify the existing prompt in the DeepSeekTutorApplication class from asking for a joke to asking for a fun fact

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
            // TODO: Change the prompt to ask for a fun fact instead of a joke
            String prompt = "Hi, can you tell me a fun fact?";
            String reply = chatModel.call(prompt);
            System.out.println("Prompt: " + prompt);
            System.out.println("Response: " + reply);

            // Exit application
            SpringApplication.exit(context, () -> 0);
        };
    }
}