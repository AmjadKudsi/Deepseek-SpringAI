// create two separate ChatService instances, each with a different personality

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

public class ChatService {

    private final String systemPrompt;
    private final ChatClient chatClient;

    // TODO: Update the constructor to accept a system prompt parameter
    public ChatService(ChatClient.Builder builder, String systemPrompt) {
        this.systemPrompt = systemPrompt;
        
        // Create a ChatMemory that keeps the last 20 messages
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        // Advisor that injects and updates that memory automatically
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .defaultSystem(systemPrompt)
                .build();
    }

    public String askQuestion(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}