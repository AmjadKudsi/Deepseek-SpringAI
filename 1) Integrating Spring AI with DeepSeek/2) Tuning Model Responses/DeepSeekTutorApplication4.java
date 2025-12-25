// update the code by adding a parameter that encourages the AI to introduce new topics in its responses

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
            String userText = "Tell me about a variety of topics that most people don't usually discuss.";

            // 1) Build your runtime options
            ChatOptions options = ChatOptions.builder()
                    .model("deepseek-ai/DeepSeek-V3")
                    .maxTokens(100)
                    .temperature(0.2)
                    // TODO: Add the appropriate parameter to encourage new topics
                    .presencePenalty(0.5)
                    .build();

            // 2) Wrap your text in a UserMessage
            UserMessage userMessage = UserMessage.builder()
                    .text(userText)
                    .build();

            // 3) Construct the Prompt with message + options
            Prompt prompt = Prompt.builder()
                    .messages(userMessage)
                    .chatOptions(options)
                    .build();

            // 4) Call the model
            AssistantMessage reply = chatModel.call(prompt)
                    .getResults()
                    .getFirst()
                    .getOutput();

            System.out.println("Assistant: " + reply.getText());

            SpringApplication.exit(context, () -> 0);
        };
    }
}