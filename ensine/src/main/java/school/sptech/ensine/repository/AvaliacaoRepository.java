package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.Professor;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    Optional<Avaliacao> findByAula_IdAndUsuario_IdUsuario(int idAula, int idUsuario);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.professor = :professor")
    Double findMeanNotaByProfessor(@Param("professor") Professor professor);

    List<Avaliacao> findByAula_Id(int id);

    List<Avaliacao> findByProfessor_IdUsuario(int id);

    List<Avaliacao> findByUsuario_IdUsuario(int id);


}
