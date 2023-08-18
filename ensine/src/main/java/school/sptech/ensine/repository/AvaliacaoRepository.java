package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.Professor;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    Optional<Avaliacao> findByIdAndAula_Alunos_Id(int idAula, int idAluno);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.professor = :professor")
    Double findMeanNotaByProfessor(@Param("professor") Professor professor);
}
