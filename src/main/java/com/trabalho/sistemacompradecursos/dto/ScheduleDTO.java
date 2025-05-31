package com.trabalho.sistemacompradecursos.dto;

public record ScheduleDTO (
       String id,
       String topic,
       String date,
       String startTime,
       String endTime,
       CourseDTO course

){
}
