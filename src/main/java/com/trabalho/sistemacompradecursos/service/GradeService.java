package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.dto.GradeDTO;
import com.trabalho.sistemacompradecursos.model.Grade;
import com.trabalho.sistemacompradecursos.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;

    public GradeDTO createGrade(Long enrollmentId, Double gradeValue, String description) {
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
