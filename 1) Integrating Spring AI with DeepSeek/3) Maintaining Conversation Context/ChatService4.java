// update ChatService to keep track of the conversation history and add a method to display it

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Service
public class ChatService {

    // TODO: Add a private ChatMemory field to store the conversation history
    private final ChatMemory chatMemory;

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {

        // TODO: Change 'memory' to a private field and assign it to the ChatMemory field
        this.chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        // Advisor that injects and updates that memory automatically
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .build();
    }

    public String askQuestion(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    // TODO: Add a method called printConversationHistory that prints each message's role and content from the conversation history
    // Hint: Use chatMemory.get("default") to get the list of messages in the conversation.
    // Hint: Use message.getMessageType() to determine if the message is from the USER or ASSISTANT.
    // Hint: Use message.getText() to get the actual content of each message.
    public void printConversationHistory() {
        for (var message : chatMemory.get("default")) {
            System.out.println(message.getMessageType() + ": " + message.getText());
         }
    }
}