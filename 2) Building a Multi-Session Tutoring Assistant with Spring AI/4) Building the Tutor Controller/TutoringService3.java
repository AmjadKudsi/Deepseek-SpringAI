# extend your tutoring API to support more advanced, "thinking" questions using the DeepSeek-R1 model

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
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

    private final ChatMemory chatMemory;
    private final ChatClient.Builder clientBuilder;

    public TutoringService(ChatClient.Builder builder) {
        this.chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
        this.clientBuilder = builder;
    }

    // Create a new tutoring session with a specific prompt template
    public String createSession(String promptName) {
        String sessionId = UUID.randomUUID().toString();
        String systemPrompt = loadPrompt(promptName);

        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        ChatClient chatClient = clientBuilder
                .defaultAdvisors(advisor)
                .defaultSystem(systemPrompt)
                .build();

        // Optionally cache chatClient per session if needed
        chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, sessionId))
                .system(systemPrompt); // initializes memory

        return sessionId;
    }

    public String askQuestion(String sessionId, String question) {
        return clientBuilder.build().prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, sessionId))
                .user(question)
                .call()
                .content();
    }

    // TODO: Add a method to handle thinking questions using the DeepSeek-R1 model
    //      - The method should take (sessionId, question) as parameters.
    //      - It should call the LLM with model "deepseek-ai/DeepSeek-R1" (use ChatOptions).
    //      - Set options like maxTokens, temperature, presencePenalty, and frequencyPenalty for more advanced answers.
    //      - Return the model's answer as a String.
    public String askThinkingQuestion(String sessionId, String question) {
        return clientBuilder.build().prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, sessionId))
                .user(question)
                .options(ChatOptions.builder()
                        .model("deepseek-ai/DeepSeek-R1")
                        .maxTokens(2048)
                        .temperature(0.7)
                        .presencePenalty(0.5)
                        .frequencyPenalty(0.5)
                        .build())
                .call()
                .content();
    }    

    public List<Message> getConversation(String sessionId) {
        return chatMemory.get(sessionId);
    }

    private String loadPrompt(String promptName) {
        try {
            var resource = new ClassPathResource("data/prompts/" + promptName + ".txt");
            try (var in = resource.getInputStream();
                 var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Prompt not found: " + promptName, e);
        }
    }
}