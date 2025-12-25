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

        // Create a ChatMemory that keeps the last 20 messages
        ChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        // Advisor that injects and updates that memory automatically
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(memory)
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
}