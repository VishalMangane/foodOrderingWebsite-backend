package com.example.bitemeals.controller;

import com.example.bitemeals.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token is missing");
        }

        boolean isValid = jwtUtil.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok().body(Map.of(
                    "message", "Token is valid",
                    "isValid", true
            ));
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }

}
