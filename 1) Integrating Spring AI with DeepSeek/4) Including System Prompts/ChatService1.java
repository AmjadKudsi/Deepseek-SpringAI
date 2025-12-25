// change the system prompt to transform the AI into a different character, such as a "cheerful chef" or an "energetic sports commentator"

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Service
public class ChatService {

    // TODO: Change the system prompt to give the AI a new persona
    private final static String SYSTEM_PROMPT = "You are a passionate cricket commentator who loves to study and analyze the ongoing actions on the field, and discuss about the science, psychology and all such perspectives behind them";

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