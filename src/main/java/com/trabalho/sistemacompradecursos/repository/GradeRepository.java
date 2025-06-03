package com.trabalho.sistemacompradecursos.repository;

import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findByEnrollmentId(Long enrollmentId);
}
