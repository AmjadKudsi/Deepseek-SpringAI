// Complete the given code to sends a prompt to the model with custom parameters and prints the AI's response

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
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

            // TODO Step 1: Build ChatOptions with:
            //   - model("deepseek-ai/DeepSeek-V3")
            //   - maxTokens(100)
            //   - temperature(1.7)
            //   - presencePenalty(0.5)
            //   - frequencyPenalty(0.2)
            ChatOptions options = ChatOptions.builder()
                .model("deepseek-ai/DeepSeek-V3")
                .maxTokens(100)
                .temperature(1.7)
                .presencePenalty(0.5)
                .frequencyPenalty(0.2)
                .build();
            

            // TODO Step 2: Create a UserMessage with the text:
            //   "Describe a sunset over the ocean"
            UserMessage userMessage = UserMessage.builder()
                .text("Describe a sunset over the ocean")
                .build();

            // TODO Step 3: Construct a Prompt using the UserMessage and ChatOptions
            Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .chatOptions(options)
                .build();

            // TODO Step 4: Call the model with the Prompt, get the assistant's reply,
            //   and print the reply text to the console
            AssistantMessage reply = chatModel.call(prompt)
                .getResults()
                .getFirst()
                .getOutput();
                
            System.out.println("Assistant: " + reply.getText());    

            SpringApplication.exit(context, () -> 0);
        };
    }
}