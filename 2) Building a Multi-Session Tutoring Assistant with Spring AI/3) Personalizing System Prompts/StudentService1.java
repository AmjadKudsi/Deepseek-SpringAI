// extend your application to support multiple prompt templates for tutoring sessions

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StudentService {

    private final TutoringService tutoringService;
    private final Map<String, Set<String>> studentSessions = new ConcurrentHashMap<>();

    public StudentService(TutoringService tutoringService) {
        this.tutoringService = tutoringService;
    }

    // TODO: Update createSession to accept a promptName and pass it to tutoringService.createSession
    public String createSession(String studentId, String promptName) {
        var sessions = studentSessions.computeIfAbsent(studentId, id -> ConcurrentHashMap.newKeySet());
        String sessionId = tutoringService.createSession(promptName);
        sessions.add(sessionId);
        return sessionId;
    }

    // Ask a question in an existing session (validates ownership first).
    public String askQuestion(String studentId, String sessionId, String question) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.askQuestion(sessionId, question);
    }

    public List<Message> getHistory(String studentId,
                                    String sessionId) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.getConversation(sessionId);
    }

    // Return all session IDs this student has created.
    public Set<String> listSessions(String studentId) {
        return Collections.unmodifiableSet(
                studentSessions.getOrDefault(studentId, Set.of()));
    }

}