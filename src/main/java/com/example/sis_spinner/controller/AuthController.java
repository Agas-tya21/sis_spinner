package com.example.sis_spinner.controller;

import com.example.sis_spinner.dto.LoginRequest;
import com.example.sis_spinner.security.JwtUtil;
import com.example.sis_spinner.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    public AuthController(JwtUtil jwtUtil, AdminService adminService) {
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = service.createAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginData) { // Pakai LoginRequest
        String username = loginData.getUsername(); // Pakai getter
        String password = loginData.getPassword();

        // Logika autentikasi tetap sama...
        if (adminService.authenticate(username, password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}