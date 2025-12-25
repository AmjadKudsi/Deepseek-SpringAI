// update ChatService to keep track of the conversation history and add a method to display it

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
            String answer = chatService.askQuestion(firstQuery);

            // Now, using the previous information, what is that number multiplied by 2?
            String followUpQuery = "Based on the number I mentioned earlier, what is it multiplied by 2?";
            String followUp = chatService.askQuestion(followUpQuery);

            // TODO: Call chatService.printConversationHistory() here to display the conversation history
            chatService.printConversationHistory();

            SpringApplication.exit(context, () -> 0);
        };
    }
}