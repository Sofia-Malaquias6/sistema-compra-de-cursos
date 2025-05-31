package com.trabalho.sistemacompradecursos.controller;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import com.trabalho.sistemacompradecursos.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Abre templates/login.html
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        // Simples autenticação manual
        UserDTO user = userService.authenticate(username, password);

        if (user != null) {
            session.setAttribute("loggedUser", user); // Salva usuário na sessão
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão inteira
        return "redirect:/login";
    }
}
