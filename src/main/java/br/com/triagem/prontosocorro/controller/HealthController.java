package br.com.triagem.prontosocorro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController 
@RequestMapping("/health") 
public class HealthController {

    @GetMapping 
    public ResponseEntity<Map<String, String>> checkHealth() {
        Map<String, String> response = Map.of("status", "UP", "message", "Sistema de triagem está no ar!");
        return ResponseEntity.ok(response);
    }
}