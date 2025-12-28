// Your educational AI assistant’s support hours and contact email have recently changed
// ensure the system prompt includes these details so the tutor provides accurate information to students

package com.codesignal.deepseektutor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class TutoringService {

    private final ChatClient chatClient;

    public TutoringService(ChatClient.Builder builder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        String systemPrompt = loadSystemPrompt("data/system_prompt.txt");
        this.chatClient = builder
                .defaultAdvisors(advisor)
                .defaultSystem(systemPrompt)
                .build();
    }

    // Create a new tutoring session and return its unique ID.
    public String createSession() {
        return UUID.randomUUID().toString();
    }

    // Send a user question under a given session ID and return the assistant's reply.
    public String askQuestion(String sessionId, String question) {
        return chatClient.prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID, sessionId))
                .user(question)
                .call()
                .content();
    }

    private String loadSystemPrompt(String filePath) {
        try {
            var resource = new ClassPathResource(filePath);
            return FileCopyUtils.copyToString(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            System.err.println("Failed to load system prompt: " + e.getMessage());
            return "You are a helpful assistant.";
        }
    }
}