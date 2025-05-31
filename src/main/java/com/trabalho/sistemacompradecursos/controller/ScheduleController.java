package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.ScheduleDTO;
import com.trabalho.sistemacompradecursos.model.Schedule;
import com.trabalho.sistemacompradecursos.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/course/{courseId}")
    public String viewSchedules(@PathVariable String courseId, Model model) {
        List<Schedule> schedules = scheduleService.findSchedulesByCourse(courseId);

        model.addAttribute("schedules", schedules);
        model.addAttribute("courseId", courseId);
        model.addAttribute("newSchedule", new ScheduleDTO(null, null, "", "", "",null));

        return "schedules";
    }

    @PostMapping("/course/{courseId}/add")
    public String addSchedule(
            @PathVariable String courseId,
            @RequestParam String dayOfWeek,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        ScheduleDTO dto = new ScheduleDTO(null, null, dayOfWeek, startTime, endTime,null);
        scheduleService.createSchedule(dto);
        return "redirect:/schedules/course/" + courseId;
    }

    @GetMapping("/{scheduleId}/edit")
    public String editScheduleForm(@PathVariable String scheduleId, Model model) {
        Schedule schedule = scheduleService.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule não encontrado: " + scheduleId));

        ScheduleDTO dto = new ScheduleDTO(
                schedule.getId(),
                schedule.getCourse().getId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime()
        );

        model.addAttribute("schedule", dto);
        return "schedule-edit";
    }

    @PostMapping("/{scheduleId}/edit")
    public String editSchedule(
            @PathVariable Long scheduleId,
            @RequestParam String dayOfWeek,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam Long courseId
    ) {
        ScheduleDTO dto = new ScheduleDTO(scheduleId, courseId, dayOfWeek, startTime, endTime);
        scheduleService.updateSchedule(dto);
        return "redirect:/schedules/course/" + courseId;
    }

    @GetMapping("/{scheduleId}/delete")
    public String deleteSchedule(@PathVariable Long scheduleId, @RequestParam Long courseId) {
        scheduleService.deleteSchedule(scheduleId);
        return "redirect:/schedules/course/" + courseId;
    }
}
