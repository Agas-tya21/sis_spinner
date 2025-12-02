package com.example.sis_spinner.controller;

import com.example.sis_spinner.model.Customer;
import com.example.sis_spinner.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // READ ALL
    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return service.save(customer);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customerDetails) {
        return service.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setNama(customerDetails.getNama());
                    existingCustomer.setCabang(customerDetails.getCabang());
                    existingCustomer.setPeriode(customerDetails.getPeriode());
                    existingCustomer.setNamaNasabah(customerDetails.getNamaNasabah());
                    existingCustomer.setStatus(customerDetails.getStatus());
                    // Jangan ubah tanggalDibuat
                    return ResponseEntity.ok(service.save(existingCustomer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        if (service.findById(id).isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}