package com.example.sis_spinner.service;

import com.example.sis_spinner.model.Admin;
import com.example.sis_spinner.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- FITUR LOGIN ---
    public boolean authenticate(String username, String rawPassword) {
        Optional<Admin> admin = repository.findByUsername(username);
        if (admin.isPresent()) {
            return passwordEncoder.matches(rawPassword, admin.get().getPassword());
        }
        return false;
    }

    // --- CRUD ---
    
    // Create (Register)
    public Admin createAdmin(Admin admin) {
        if (admin.getUsername() == null || admin.getPassword() == null) {
            throw new RuntimeException("Username dan Password wajib diisi");
        }

        // [PENTING] Paksa ID menjadi null agar dianggap data BARU oleh Hibernate
        // Ini mencegah error jika Swagger/Postman mengirimkan "id": "string"
        admin.setId(null); 

        // Enkripsi password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        
        return repository.save(admin);
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }

    public Optional<Admin> findById(String id) {
        return repository.findById(id);
    }

    public Admin updateAdmin(String id, Admin adminDetails) {
        return repository.findById(id).map(admin -> {
            admin.setUsername(adminDetails.getUsername());
            admin.setEmail(adminDetails.getEmail());
            if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
                admin.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
            }
            return repository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}