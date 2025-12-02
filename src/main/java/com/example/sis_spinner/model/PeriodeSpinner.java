package com.example.sis_spinner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "periode_spinner") // Nama tabel di database
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeSpinner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generate ID sebagai UUID otomatis
    @Column(name = "id_periode_spinner", nullable = false)
    private String idPeriodeSpinner;

    @Column(name = "tanggal_awal_periode", nullable = false)
    private LocalDateTime tanggalAwalPeriode;

    @Column(name = "tanggal_akhir_periode", nullable = false)
    private LocalDateTime tanggalAkhirPeriode;
}