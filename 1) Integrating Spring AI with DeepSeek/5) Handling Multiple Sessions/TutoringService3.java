// create two distinct tutoring sessions, each using a different DeepSeek model, and interact with each independently

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import java.util.UUID;

@Service
public class TutoringService {

    // TODO: Remove this SYSTEM_PROMPT constant. The system prompt will be provided at runtime via a configure method.
//    private static final String SYSTEM_PROMPT =
//            "You are an experienced and patient tutor specialized in math and science, providing clear and concise explanations.";

    // TODO: Replace this ChatClient field with fields for ChatClient.Builder and ChatMemory.
//    private final ChatClient chatClient;

    private final ChatClient.Builder builder;
    private final ChatMemory chatMemory;

    private ChatClient chatClient;
    
    // TODO: Change the constructor to accept and store ChatClient.Builder and ChatMemory, but do not build the ChatClient here.
    public TutoringService(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.builder = builder;
        this.chatMemory = chatMemory;
    }

    // TODO: Add a configure(String systemPrompt, String modelName) method.
    //       Build and assign a ChatClient using the stored builder and chatMemory, setting the system prompt and model name dynamically.
    //       When building ChatOptions, make sure to set:
    //         .maxTokens(150)
    //         .temperature(0.6)
    //         .presencePenalty(0.5)
    //         .frequencyPenalty(0.9)
    //       Use MessageChatMemoryAdvisor and ChatOptions to configure the ChatClient.
    public void configure(String systemPrompt, String modelName) {
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        ChatOptions options = ChatOptions.builder()
                .model(modelName)
                .maxTokens(150)
                .temperature(0.6)
                .presencePenalty(0.5)
                .frequencyPenalty(0.9)
                .build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .defaultSystem(systemPrompt)
                .defaultOptions(options)
                .build();
    }        

    public String createSession() {
        return UUID.randomUUID().toString();
    }

    public String askQuestion(String sessionId, String question) {
        return chatClient.prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID, sessionId))
                .user(question)
                .call()
                .content();
    }
}