package com.example.sis_spinner.controller;

import com.example.sis_spinner.dto.LoginRequest;
import com.example.sis_spinner.model.Admin;
import com.example.sis_spinner.security.JwtUtil;
import com.example.sis_spinner.service.AdminService;
import io.swagger.v3.oas.annotations.Operation; // [Baru] Import ini
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

    // Tambahkan @Operation(security = {}) untuk menghilangkan gembok
    @Operation(summary = "Register Admin Baru", security = {}) 
    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.createAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    // Tambahkan @Operation(security = {}) untuk menghilangkan gembok
    @Operation(summary = "Login Admin", security = {}) 
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();

        if (adminService.authenticate(username, password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}