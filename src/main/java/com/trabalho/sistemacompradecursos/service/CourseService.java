package com.trabalho.sistemacompradecursos.service;
// CourseService.java

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Schedule;
import com.trabalho.sistemacompradecursos.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trabalho.sistemacompradecursos.model.Course.fromDTO;
import static com.trabalho.sistemacompradecursos.model.Course.toDTO;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;


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

    public Optional<CourseDTO> findCourseById(String id) {

        return repository.findById(safeLongNull(id)).map(Course::toDTO);
    }

    public void deleteCourse(String id) {
        Long lid = safeLongNull(id);
        if(repository.existsById(lid)){
            repository.deleteById(lid);
        }
    }
}
