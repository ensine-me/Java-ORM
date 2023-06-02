package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.service.usuario.dto.ContagemAula;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Professor> findProfessorById(Integer id);
    Optional<Usuario> findByNomeIgnoreCase(String nome);



}
