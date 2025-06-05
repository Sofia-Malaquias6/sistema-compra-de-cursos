package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.parseMoney;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    private LocalDate enrollmentDate;
    @OneToMany(mappedBy = "grade")
    private List<Grade> grades = new ArrayList<>();

    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        return new EnrollmentDTO(safeStringNull(enrollment.getId()), enrollment.getStatus(), Course.toDTO(enrollment.getCourse()),User.toDTO(enrollment.getUser()),formatDate(enrollment.getEnrollmentDate()));
    }
    public static Enrollment fromDTO(EnrollmentDTO enrollment) {
        return new Enrollment(
                safeLongNull(enrollment.id()),
                enrollment.status(),
                Course.fromDTO(enrollment.course()),
                User.fromDTO(enrollment.user()),
                safeLocalDate(enrollment.enrollmentDate()),
                null
        );
    }
}
