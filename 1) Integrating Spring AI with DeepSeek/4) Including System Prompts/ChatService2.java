// find and fix this bug so the AI behaves as Albert Einstein according to the SYSTEM_PROMPT

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Service
public class ChatService {

    private final static String SYSTEM_PROMPT = "You are Albert Einstein. Always answer as if you are Albert Einstein, using first person. If a user asks about relativity, mention your work proudly.";

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {

        // Create a ChatMemory that keeps the last 20 messages
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        // Advisor that injects and updates that memory automatically
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }

    public String askQuestion(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}