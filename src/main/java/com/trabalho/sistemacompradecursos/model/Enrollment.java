package com.trabalho.sistemacompradecursos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    private LocalDate enrollmentDate;
    @OneToMany(mappedBy = "grade")
    private List<Grade> grades = new ArrayList<>();
}
