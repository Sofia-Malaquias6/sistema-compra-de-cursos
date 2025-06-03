package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.ScheduleDTO;
import com.trabalho.sistemacompradecursos.security.UserDetailsImpl;
import com.trabalho.sistemacompradecursos.service.CourseService;
import com.trabalho.sistemacompradecursos.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CourseService courseService;

    // Listar todos
    @GetMapping("/course/{courseId}")
    public String listSchedules(@PathVariable String courseId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ScheduleDTO> schedules = scheduleService.findSchedulesByCourse(courseId);
        model.addAttribute("schedules", schedules);
        model.addAttribute("user", userDetails.getUser());
        courseService.findCourseById(courseId).ifPresentOrElse(course -> {
            model.addAttribute("course", course);
        }, () -> {
            throw new IllegalArgumentException("Curso não encontrado: " + courseId);
        });
        if(schedules.isEmpty()){
            return showCreateForm(courseId, model, userDetails);
        }else{
            return "schedule/list"; // templates/schedule/list.html

        }
    }

    // Formulário para criar novo
    @GetMapping("/course/{courseId}/new")
    public String showCreateForm(@PathVariable String courseId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("schedule", new ScheduleDTO(null, "", "", "", "", "", courseService.findCourseById(courseId).orElseThrow()));
        model.addAttribute("course", courseService.findCourseById(courseId).orElseThrow());
        model.addAttribute("user", userDetails.getUser());
        return "schedule/form"; // templates/schedule/form.html
    }

    // Salvar novo
    @PostMapping("/save")
    public String saveSchedule(@ModelAttribute("schedule") ScheduleDTO scheduleDTO, BindingResult result, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", scheduleDTO.course());
            model.addAttribute("user", userDetails.getUser());  // ✅ Linha adicionada
            return "schedule/form";
        }
        scheduleService.createSchedule(scheduleDTO);
        return listSchedules(scheduleDTO.course().id(),model,userDetails);
    }

    // Form para editar
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.findScheduleById(id).ifPresentOrElse(schedule -> {
            model.addAttribute("schedule", schedule);
            model.addAttribute("user", userDetails.getUser());
        }, () -> {
            throw new IllegalArgumentException("Curso não encontrado: " + id);
        });
        return "schedule/form";
    }

    // Atualizar
    @PostMapping("/update")
    public String updateSchedule(@ModelAttribute("schedule") ScheduleDTO scheduleDTO, Model model, BindingResult result, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (result.hasErrors()) {
            return "schedule/form";
        }
        scheduleService.updateSchedule(scheduleDTO);
        return listSchedules(scheduleDTO.course().id(),model,userDetails);
    }

    // Deletar
    @GetMapping("/course/{courseId}/delete/{id}")
    public String deleteSchedule(@PathVariable String courseId, Model model, @PathVariable String id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id);
        return listSchedules(courseId,model,userDetails);
    }
}
