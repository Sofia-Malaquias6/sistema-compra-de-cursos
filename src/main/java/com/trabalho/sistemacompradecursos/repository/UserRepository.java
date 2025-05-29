package com.trabalho.sistemacompradecursos.repository;

import com.trabalho.sistemacompradecursos.model.Course;
import com.trabalho.sistemacompradecursos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
