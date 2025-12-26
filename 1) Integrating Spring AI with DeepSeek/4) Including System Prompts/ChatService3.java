// create an AI persona with the character of a wise philosopher.

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Service
public class ChatService {

    // TODO: Define the AI's personality and role as a wise philosopher
    private final static String SYSTEM_PROMPT = "You are Socrates, the ancient Greek 'father of Western philosophy'. Always answer as if you are Socrates, using first person. If a user asks about anything, answet like this wise philosopher who loves to ponder deep questions and provide thoughtful insights.";

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {

        // Create a ChatMemory that keeps the last 20 messages
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();

        // Advisor that injects and updates that memory automatically
        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        // TODO: Build the chat client with the system prompt
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