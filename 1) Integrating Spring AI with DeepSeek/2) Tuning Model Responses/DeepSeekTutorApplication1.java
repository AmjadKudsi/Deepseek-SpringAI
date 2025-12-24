// Run the provided code as it is
package com.codesignal.deepseektutor;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
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
            String userText = "Describe a sunset over the ocean";

            // 1) Wrap your text in a UserMessage
            UserMessage userMessage = UserMessage.builder()
                    .text(userText)
                    .build();

            // 2) Construct the Prompt with just the message (no extra options)
            Prompt prompt = Prompt.builder()
                    .messages(userMessage)
                    .build();

            // 3) Call the model with default settings
            AssistantMessage reply = chatModel.call(prompt)
                    .getResults()
                    .getFirst()
                    .getOutput();

            System.out.println("Assistant: " + reply.getText());

            SpringApplication.exit(context, () -> 0);
        };
    }
}