package com.example.sis_spinner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// [BARU] Import untuk Generate ID
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    // [PERBAIKAN] Tambahkan anotasi untuk generate UUID otomatis
    @GeneratedValue(strategy = GenerationType.UUID) 
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "cabang")
    private String cabang;

    @Column(name = "periode")
    private String periode;

    @Column(name = "tanggal_dibuat")
    private LocalDateTime tanggalDibuat;

    @Column(name = "nama_nasabah")
    private String namaNasabah;

    @Column(name = "status")
    private String status;
}