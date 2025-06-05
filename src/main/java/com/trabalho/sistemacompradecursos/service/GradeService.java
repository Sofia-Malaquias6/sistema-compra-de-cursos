package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import com.trabalho.sistemacompradecursos.dto.GradeDTO;
import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Enrollment;
import com.trabalho.sistemacompradecursos.model.Grade;
import com.trabalho.sistemacompradecursos.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trabalho.sistemacompradecursos.model.Grade.fromDTO;
import static com.trabalho.sistemacompradecursos.model.Grade.toDTO;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.safeLongNull;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;
    private final EnrollmentService enrollmentService;

    public GradeDTO addGrade(GradeDTO gradeDTO) {
        Optional<EnrollmentDTO> enrollment = enrollmentService.findEnrollmentById(gradeDTO.enrollment().id());
        if (enrollment.isEmpty()) {
            throw new IllegalArgumentException("Enrollment with ID " + gradeDTO.enrollment().id() + " not found.");
        }
        return toDTO(repository.save(fromDTO(gradeDTO)));
    }

    public List<Grade> findGradesByEnrollment(String enrollmentId) {
        return repository.findByEnrollmentId(safeLongNull(enrollmentId));
    }

    public Double calculateAverage(String enrollmentId) {
        List<Grade> grades = findGradesByEnrollment(enrollmentId);
        if (grades.isEmpty()) {
            return 0.0;
        }
        Double sum1 = grades.stream().mapToDouble(Grade::getGrade).sum();
        Double average = sum1 / grades.size();
        return average;
    }
    public Optional <GradeDTO> updateGrade(GradeDTO grade){
        return repository.findById(safeLongNull(grade.id())).map(existing ->{
            return toDTO(repository.save(fromDTO(grade)));
        });
    }

    public List<GradeDTO> findAllGrades() {
        return repository.findAll().stream().map(Grade::toDTO).collect(Collectors.toList());
    }

    public Optional<GradeDTO> findGradeById(String id) {

        return repository.findById(safeLongNull(id)).map(Grade::toDTO);
    }

    public void deleteGrade(String id) {
        Long lid = safeLongNull(id);
        if(repository.existsById(lid)){
            repository.deleteById(lid);
        }
    }
}
