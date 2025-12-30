// implement two essential methods in the StudentService class: getHistory, listSessions and demonstrate both methods

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.messages.Message;

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

    public String askQuestion(String studentId, String sessionId, String question) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.askQuestion(sessionId, question);
    }

    // TODO: Implement getHistory
    // - Parameters: String studentId, String sessionId
    // - Check if sessionId belongs to studentId
    // - Return tutoringService.getConversation(sessionId)
    public List<Message> getHistory(String studentId, String sessionId) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null | !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.getConversation(sessionId);
    }

    // TODO: Implement listSessions
    // - Parameter: String studentId
    // - Return all session IDs for the student as an unmodifiable set
    public Set<String> listSessions(String studentId) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(sessions);
    }
}