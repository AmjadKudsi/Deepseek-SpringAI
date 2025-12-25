// Implement the askQuestion method in the ChatService class so that it sends a user message to the AI and returns the assistant's reply as a string. Use the chatClient field to send the prompt and return the assistant's response all while maintaining conversation history

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        // ChatClient and memory setup already provided
        ChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(memory)
                .build();
        this.chatClient = builder
                .defaultAdvisors(advisor)
                .build();
    }

    // TODO: Implement the askQuestion method to send the user question and return the assistant's reply
    public String askQuestion(String question) {
        return chatClient.prompt()
        .user(question)
        .call()
        .content();
    }

}