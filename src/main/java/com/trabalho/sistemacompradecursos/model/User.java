package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String senha;
    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments = new ArrayList<>();

    public static UserDTO toDTO(User user){
        return new UserDTO(String.valueOf(user.getId()), user.getName(), user.getEmail(), "");
    }
}
