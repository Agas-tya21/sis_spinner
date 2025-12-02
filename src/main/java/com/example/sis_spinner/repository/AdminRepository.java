package com.example.sis_spinner.repository;

import com.example.sis_spinner.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);
}