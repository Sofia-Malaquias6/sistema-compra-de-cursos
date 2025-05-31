package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Remove;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login"; // Se não estiver logado, redireciona
        }

        model.addAttribute("user", user);
        return "home"; // Abre templates/home.html
    }
}
