// update the TutoringService so it can manage multiple independent tutoring sessions, similar to the lesson

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

        // TODO: Add a createSession() method that returns a unique session ID.
        public String createSession() {
                return UUID.randomUUID().toString();
        }

    // TODO: Change askQuestion to accept a sessionId and a question, and use the sessionId to manage conversation history.
    public String askQuestion(String sessionId, String question) {
        return chatClient.prompt()
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID, sessionId))
                .user(question)
                .call()
                .content();
    }
}