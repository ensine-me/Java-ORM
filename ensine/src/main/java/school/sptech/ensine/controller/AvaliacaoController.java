package school.sptech.ensine.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Avaliacao;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.domain.exception.EntidadeNaoEncontradaException;
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

    @PostMapping("/{idAluno}/{idProfessor}")
    public ResponseEntity<Avaliacao> criarAvaliacao(@PathVariable Integer idAluno, @PathVariable Integer idProfessor, @RequestBody @Valid Avaliacao avaliacao) {
        Optional<Professor> professor = this.usuarioService.encontraProfessorID(idProfessor);
        Optional<Usuario> usuario = this.usuarioService.encontraUsuarioId(idAluno);
        if (professor.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Professor não foi encontrado");
        }
        if(usuario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário não foi encontrado");
        }
        List<Aula> aulas = this.aulaService.getAulasConcluidasPorProfessorAndUsuario(usuario.get(), professor.get());
        if(aulas.isEmpty()) {
            throw new IllegalStateException("Usuário não realizou nenhuma aula com o professor antes de avaliá-lo");
        }
        avaliacao.setProfessor(professor.get());
        avaliacao.setUsuario(usuario.get());
        return ResponseEntity.ok(this.avaliacaoService.criarAvaliacao(avaliacao));
    }
}
