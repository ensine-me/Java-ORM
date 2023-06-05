package school.sptech.ensine.service.usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    public Avaliacao criarAvaliacao(@Valid Avaliacao avaliacao) {
        return this.avaliacaoRepository.save(avaliacao);
    }

    public Double getNotaByProfessor(Professor professor) {
        return this.avaliacaoRepository.findMeanNotaByProfessor(professor);
    }
}
