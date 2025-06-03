package com.trabalho.sistemacompradecursos.service;
import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.ScheduleDTO;
import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.Schedule;
import com.trabalho.sistemacompradecursos.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trabalho.sistemacompradecursos.model.Schedule.fromDTO;
import static com.trabalho.sistemacompradecursos.model.Schedule.toDTO;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.safeLongNull;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO schedule) {
        return toDTO( repository.save(fromDTO(schedule)));
    }
    public Optional<ScheduleDTO> updateSchedule(ScheduleDTO schedule){
        return repository.findById(safeLongNull(schedule.id())).map(existing ->{
            return toDTO(repository.save(fromDTO(schedule)));
        });
    }


    public List<ScheduleDTO> findSchedulesByCourse(String courseId) {
        return repository.findByCourseId(safeLongNull(courseId)).stream().map(Schedule::toDTO).collect(Collectors.toList());
    }
    public void deleteSchedule(String scheduleId) {
        repository.deleteById(safeLongNull(scheduleId));
    }
    public Optional<ScheduleDTO> findScheduleById(String scheduleId) {
        return repository.findById(safeLongNull(scheduleId)).map(Schedule::toDTO);
    }
}
