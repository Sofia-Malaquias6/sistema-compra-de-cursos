package com.trabalho.sistemacompradecursos.service;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import com.trabalho.sistemacompradecursos.model.User;
import com.trabalho.sistemacompradecursos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> findAllUsers() {
        return repository.findAll().stream().map(User::toDTO).toList();
    }

    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(User::toDTO);
    }

}


