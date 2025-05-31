package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import com.trabalho.sistemacompradecursos.dto.UserDTO;
import com.trabalho.sistemacompradecursos.service.CourseService;
import com.trabalho.sistemacompradecursos.service.EnrollmentService;
import com.trabalho.sistemacompradecursos.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final CourseService courseService;

    // Listar matrículas de um usuário
    @GetMapping
    public String listUserEnrollments(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<EnrollmentDTO> enrollments = enrollmentService.findEnrollmentsByUser(user.id());
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("user", user);
        return "enrollment/list";
    }

    // Formulário para nova matrícula
    @GetMapping("/new")
    public String showEnrollForm(Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<CourseDTO> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "enrollment/form";
    }

    // Realizar matrícula
    @PostMapping("/save")
    public String enroll(@RequestParam("courseId") String courseId, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        enrollmentService.enroll(user.id(), courseId);
        return "redirect:/enrollments";
    }

    // Cancelar matrícula
    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable String id) {
        enrollmentService.deleteEnrollmentById(id);
        return "redirect:/enrollments";
    }
}
