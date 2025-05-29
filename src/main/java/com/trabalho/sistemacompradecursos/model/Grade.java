package com.trabalho.sistemacompradecursos.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double grade;
    private String description;
    @ManyToOne
    private Enrollment enrollment;
}
