// add one more question to the second tutoring session using the existing TutoringService

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import java.util.UUID;

@Service
public class TutoringService {

    private static final String SYSTEM_PROMPT =
            "You are an experienced and patient tutor specialized in math and science, providing clear and concise explanations.";

    private final ChatClient chatClient;

    public TutoringService(ChatClient.Builder builder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .defaultSystem(SYSTEM_PROMPT)
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
}