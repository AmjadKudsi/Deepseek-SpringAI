// create two distinct tutoring sessions, each using a different DeepSeek model, and interact with each independently

package com.codesignal.deepseektutor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeepSeekTutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepSeekTutorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(TutoringService tutoringService,
                                    ApplicationContext ctx) {
        return args -> {
            // TODO: Define a system prompt to use for both sessions.
            String systemPrompt =
                    "You are an experienced and patient tutor specialized in math and science, providing clear and concise explanations.";

            // TODO: Configure the tutoringService with DeepSeek-V3 and the prompt.
            tutoringService.configure(systemPrompt, "DeepSeek-V3");

            // TODO: Create the first session and ask a simple question using DeepSeek-V3.
            String session1 = tutoringService.createSession();
            String response1 = tutoringService.askQuestion(session1, "What is the difference between speed and velocity?");
            System.out.println("Session 1 (DeepSeek-V3): " + response1);
            
            // TODO: Configure the tutoringService with DeepSeek-R1 and the same prompt.
            tutoringService.configure(systemPrompt, "DeepSeek-R1");
            
            // TODO: Create the second session and ask a more detailed question using DeepSeek-R1.
            String session2 = tutoringService.createSession();
            String response2 = tutoringService.askQuestion(session2, "Explain why the derivative represents the instantaneous rate of change, with a simple example.");
            System.out.println("Session 2 (DeepSeek-R1): " + response2);            

            SpringApplication.exit(ctx, () -> 0);
        };
    }
}