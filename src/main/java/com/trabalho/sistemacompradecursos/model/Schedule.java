package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.ScheduleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek; // MON-WED, TUE-THU, etc.

    @ManyToOne
    private Course course;

    public static Schedule fromDTO(ScheduleDTO dto) {
        return new Schedule(
                safeLongNull(dto.id()),
                dto.topic(),
                safeLocalDate(dto.date()),
                safeLocalTime(dto.startTime()),
                safeLocalTime(dto.endTime()),
                dto.dayOfWeek(),
                Course.fromDTO(dto.course())
        );
    }

    public static ScheduleDTO toDTO(Schedule entity) {
        return new ScheduleDTO(
                safeStringNull(entity.getId()),
                entity.getTopic(),
                safeStringDate(entity.getDate()),
                safeStringTime(entity.getStartTime()),
                safeStringTime(entity.getEndTime()),
                entity.getDayOfWeek(),
                Course.toDTO(entity.getCourse())
        );
    }
}
