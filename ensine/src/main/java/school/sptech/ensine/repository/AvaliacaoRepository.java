package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.Professor;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.professor = :professor")
    Double findMeanNotaByProfessor(@Param("professor") Professor professor);
}
