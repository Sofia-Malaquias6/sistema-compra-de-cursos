package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.GradeDTO;
import com.trabalho.sistemacompradecursos.dto.GradeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.parseMoney;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double grade;
    private String description;
    @ManyToOne
    private Enrollment enrollment;
    public static GradeDTO toDTO(Grade grade) {
        return new GradeDTO(
                safeStringNull(grade.getId()),
                safeStringDouble(grade.getGrade()),
                grade.getDescription(),
                Enrollment.toDTO(grade.getEnrollment())
        );
    }

    public static Grade fromDTO(GradeDTO grade) {
        return new Grade(
                safeLongNull(grade.id()),
                safeDoubleNull(grade.grade()),
                grade.description(),
                Enrollment.fromDTO(grade.enrollment())

        );
    }
}
