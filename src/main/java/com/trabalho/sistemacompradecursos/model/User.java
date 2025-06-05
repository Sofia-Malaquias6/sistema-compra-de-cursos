package com.trabalho.sistemacompradecursos.model;

import com.trabalho.sistemacompradecursos.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.trabalho.sistemacompradecursos.utils.FormatUtils.safeLongNull;
import static com.trabalho.sistemacompradecursos.utils.FormatUtils.safeStringNull;

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
        return new UserDTO(safeStringNull(user.getId()), user.getName(), user.getEmail(), "");
    }
    public static User fromDTO(UserDTO user){
        return new User(safeLongNull(user.id()), user.name(), user.email(), "",null);
    }
}
