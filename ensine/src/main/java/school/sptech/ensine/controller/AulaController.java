package school.sptech.ensine.controller;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.*;
import school.sptech.ensine.domain.exception.ParametrosInvalidosException;
import school.sptech.ensine.enumeration.Privacidade;
import school.sptech.ensine.enumeration.Status;
import school.sptech.ensine.service.usuario.AulaService;
import school.sptech.ensine.service.usuario.DisponibilidadeService;
import school.sptech.ensine.service.usuario.UsuarioService;
import school.sptech.ensine.service.usuario.dto.ContagemAula;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Tag(name = "Aula", description = "Requisições relacionada às aulas")
@SecurityRequirement(name = "Bearer")
@RestController
@RequestMapping("/aulas")
public class AulaController {
    @Autowired
    private AulaService aulaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @PutMapping("/{id}/adicionar-aluno")
    public ResponseEntity<Aula> adicionarAluno(@PathVariable int id, @RequestParam String email) {
        Optional<Usuario> usuarioOptional = usuarioService.encontraPorEmail(email);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Aula> aulaOptional = aulaService.encontraAulaId(id);
        if (aulaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Aula> aulaModificada = aulaService.adicionarAluno(aulaOptional.get(), usuarioOptional.get());
        return ResponseEntity.ok(aulaModificada.get());
    }

    @GetMapping("/busca-por-descricao")
    public ResponseEntity<List<Aula>> buscarAulasPorDescricao(@RequestParam String termo) {
        List<Aula> aulas = this.aulaService.getAulasPorDescricao(termo);
        if(aulas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(aulas);
    }

    @GetMapping("/busca-por-titulo")
    public ResponseEntity<List<Aula>> buscarAulasPorTitulo(@RequestParam String termo) {
        List<Aula> aulas = this.aulaService.getAulasPorTitulo(termo);
        if(aulas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(aulas);
    }

    @GetMapping("/busca-por-materia")
    public ResponseEntity<List<Aula>> buscarAulasPorMateria(@RequestParam String termo) {
        List<Aula> aulas = this.aulaService.getAulasPorMateria(termo);
        if(aulas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(aulas);
    }

    @GetMapping
    @Tag(name = "Listar aulas", description = "Lista as aulas cadastradas")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    public ResponseEntity<ListaObj<Aula>> getAulas() {
        int qtdAulas = aulaService.qtdeAulas();
        ListaObj<Aula> aulas = new ListaObj<Aula>(qtdAulas);
        aulas.adiciona(aulaService.aulas().toArray(new Aula[qtdAulas]));
        if (aulas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(aulas);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Aula>> getAulasByStatus(@PathVariable Status status) {
        List<Aula> aulas = aulaService.getAulasPorStatus(status);
        if (aulas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(aulas);
    }

    @GetMapping("/privacidade/{privacidade}")
    public ResponseEntity<List<Aula>> getAulasByPrivacidade(@PathVariable Privacidade privacidade) {
        List<Aula> aulas = aulaService.getAulasPorPrivacidade(privacidade);
        if (aulas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(aulas);
    }

    @GetMapping("busca-id-usuario")
    @Tag(name = "AulasIdUsuario", description = "Mostrar aulas apartir do id do usuario.")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    public ResponseEntity<List<Aula>> getAulasIdUsuario(@RequestParam int id){
     return ResponseEntity.status(200).body(aulaService.encontraAulaPeloIdAluno(id));
    }

    @GetMapping("busca-id")
    @Tag(name = "Pegar aula por id", description = "Devolve uma aula dado um id")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aula recuperada com sucesso")
    public ResponseEntity<Aula> getAulaPorId(@RequestParam int id) {
        return ResponseEntity.of(aulaService.encontraAulaId(id));
    }

    @GetMapping("conta-aulas-professor-id")
    @Tag(name = "conta aula por id", description = "devolve int qtd aulas")
    @ApiResponse(responseCode = "404", description = "qtd de Aula não encontrada", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "qtd de Aula recuperada com sucesso")
    public ResponseEntity<Long> countAulaProfessorPorId(@RequestParam int id) {
        Long qtdAulas = aulaService.countProfessorId(id);
        return ResponseEntity.ok(qtdAulas);
    }

    @GetMapping("conta-aulas-professorid-concluida")
    @ApiResponse(responseCode = "404", description = "qtd de Aula não encontrada", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "qtd de Aula recuperada com sucesso")
    public ResponseEntity<Long> countAulaProfessorConcluidaPorId(@RequestParam int id) {
        Long qtdAulas = aulaService.countProfessorIdConcluida(id);
        return ResponseEntity.ok(qtdAulas);
    }

    @GetMapping("conta-aulas-professorid-agendada")
    @ApiResponse(responseCode = "404", description = "qtd de Aulas agendadas não encontrada", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "qtd de Aulas agendadas recuperada com sucesso")
    public ResponseEntity<Long> countAulaProfessorAgendadaPorId(@RequestParam int id) {
        Long qtdAulas = aulaService.countProfessorIdAgendada(id);
        return ResponseEntity.ok(qtdAulas);
    }

    @GetMapping("busca-professor")
    @Tag(name = "Pegar aulas por professor", description = "Devolve uma aula dado o nome de um professor")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<ListaObj<Aula>> getAulasPorProfessor(@RequestParam String nomeProfessor) {
        Optional<Usuario> usuario = usuarioService.encontraPorNome(nomeProfessor);
        if (usuario.get().isProfessor()) {
            int qtdAulas = Math.toIntExact(aulaService.countProfessorNome(nomeProfessor));
            ListaObj<Aula> aulas = new ListaObj<Aula>(qtdAulas);
            aulas.adiciona(aulaService.aulas().toArray(new Aula[qtdAulas]));
            if (aulas.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(200).body(aulas);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("busca-professor-id-solicitado")
    @Tag(name = "Pegar aulas por professorid solicitado", description = "Devolve uma aula dado o nome de um professor id solicitado")
    @ApiResponse(responseCode = "204", description = "Não há aulas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Aulas recuperadas com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<Aula>> getProfessorIdSolicitado(@RequestParam int id) {
        List<Aula> aulas = aulaService.getProfessorIdSolicitado(id);
        if(aulas.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.ok(aulas);
        }
    }

    @PostMapping
    @Tag(name = "Cadastrar aula", description = "Cadastra uma nova aula")
    @ApiResponse(responseCode = "201", description = "Aula cadastrada com sucesso")
    public ResponseEntity<Aula> cadastrarAula(@RequestBody Aula newAula) {
        LocalTime horarioAula = newAula.getDataHora().toLocalTime();
        LocalTime horarioAulaFim = horarioAula.plusSeconds(newAula.getDuracaoSegundos());

        Professor professor = newAula.getProfessor();
        List<Disponibilidade> disponibilidades = this.disponibilidadeService.getDisponibilidadesByProfessorId(professor.getIdUsuario());

        boolean estaDisponivel = false;

        for (Disponibilidade disponibilidade : disponibilidades) {
            if (disponibilidade.getDiaDaSemana().ordinal() == newAula.getDataHora().getDayOfWeek().ordinal()) {
                if(horarioAula.isAfter(disponibilidade.getHorarioInicio()) ||
                        horarioAula.equals(disponibilidade.getHorarioInicio()) &&
                        horarioAulaFim.isBefore(disponibilidade.getHorarioFim()) ||
                        horarioAulaFim.equals(disponibilidade.getHorarioFim())){
                    estaDisponivel = true;
                    break;
                }
            }
        }

        if(!estaDisponivel) {
            throw new ParametrosInvalidosException("A aula não está dentro da disponibilidade do professor");
        }

        Aula aulaCadastrada = aulaService.aulaNova(newAula);
        return ResponseEntity.status(201).body(aulaCadastrada);
    }

    @PutMapping("/iniciar-aula")
    @Tag(name = "Iniciar aula", description = "Muda o status de uma aula para iniciado")
    @ApiResponse(responseCode = "200", description = "Aula iniciada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public ResponseEntity<String> iniciarAula(@RequestParam int id) {
        if (aulaService.existePorId(id)) {
            Aula aula = aulaService.referenciaId(id);
            aula.setStatus(Status.EM_PROGRESSO);
            return ResponseEntity.status(200).body("Aula iniciada");
        }
        return ResponseEntity.status(404).body("Aula não encontrada");
    }

    @PostMapping("/finalizar-aula")
    @Tag(name = "Finalizar aula", description = "Muda o status de uma aula para finalizada")
    @ApiResponse(responseCode = "200", description = "Aula finalizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    public ResponseEntity<String> finalizarAula(@RequestParam int id) {
        if (aulaService.existePorId(id)) {
            aulaService.finalizarAula(id);
            return ResponseEntity.status(200).body("Aula finalizada");
        }
        return ResponseEntity.status(404).body("Aula não encontrada");
    }
    @GetMapping("/{id}/mudanca-status")
    public ResponseEntity<Aula> mudarStatus(@PathVariable int id, @RequestParam Status status) {
        return ResponseEntity.of(aulaService.atualizarStatusAula(id, status));
    }

    @GetMapping("contagem/{idProfessor}")
    public List<ContagemAula> contagemAulas (@PathVariable int idProfessor){
       return aulaService.contagemAulas(idProfessor);
    }

    @GetMapping("professor/{idProfessor}")
    public List<Aula> listAulasByProfessorId (@PathVariable int idProfessor) {
        return aulaService.listAulasByProfessorId(idProfessor);
    }

    @GetMapping("aluno/{idAluno}")
    public List<Aula> listAulasByAlunoId (@PathVariable int idAluno) {
        return aulaService.listAulasByAlunoId(idAluno);
    }

    @GetMapping("qtd-aulas-hoje")
    public ResponseEntity<List<Object>> contagemAulasHoje(){
        long qtdAulasHoje = aulaService.getQtdAulasHoje();
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatoData);

        List<Object> responseList = Arrays.asList(dataFormatada, qtdAulasHoje);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("qtd-aulas-meses")
    public ResponseEntity<List<ContagemAula>> qtdAulasUltimosMeses(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = currentTime.minusMonths(3);

        List<ContagemAula> qtd = aulaService.getContagemAulasUltimosTresMeses(currentTime, threeMonthsAgo);

        if (qtd.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(qtd);
    }

    @GetMapping("total-valor-aulas")
    public ResponseEntity<List<Object[]>> totalValorAulas(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = currentTime.minusMonths(3);

        List<Object[]> qtd = aulaService.getTotalValorAulas(currentTime, threeMonthsAgo);

        if (qtd.isEmpty()){

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(qtd);
    }

    @GetMapping("alunos/nao-avaliadas/{alunoId}")
    public ResponseEntity<List<Aula>> listaAulasNaoAvaliadas(@PathVariable Integer alunoId) {
        List<Aula> aulas = aulaService.listaAulasNaoAvaliadas(alunoId);
        return ResponseEntity.status(200).body(aulas);
    }

    @PostMapping("report")
    public ResponseEntity<ReportAula> criaReport(@RequestBody ReportAula reportAula){
        ReportAula report = aulaService.criaReport(reportAula);

        return ResponseEntity.status(200).body(report);
    }

    @GetMapping("report")
    public ResponseEntity<List<ReportAula>> getAllReports(){
        List<ReportAula> reports = aulaService.getAllReports();
        return ResponseEntity.status(200).body(reports);
    }

    @GetMapping("report/{id}")
    public ResponseEntity<ReportAula> getReportById(@PathVariable Integer id){
        ReportAula report = aulaService.getReportById(id);
        return ResponseEntity.status(200).body(report);
    }
}
