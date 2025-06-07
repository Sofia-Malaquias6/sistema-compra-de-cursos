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

import java.util.List;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.*;

@Controller
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService service;
    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollment/{enrollmentId}")
    public String viewGrades(@PathVariable String enrollmentId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("enrollmentId", enrollmentId); // corrigido
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
    @PostMapping("/enrollment/{enrollmentId}/save")
    public String saveGrade(@PathVariable String enrollmentId,@ModelAttribute("grade") GradeDTO gradeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "grade/form";
        }
        // Força a associação com a matrícula correta, sem confiar no formulário
        EnrollmentDTO enrollment = enrollmentService.findEnrollmentById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: " + enrollmentId));

        service.addGrade(new GradeDTO(null, gradeDTO.grade(), gradeDTO.description(),enrollment));
        return "redirect:/grades/enrollment/" + enrollmentId;
    }

    // Form para editar curso
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<EnrollmentDTO> enrollments = enrollmentService.findEnrollmentsByUser(safeStringNull(userDetails.getUser().getId()));
        model.addAttribute("enrollments", enrollments);
        service.findGradeById(id).ifPresentOrElse(grade -> {
            model.addAttribute("grade", grade);
            model.addAttribute("user",  userDetails.getUser());
        }, () -> {
            throw new IllegalArgumentException("Nota não encontrada: " + id);
        });
        return "grade/form";
    }

    // Atualizar curso
    @PostMapping("/enrollment/{enrollmentId}/update/{gradeId}")
    public String updateGrade(@PathVariable String enrollmentId,
                              @PathVariable String gradeId,
                              @ModelAttribute("grade") GradeDTO gradeDTO,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "grade/form";
        }
        // Força a associação com a matrícula correta, sem confiar no formulário
        EnrollmentDTO enrollment = enrollmentService.findEnrollmentById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: " + enrollmentId));

        service.updateGrade(new GradeDTO(gradeId, gradeDTO.grade(), gradeDTO.description(),enrollment));
        return "redirect:/grades/enrollment/" + enrollmentId;
    }

    // Deletar curso
    @GetMapping("/enrollment/{enrollmentId}/delete/{id}")
    public String deleteGrade(@PathVariable String enrollmentId,@PathVariable String id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes redirectAttributes) {
        service.deleteGrade(id);
        return "redirect:/grades/enrollment/" + enrollmentId;
    }
}
