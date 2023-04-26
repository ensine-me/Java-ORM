package school.sptech.ensine.controller;


import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.ListaObj;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.AulaRepository;
import school.sptech.ensine.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.SubmissionPublisher;

@Tag(name = "Aula", description = "Requisições relacionada às aulas")
@SecurityRequirement(name = "Bearer")
@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Tag(name = "Listar aulas", description = "Lista as aulas cadastradas")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    public ResponseEntity<ListaObj<Aula>> getAulas() {
        int qtdAulas = (int) aulaRepository.count();
        ListaObj<Aula> aulas = new ListaObj<Aula>(qtdAulas);
        aulas.adiciona(aulaRepository.findAll().toArray(new Aula[qtdAulas]));
        if (aulas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(aulas);
    }

    @GetMapping("busca-id")
    @Tag(name = "Pegar aula por id", description = "Devolve uma aula dado um id")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aula recuperada com sucesso")
    public ResponseEntity<Aula> getAulaPorId(@RequestParam int id) {
        return ResponseEntity.of(aulaRepository.findById(id));
    }

    @GetMapping("busca-professor")
    @Tag(name = "Pegar aulas por professor", description = "Devolve uma aula dado o nome de um professor")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<ListaObj<Aula>> getAulasPorProfessor(@RequestParam String nomeProfessor) {
        Usuario usuario = usuarioRepository.findByNomeIgnoreCase(nomeProfessor);
        if (usuario.isProfessor()) {
            int qtdAulas = Math.toIntExact(aulaRepository.countByProfessorNomeEqualsIgnoreCase(nomeProfessor));
            ListaObj<Aula> aulas = new ListaObj<Aula>(qtdAulas);
            aulas.adiciona(aulaRepository.findAll().toArray(new Aula[qtdAulas]));
            if (aulas.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(200).body(aulas);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping
    @Tag(name = "Cadastrar aula", description = "Cadastra uma nova aula")
    @ApiResponse(responseCode = "201", description = "Aula cadastrada com sucesso")
    public ResponseEntity<Aula> cadastrarAula(@RequestBody Aula newAula) {
        Aula aulaCadastrada = aulaRepository.save(newAula);
//        for (int i = 0; i < aulaCadastrada.getAlunos().size(); i++) { // for para adicionar cada aluno como um observador
//            aulaCadastrada.addObserver(aulaCadastrada.getAlunos().get(i));
//        }
        return ResponseEntity.status(201).body(aulaCadastrada);
    }

    @PutMapping("/iniciar-aula")
    @Tag(name = "Iniciar aula", description = "Muda o status de uma aula para iniciado")
    @ApiResponse(responseCode = "200", description = "Aula iniciada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public ResponseEntity<String> iniciarAula(@RequestParam int id) {
        if (aulaRepository.existsById(id)) {
            Aula aula = aulaRepository.getReferenceById(id);
            aula.setStatus("Iniciada");
            return ResponseEntity.status(200).body("Aula iniciada");
        }
        return ResponseEntity.status(404).body("Aula não encontrada");
    }

    @PutMapping("/finalizar-aula")
    @Tag(name = "Finalizar aula", description = "Muda o status de uma aula para finalizada")
    @ApiResponse(responseCode = "200", description = "Aula finalizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public ResponseEntity<String> finalizarAula(@RequestParam int id) {
        if (aulaRepository.existsById(id)) {
            Aula aula = aulaRepository.getReferenceById(id);
            aula.setStatus("Finalizada");
            return ResponseEntity.status(200).body("Aula finalizada");
        }
        return ResponseEntity.status(404).body("Aula não encontrada");
    }
}
