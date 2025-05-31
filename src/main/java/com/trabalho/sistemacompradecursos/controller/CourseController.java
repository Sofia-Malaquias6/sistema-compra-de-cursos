package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.service.CourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Listar todos os cursos
    @GetMapping
    public String listCourses(Model model, HttpSession session) {
        List<CourseDTO> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "course/list"; // templates/course/list.html
    }

    // Formulário para criar novo curso
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        model.addAttribute("course", new CourseDTO(null, "", "", "0"));
        model.addAttribute("user", session.getAttribute("loggedUser"));
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
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        courseService.findCourseById(id).ifPresentOrElse(course -> {
            model.addAttribute("course", course);
            model.addAttribute("user", session.getAttribute("loggedUser"));
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
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
