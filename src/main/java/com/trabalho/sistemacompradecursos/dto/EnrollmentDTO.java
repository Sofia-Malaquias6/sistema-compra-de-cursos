package com.trabalho.sistemacompradecursos.dto;

public record EnrollmentDTO(
         String id,
         String status,
         CourseDTO course,
         UserDTO user,
         String enrollmentDate
) {
}
