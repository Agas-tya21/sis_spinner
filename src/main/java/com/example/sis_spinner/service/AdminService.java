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
            // Cek apakah password raw cocok dengan hash di DB
            return passwordEncoder.matches(rawPassword, admin.get().getPassword());
        }
        return false;
    }

    // --- CRUD ---
    
    // Create (Register)
    public Admin createAdmin(Admin admin) {
        // Validasi sederhana (opsional)
        if (admin.getUsername() == null || admin.getPassword() == null) {
            throw new RuntimeException("Username dan Password wajib diisi");
        }

        // Enkripsi password sebelum disimpan (Wajib!)
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        
        // Set ID jika belum ada (opsional, tergantung strategi ID Anda)
        if (admin.getId() == null) {
            admin.setId(java.util.UUID.randomUUID().toString());
        }

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
            // Jika password dikirim, update & enkripsi ulang. Jika kosong, biarkan lama.
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