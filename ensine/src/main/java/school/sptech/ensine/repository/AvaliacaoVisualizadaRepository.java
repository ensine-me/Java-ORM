package school.sptech.ensine.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ensine.domain.AvaliacaoVisualizada;

public interface AvaliacaoVisualizadaRepository extends JpaRepository<AvaliacaoVisualizada, Integer> {
    List<AvaliacaoVisualizada> findByAluno_IdUsuarioAndVisualizada(int idUsuario, Boolean visualizada);

}
