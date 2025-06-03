package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Schedule> schedules = new ArrayList<>();

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(
                safeStringNull(course.getId()),
                course.getName(),
                course.getDescription(),
                formatMoney(course.getPrice())
        );
    }

    public static Course fromDTO(CourseDTO course) {
        return new Course(
                safeLongNull(course.id()),
                course.name(),
                course.description(),
                parseMoney(course.price()),
                null,
                null
        );
    }
}
