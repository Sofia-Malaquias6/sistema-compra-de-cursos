package com.trabalho.sistemacompradecursos.config;

import com.trabalho.sistemacompradecursos.model.User;
import com.trabalho.sistemacompradecursos.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@Configuration
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User(null, "Alice", "alice@exemplo.com", passwordEncoder.encode("123456"), null);
            userRepository.save(user);
            User user1 = new User(null, "Sofia", "sofiamalaquias@exemplo.com", passwordEncoder.encode("1234567"), null);
            userRepository.save(user1);
        }
    }
}
