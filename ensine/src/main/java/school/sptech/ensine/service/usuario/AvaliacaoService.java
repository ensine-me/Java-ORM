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
import school.sptech.ensine.repository.UsuarioRepository;

@Service
public class AvaliacaoService {
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AvaliacaoVisualizadaRepository avaliacaoVisualizadaRepository;

    public Avaliacao criarAvaliacao(@Valid Avaliacao avaliacao) {
        if (avaliacaoRepository.findByIdAndAula_Alunos_IdUsuario(avaliacao.getAula().getId(),
                avaliacao.getUsuario().getIdUsuario()).isEmpty()) {
            Integer experiencia = (int) (avaliacao.getNota() * 100) + (avaliacao.getInsignias().size() * 50);
            avaliacao.setExperiencia(experiencia);
            Avaliacao avaliacao1 = this.avaliacaoRepository.save(avaliacao);
            Professor professor = avaliacao1.getProfessor();
            Double media = avaliacaoRepository.findMeanNotaByProfessor(professor);
            professor.setNota(media);

            professor.setExperiencia(professor.getExperiencia() + avaliacao1.getExperiencia());

            Optional<AvaliacaoVisualizada> avaliacaoVisualizada =
                    avaliacaoVisualizadaRepository.findByAluno_IdUsuarioAndAula_IdAndVisualizada(
                            avaliacao1.getUsuario().getIdUsuario(),
                            avaliacao1.getAula().getId(),
                            false
                    );
            if(!avaliacaoVisualizada.isEmpty()){
                AvaliacaoVisualizada avaliacaoVisualizada1 = avaliacaoVisualizada.get();
                avaliacaoVisualizada1.setVisualizada(true);
                avaliacaoVisualizadaRepository.save(avaliacaoVisualizada1);
            }
            usuarioRepository.save(professor);

            return avaliacao1;
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
