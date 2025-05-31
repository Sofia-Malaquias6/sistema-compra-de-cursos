package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.ScheduleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Entity
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    private Course course;


    public static Schedule fromDTO(ScheduleDTO schedule) {
        return null;
    }

    public static ScheduleDTO toDTO(Schedule schedule) {
        return null;
    }

}
