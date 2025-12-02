package com.example.sis_spinner.service;

import com.example.sis_spinner.model.Customer;
import com.example.sis_spinner.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    public Customer save(Customer customer) {
        if (customer.getTanggalDibuat() == null) {
            customer.setTanggalDibuat(LocalDateTime.now());
        }
        return repository.save(customer);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}