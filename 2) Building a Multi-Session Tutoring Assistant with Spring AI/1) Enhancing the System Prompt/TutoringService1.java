// implement a method to load the system prompt from the file
// In the constructor , use your method to load the system prompt and store its return value in a variable

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
        // TODO: Load the system prompt from the file "data/system_prompt.txt"
        // TODO: Use your method to load the system prompt, with a fallback to "You are a helpful assistant."
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

    // TODO: Implement a method to load the system prompt from the specified file path.
    // - Try to read and return the text from the file.
    // - If an exception occurs, print the error and return "You are a helpful assistant."
    // private String loadSystemPrompt(String filePath) { ... }
    private String loadSystemPrompt(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String text = new String(bytes, StandardCharsets.UTF_8).trim();
                if (text.isEmpty()) {
                    return "You are a helpful assistant.";
                }
                return text;
            }
        } catch (IOException e) {
            System.err.println("Error reading system prompt file: " + e.getMessage());
            return "You are a helpful assistant.";
        }
    }    
}