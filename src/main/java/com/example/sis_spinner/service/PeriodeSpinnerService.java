package com.example.sis_spinner.service;

import com.example.sis_spinner.model.PeriodeSpinner;
import com.example.sis_spinner.repository.PeriodeSpinnerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodeSpinnerService {

    private final PeriodeSpinnerRepository repository;

    public PeriodeSpinnerService(PeriodeSpinnerRepository repository) {
        this.repository = repository;
    }

    public List<PeriodeSpinner> findAll() {
        return repository.findAll();
    }

    public Optional<PeriodeSpinner> findById(String id) {
        return repository.findById(id);
    }

    public PeriodeSpinner save(PeriodeSpinner periodeSpinner) {
        return repository.save(periodeSpinner);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}