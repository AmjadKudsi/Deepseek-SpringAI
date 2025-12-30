// implement the askQuestion method in the StudentService class and demonstrate its usage in the CommandLineRunner

package com.codesignal.deepseektutor;

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

    public String createSession(String studentId) {
        var sessions = studentSessions.computeIfAbsent(studentId, id -> ConcurrentHashMap.newKeySet());
        String sessionId = tutoringService.createSession();
        sessions.add(sessionId);
        return sessionId;
    }

    // TODO: Implement the askQuestion method
    // - Parameters: String studentId, String sessionId, String question
    // - Check if sessionId belongs to studentId
    // - Use tutoringService.askQuestion to get the assistant's reply
    // - Return the assistant's reply
    public String askQuestion(String studentId, String sessionId, String question) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.askQuestion(sessionId, question);
    }

    // (Other methods omitted for brevity)
}