package com.codesignal.deepseektutor;

import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
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
import java.util.List;
import java.util.UUID;

@Service
public class TutoringService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public TutoringService(ChatClient.Builder builder) {

        this.chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        String systemPrompt = loadSystemPrompt();

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

    public List<Message> getConversation(String sessionId) {
        return chatMemory.get(sessionId);
    }

    // Load the system prompt from a file
    private String loadSystemPrompt() {
        try {
            var resource = new ClassPathResource("data/system_prompt.txt");
            try (var in = resource.getInputStream();
                 var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load system prompt", e);
        }
    }
}