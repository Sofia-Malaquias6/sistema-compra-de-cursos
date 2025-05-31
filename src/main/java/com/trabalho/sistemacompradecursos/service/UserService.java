package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import com.trabalho.sistemacompradecursos.model.User;
import com.trabalho.sistemacompradecursos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;


    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> findAllUsers() {
        return repository.findAll().stream().map(User::toDTO).toList();
    }

    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(User::toDTO);
    }

    public UserDTO authenticate(String username, String password) {
        return repository.findByEmail(username)
                .filter(user -> passwordEncoder.matches(password, user.getSenha()))
                .map(User::toDTO)
                .orElse(null);
    }


}


