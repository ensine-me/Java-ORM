package school.sptech.ensine.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.AvaliacaoVisualizada;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.ensine.enumeration.Status;
import school.sptech.ensine.service.usuario.AulaService;
import school.sptech.ensine.service.usuario.AvaliacaoService;
import school.sptech.ensine.service.usuario.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    AvaliacaoService avaliacaoService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    AulaService aulaService;

    @PostMapping("/{idAluno}/{idAula}")
    public ResponseEntity<Avaliacao> criarAvaliacao(@PathVariable Integer idAluno, @PathVariable Integer idAula, @RequestBody @Valid Avaliacao avaliacao) {
        Optional<Usuario> usuario = this.usuarioService.encontraUsuarioId(idAluno);
        Optional<Aula> aula = this.aulaService.encontraAulaId(idAula);
        if (usuario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário não foi encontrado");
        }
        if (aula.isEmpty()) {
           throw new EntidadeNaoEncontradaException("Aula não encontrada");
        }
        Professor professor = aula.get().getProfessor();

        if (aula.get().getAlunos().isEmpty()) {
            throw new IllegalStateException("Aluno não pertence a essa aula");
        } else {
            for (Usuario aluno:
                 aula.get().getAlunos()) {
                if(aluno.getIdUsuario() == idAluno) {
                    if (aula.get().getStatus() != Status.CONCLUIDA) {
                        throw new IllegalStateException("Aula não concluída");
                    }
                    avaliacao.setProfessor(professor);
                    avaliacao.setUsuario(usuario.get());
                    avaliacao.setAula(aula.get());
                    return ResponseEntity.ok(this.avaliacaoService.criarAvaliacao(avaliacao));
                }
            }
            throw new IllegalStateException("Aluno não pertence a essa aula");
        }
    }

    @GetMapping("/{idAluno}/{idAula}")
    public ResponseEntity<Avaliacao> getAvaliacaoByAlunoAula(@PathVariable int idAluno, @PathVariable int idAula) {
        Optional<Usuario> alunoOpt = this.usuarioService.encontraUsuarioId(idAluno);
        Optional<Aula> aulaOpt = this.aulaService.encontraAulaId(idAula);

        if (alunoOpt.isEmpty() || aulaOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Optional<Avaliacao> avaliacaoOpt = this.avaliacaoService.getAvaliacaoByAulaAndUsuario(idAula, idAluno);

        if (avaliacaoOpt.isPresent()) {
            return ResponseEntity.ok(avaliacaoOpt.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/aula/{idAula}")
    public ResponseEntity<List<Avaliacao>> listAvaliacaoByAulaId(@PathVariable Integer idAula) {
        Optional<Aula> aula = this.aulaService.encontraAulaId(idAula);
        if (aula.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Aula não encontrada");
        }
        List<Avaliacao> avaliacoes = avaliacaoService.listAvaliacaoByAulaId(idAula);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<Avaliacao>> listAvaliacaoByProfessorId(@PathVariable Integer idProfessor) {
        Optional<Professor> professor = this.usuarioService.encontraProfessorID(idProfessor);
        if (professor.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Professor não encontrado");
        }
        List<Avaliacao> avaliacoes = avaliacaoService.listAvaliacaoByProfessorId(idProfessor);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/professor/{idProfessor}/media")
    public ResponseEntity<Double> getMediaByProfessorId(@PathVariable Integer idProfessor) {
        Optional<Professor> professor = this.usuarioService.encontraProfessorID(idProfessor);
        if (professor.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Professor não encontrado");
        }
        Double mediaProfessor = avaliacaoService.getMediaByProfessorId(professor.get());
        return ResponseEntity.ok(mediaProfessor);
    }

    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<Avaliacao>> listAvaliacaoByidAlunoId(@PathVariable Integer idAluno) {
        Optional<Usuario> aluno = this.usuarioService.encontraUsuarioId(idAluno);
        if (aluno.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Aluno não encontrado");
        }
        List<Avaliacao> avaliacoes = avaliacaoService.listAvaliacaoByAlunoId(idAluno);
        return ResponseEntity.ok(avaliacoes);
    }
    @GetMapping("/visualizada/aluno/{idAluno}")
    public ResponseEntity<AvaliacaoVisualizada> recuperaUltimaNaoVisualizada(@PathVariable Integer idAluno){
        Optional<AvaliacaoVisualizada> avaliacao = avaliacaoService.recuperaUltimaNaoVisualizada(idAluno);
        if(avaliacao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avaliacao.get());
    }

}
