# Add the two missing REST endpoints to the TutoringController: POST and ASK

package com.codesignal.deepseektutor;

import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tutor")
public class TutoringController {

    private final StudentService studentService;

    public TutoringController(StudentService studentService) {
        this.studentService = studentService;
    }

    public record CreateSessionRequest(String studentId, String promptName) {}
    public record CreateSessionResponse(String sessionId) {}
    public record AskRequest(String studentId, String sessionId, String question) {}
    public record AskResponse(String answer) {}

    // TODO: Add a POST endpoint at "/create" that takes a CreateSessionRequest and returns a CreateSessionResponse
    @PostMapping("/create")
    public ResponseEntity<CreateSessionResponse> createSession(
            @RequestBody CreateSessionRequest req
    ) {
        String sessionId = studentService.createSession(req.studentId(), req.promptName());
        return ResponseEntity.ok(new CreateSessionResponse(sessionId));
    }    

    // TODO: Add a POST endpoint at "/ask" that takes an AskRequest and returns an AskResponse
    @PostMapping("/ask")
    public ResponseEntity<AskResponse> askQuestion(
            @RequestBody AskRequest req
    ) {
        String answer = studentService.askQuestion(req.studentId(), req.sessionId(), req.question());
        return ResponseEntity.ok(new AskResponse(answer));
    }    

    // List all session IDs for a student
    @GetMapping("/sessions/{studentId}")
    public ResponseEntity<Set<String>> listSessions(
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(studentService.listSessions(studentId));
    }

    // Get the conversation history for a given session
    @GetMapping("/history")
    public List<Message> history(
            @RequestParam String studentId,
            @RequestParam String sessionId
    ) {
        return studentService.getHistory(studentId, sessionId);
    }
}