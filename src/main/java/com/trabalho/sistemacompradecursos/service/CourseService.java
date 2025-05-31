package com.trabalho.sistemacompradecursos.service;
// CourseService.java

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trabalho.sistemacompradecursos.model.Course.fromDTO;
import static com.trabalho.sistemacompradecursos.model.Course.toDTO;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public CourseDTO createCourse(CourseDTO course) {
        return toDTO(repository.save(fromDTO(course)));
    }



    public Optional <CourseDTO> updateCourse(CourseDTO course){
        return repository.findById(safeLongNull(course.id())).map(existing ->{
          return toDTO(repository.save(fromDTO(course)));
        });
    }

    public List<CourseDTO> findAllCourses() {
        return repository.findAll().stream().map(Course::toDTO).collect(Collectors.toList());
    }

    public Optional<CourseDTO> findCourseById(Long id) {

        return repository.findById(id).map(Course::toDTO);
    }

    public void deleteCourse(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
