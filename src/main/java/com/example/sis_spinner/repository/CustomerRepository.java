package com.example.sis_spinner.repository;

import com.example.sis_spinner.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}