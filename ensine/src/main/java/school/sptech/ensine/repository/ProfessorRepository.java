package school.sptech.ensine.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ensine.domain.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    List<Professor> findTop10ByOrderByExperienciaDesc();

//    List<Professor> findOrderByExperienciaDesc();
}
