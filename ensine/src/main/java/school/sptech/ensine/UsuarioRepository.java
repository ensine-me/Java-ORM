package school.sptech.ensine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);
}
