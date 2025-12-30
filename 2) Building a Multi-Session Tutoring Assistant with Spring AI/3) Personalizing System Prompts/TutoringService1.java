// extend your application to support multiple prompt templates for tutoring sessions

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

    // TODO: Update createSession to accept a promptName and use it to load the correct prompt file
    public String createSession(String promptName) {
        String sessionId = UUID.randomUUID().toString();

        // Load the system prompt from data/prompts/{promptName}.txt
        String systemPrompt = loadSystemPrompt(promptName);

        // Seed chat memory for this session with the system prompt
        chatMemory.add(sessionId, new org.springframework.ai.chat.messages.SystemMessage(systemPrompt));

        return sessionId;
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

    // TODO: Update loadPrompt to load the contents of data/prompts/{promptName}.txt as the system prompt
    private String loadSystemPrompt(String... promptName) {
        // If called with no args (constructor path), return a simple default.
        // The real per-session prompt will be set in createSession.
        if (promptName == null || promptName.length == 0 || promptName[0] == null || promptName[0].isBlank()) {
            return "You are a helpful tutoring assistant.";
        }

        String name = promptName[0];

        try {
            var resource = new ClassPathResource("data/prompts/" + name + ".txt");
            try (var in = resource.getInputStream();
                var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Prompt not found: " + name, e);
        }
    }

}