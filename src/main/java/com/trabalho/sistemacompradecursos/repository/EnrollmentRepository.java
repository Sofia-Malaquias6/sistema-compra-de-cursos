package com.trabalho.sistemacompradecursos.repository;

import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByUserId(Long userId);

    List<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
}
