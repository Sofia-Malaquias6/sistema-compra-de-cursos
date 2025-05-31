package com.trabalho.sistemacompradecursos.dto;

public record GradeDTO(
        String id,
        String grade,
        String description,
        EnrollmentDTO enrollment
) {
}
