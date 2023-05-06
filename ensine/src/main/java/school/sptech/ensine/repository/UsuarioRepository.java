package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ensine.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Usuario> findByEmailIgnoreCase(String email);
   Optional<Usuario> findByNomeIgnoreCase(String nome);
}
