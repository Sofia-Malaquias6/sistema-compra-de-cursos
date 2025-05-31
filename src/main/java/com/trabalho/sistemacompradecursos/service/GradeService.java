package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.model.Grade;
import com.trabalho.sistemacompradecursos.repository.GradeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradeService {

    private final GradeRepository repository;

    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public Grade addGrade(Long enrollmentId, Double gradeValue, String description) {
        // TODO: Add grade for enrollment
        return null;
    }

    public List<Grade> findGradesByEnrollment(Long enrollmentId) {
        // TODO: Return grades for enrollment
        return null;
    }

    public Double calculateAverage(Long enrollmentId) {
        // TODO: Calculate average grade
        return null;
    }
}
