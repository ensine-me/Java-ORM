package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ensine.domain.Formacao;

public interface FormacaoRepository extends JpaRepository<Formacao, Integer> {
}
