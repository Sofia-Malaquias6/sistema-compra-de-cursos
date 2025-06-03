package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.security.UserDetailsImpl;
import com.trabalho.sistemacompradecursos.service.CourseService;
import com.trabalho.sistemacompradecursos.service.EnrollmentService;
import com.trabalho.sistemacompradecursos.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ScheduleService scheduleService;
    private final EnrollmentService enrollmentService;

    // Listar todos os cursos
    @GetMapping
    public String listCourses(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CourseDTO> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("user",  userDetails.getUser());
        return "course/list"; // templates/course/list.html
    }

    // Formulário para criar novo curso
    @GetMapping("/new")
    public String showCreateForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("course", new CourseDTO(null, "", "", "0"));
        model.addAttribute("user",  userDetails.getUser());
        return "course/form"; // templates/course/form.html
    }

    // Salvar novo curso
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") CourseDTO courseDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "course/form";
        }
        courseService.createCourse(courseDTO);
        return "redirect:/courses";
    }

    // Form para editar curso
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        courseService.findCourseById(id).ifPresentOrElse(course -> {
            model.addAttribute("course", new CourseDTO(course.id(),course.name(),course.description(),safeStringDouble(parseMoney(course.price()))));
            model.addAttribute("user",  userDetails.getUser());
        }, () -> {
            throw new IllegalArgumentException("Curso não encontrado: " + id);
        });
        return "course/form";
    }

    // Atualizar curso
    @PostMapping("/update")
    public String updateCourse(@ModelAttribute("course") CourseDTO courseDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "course/form";
        }
        courseService.updateCourse(courseDTO);
        return "redirect:/courses";
    }

    // Deletar curso
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes redirectAttributes) {
        if (!scheduleService.findSchedulesByCourse(id).isEmpty()) {
            redirectAttributes.addFlashAttribute("errorScheduleExists", true);
        }else if (!enrollmentService.findEnrollmentsByUserAndCourseId(safeStringNull(userDetails.getId()),id).isEmpty()) {
            redirectAttributes.addFlashAttribute("errorEnrollmentExists", true);
        }else{
            courseService.deleteCourse(id);
        }
        return "redirect:/courses";
    }
}
