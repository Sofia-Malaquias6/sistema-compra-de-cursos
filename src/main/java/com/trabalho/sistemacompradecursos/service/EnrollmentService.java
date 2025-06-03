package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import com.trabalho.sistemacompradecursos.dto.UserDTO;
import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Enrollment;
import com.trabalho.sistemacompradecursos.model.User;
import com.trabalho.sistemacompradecursos.repository.CourseRepository;
import com.trabalho.sistemacompradecursos.repository.EnrollmentRepository;
import com.trabalho.sistemacompradecursos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.trabalho.sistemacompradecursos.model.Enrollment.toDTO;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;


    public EnrollmentDTO enroll(String userId, String courseId) {
        Course course = new Course();
        course.setId(safeLongNull(courseId));
        User user = new User();
        user.setId(safeLongNull(userId));
        Enrollment enrollment = new Enrollment(null,"ACTIVE",course,user, LocalDate.now(),null);
        return toDTO(repository.save(enrollment));
    }


    public List<EnrollmentDTO> findEnrollmentsByUserAndCourseId(String userId, String courseId) {

        return repository.findByUserIdAndCourseId(safeLongNull(userId), safeLongNull(courseId)).stream().map(Enrollment::toDTO).toList();
    }

    public List<EnrollmentDTO> findEnrollmentsByUser(String userId) {

        return repository.findByUserId(safeLongNull(userId)).stream().map(Enrollment::toDTO).toList();
    }

    public Optional<EnrollmentDTO> findEnrollmentById(String id) {

        return repository.findById(safeLongNull(id)).map(Enrollment::toDTO);
    }

    private User findUser(String id) {
        return userRepository.findById(safeLongNull(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    private Course findCourse(String id) {
        return courseRepository.findById(safeLongNull(id))
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }
    public void deleteEnrollmentById(String id) {
        Long enrollmentId = safeLongNull(id);
        if (repository.existsById(enrollmentId)) {
            repository.deleteById(enrollmentId);
        }
    }
}

