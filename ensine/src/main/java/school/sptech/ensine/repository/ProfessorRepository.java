package school.sptech.ensine.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.ensine.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    List<Professor> findTop5ByOrderByExperienciaDesc();


//    List<Professor> findOrderByExperienciaDesc();
}
