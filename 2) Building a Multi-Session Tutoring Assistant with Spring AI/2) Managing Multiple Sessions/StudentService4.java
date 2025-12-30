// implement session deletion and practice retrieving session history

package com.codesignal.deepseektutor;

import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;

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

    // TODO: Implement deleteSession
    // - Parameters: String studentId, String sessionId
    // - If sessionId exists for studentId, remove it
    // - If not, throw IllegalArgumentException
    public void deleteSession(String studentId, String sessionId) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        sessions.remove(sessionId);
    }    

    public Set<String> listSessions(String studentId) {
        return Collections.unmodifiableSet(
                studentSessions.getOrDefault(studentId, Set.of()));
    }

    public List<Message> getHistory(String studentId, String sessionId) {
        var sessions = studentSessions.get(studentId);
        if (sessions == null || !sessions.contains(sessionId)) {
            throw new IllegalArgumentException("Unknown session for student");
        }
        return tutoringService.getConversation(sessionId);
    }
}