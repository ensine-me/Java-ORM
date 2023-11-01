package school.sptech.ensine.service.usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.AvaliacaoVisualizada;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.repository.AvaliacaoRepository;

import java.util.List;
import java.util.Optional;
import school.sptech.ensine.repository.AvaliacaoVisualizadaRepository;

@Service
public class AvaliacaoService {
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    AvaliacaoVisualizadaRepository avaliacaoVisualizadaRepository;

    public Avaliacao criarAvaliacao(@Valid Avaliacao avaliacao) {
        if (avaliacaoRepository.findByIdAndAula_Alunos_IdUsuario(avaliacao.getAula().getId(),
                avaliacao.getUsuario().getIdUsuario()).isEmpty()) {
            return this.avaliacaoRepository.save(avaliacao);
        }
        throw new IllegalStateException("Aluno já avaliou esta aula");
    }

    public Double getNotaByProfessor(Professor professor) {
        return this.avaliacaoRepository.findMeanNotaByProfessor(professor);
    }

    public List<Avaliacao> listAvaliacaoByAulaId(Integer idAula) {
        return this.avaliacaoRepository.findByAula_Id(idAula);
    }

    public List<Avaliacao> listAvaliacaoByProfessorId(Integer idProfessor) {
        return this.avaliacaoRepository.findByProfessor_IdUsuario(idProfessor);
    }

    public List<Avaliacao> listAvaliacaoByAlunoId(Integer idAluno) {
        return this.avaliacaoRepository.findByUsuario_IdUsuario(idAluno);
    }

    public Double getMediaByProfessorId(Professor professor) {
        return this.avaliacaoRepository.findMeanNotaByProfessor(professor);
    }

    public Optional<AvaliacaoVisualizada> recuperaUltimaNaoVisualizada (Integer idAluno){
        List<AvaliacaoVisualizada> avaliacoes = avaliacaoVisualizadaRepository.findByAluno_IdUsuarioAndVisualizada(idAluno, false);
        if(avaliacoes.isEmpty()) {
            return Optional.empty();
        }
        for (AvaliacaoVisualizada a:
             avaliacoes) {
            a.setVisualizada(true);
            avaliacaoVisualizadaRepository.save(a);
        }
        return Optional.of(avaliacoes.get(avaliacoes.size() - 1));
    }
}
