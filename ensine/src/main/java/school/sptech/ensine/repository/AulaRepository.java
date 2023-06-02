package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.service.usuario.dto.ContagemAula;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByStatus(String status);
    Long countByStatus(String status);
    Long countByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByAlunosId(int id);
    @Query("SELECT new school.sptech.ensine.service.usuario.dto.ContagemAula(a.materia.nome, COUNT(a)) " +
            "FROM Aula a " +
            "WHERE a.professor = :professor " +
            "GROUP BY a.materia.nome")
    List<ContagemAula> contagemAulas(@Param("professor") Professor professor);


}
