package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ensine.domain.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    Optional<Materia> findByNomeContainingIgnoreCase(String nome);


}
