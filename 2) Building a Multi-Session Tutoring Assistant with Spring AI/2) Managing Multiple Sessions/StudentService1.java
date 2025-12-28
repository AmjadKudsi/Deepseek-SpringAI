// implement the createSession method

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

    // TODO: Implement the createSession method
    // - Parameters: String studentId
    // - Check if studentId is not in studentSessions and initialize it
    // - Use tutoringService.createSession() to generate a sessionId
    // - Store the new sessionId for the student
    // - Return the sessionId
    public String createSession(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("studentId must not be null or empty");
        }
        
        Set<String> sessionsForStudent = studentSessions.computeIfAbsent(
            studentId, 
            id -> Collections.newSetFromMap(new ConcurrentHashMap<>())
        );
        
        String sessionId = tutoringService.createSession();
        
        sessionsForStudent.add(sessionId);
        
        return sessionId;
    }

    // (Other methods omitted for brevity)
}