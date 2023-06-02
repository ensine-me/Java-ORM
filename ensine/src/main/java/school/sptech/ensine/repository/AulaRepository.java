package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.service.usuario.dto.ContagemAula;

import java.time.LocalDateTime;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByStatus(String status);
    Long countByStatus(String status);
    Long countByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByAlunosId(int id);
    @Query( nativeQuery = true, value = "SELECT d.nome AS disciplina_nome, COUNT(a.id) AS total_aulas FROM professor p INNER JOIN aula a ON :idProfessor = a.usuario INNER JOIN materia d ON a.materia = d.id GROUP BY d.nome;")
    List<ContagemAula> contagemAulas(@Param("idProfessor") int idProfessor);


}
