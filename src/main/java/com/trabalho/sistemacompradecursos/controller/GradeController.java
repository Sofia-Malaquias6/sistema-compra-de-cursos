package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.CourseDTO;
import com.trabalho.sistemacompradecursos.dto.EnrollmentDTO;
import com.trabalho.sistemacompradecursos.dto.GradeDTO;
import com.trabalho.sistemacompradecursos.security.UserDetailsImpl;
import com.trabalho.sistemacompradecursos.service.EnrollmentService;
import com.trabalho.sistemacompradecursos.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class GradeController {
    private final GradeService service;
    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollment/{enrollmentId}")
    public String viewGrades(@PathVariable String enrollmentId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
       
            model.addAttribute("grades",  service.findGradesByEnrollment(enrollmentId));
            model.addAttribute("user",  userDetails.getUser());
            model.addAttribute("average", service.calculateAverage(enrollmentId));
     
        return "grade/list";
    }
    // Formulário para criar novo curso
    @GetMapping("enrollment/{enrollmentId}/new")
    public String showCreateForm(@PathVariable String enrollmentId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("grade", new GradeDTO(null, "", "",enrollmentService.findEnrollmentById(enrollmentId).orElseThrow()));
        model.addAttribute("user",  userDetails.getUser());
        return "grade/form";
    }

    // Salvar novo curso
    @PostMapping("/save")
    public String saveGrade(@ModelAttribute("grade") GradeDTO gradeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "grade/form";
        }
        service.addGrade(gradeDTO);
        return "redirect:/grades";
    }

    // Form para editar curso
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.findGradeById(id).ifPresentOrElse(grade -> {
            model.addAttribute("grade", grade);
            model.addAttribute("user",  userDetails.getUser());
        }, () -> {
            throw new IllegalArgumentException("Nota não encontrada: " + id);
        });
        return "grade/form";
    }

    // Atualizar curso
    @PostMapping("/update")
    public String updateGrade(@ModelAttribute("grade") GradeDTO gradeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "grade/form";
        }
        service.updateGrade(gradeDTO);
        return "redirect:/grades";
    }

    // Deletar curso
    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes redirectAttributes) {
        service.deleteGrade(id);
        return "redirect:/grades";
    }
}
