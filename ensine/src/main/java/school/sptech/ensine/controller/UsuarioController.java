package school.sptech.ensine.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.ensine.service.usuario.AvaliacaoService;
import school.sptech.ensine.service.usuario.dto.*;
import school.sptech.ensine.domain.*;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.service.usuario.UsuarioService;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.ensine.util.TabelaHashProfessor;


import java.util.*;

@Tag(name = "Usuário", description = "Requisições relacionadas aos usuários")
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    AvaliacaoService avaliacaoService;

    private TabelaHashProfessor tabelaHashProfessor = new TabelaHashProfessor(3);
    @GetMapping("/existe-por-email")
    public ResponseEntity<Boolean> existePorEmail(@RequestParam String emailUsuario) {
        return ResponseEntity.ok(usuarioService.existePorEmail(emailUsuario));
    }

    @GetMapping("/professores-recomendados")
    public ResponseEntity<List<Professor>> listarProfessoresRecomendados(@RequestParam("disciplinas") List<String> disciplinas) {
        Set<Professor> professores = new HashSet<>(); // Use a Set to automatically remove duplicates

        for (String disciplina : disciplinas) {
            List<Professor> professoresEncontrados = this.usuarioService.getProfessoresByMateria(disciplina);
            professores.addAll(professoresEncontrados); // Use addAll to add all professors at once
        }

        if (professores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ArrayList<>(professores)); // Convert Set to List for response
    }

//    @GetMapping("/{idUsuario}/professores-recomendados/")
//    public List<Professor> listarProfessoresRecomendados(@PathVariable int idUsuario) {
//        Optional<Usuario> usuario = this.usuarioService.encontraUsuarioId(idUsuario);
//        if(usuario.isEmpty()){
//            throw new EntidadeNaoEncontradaException("Usuário não encontrado");
//        }
//        List<Materia> materias = this.materiaRepository.findMateriaByUsuariosContains(usuario.get());
//        usuario.get().setMaterias(materias);
//        return this.usuarioService.getProfessoresRecomendados(usuario.get().getMaterias());
//    }

    @GetMapping("/professor/busca-por-descricao")
    public ResponseEntity<List<Professor>> buscarProfessoresPorDescricao(@RequestParam String termo) {
        List<Professor> professores = this.usuarioService.getProfessoresByDescricao(termo);
        if(professores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(professores);
    }

    @GetMapping("/professor/busca-por-nome")
    public ResponseEntity<List<Professor>> buscarProfessoresPorNome(@RequestParam String termo) {
        List<Professor> professores = this.usuarioService.getProfessoresByNome(termo);
        if(professores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(professores);
    }

    @GetMapping("/professor/busca-por-materia")
    public ResponseEntity<List<Professor>> buscarProfessoresPorMateria(@RequestParam String termo) {
        List<Professor> professores = this.usuarioService.getProfessoresByMateria(termo);
        if(professores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(professores);
    }

    @PostMapping("/professor/{idProfessor}/formacao") //cadastra formação nova em um professor existente
    public ResponseEntity<Professor> cadastrarFormacao(@PathVariable int idProfessor, @RequestBody Formacao formacao) {
        Professor professor = this.usuarioService.cadastrarFormacao(idProfessor, formacao);
        return ResponseEntity.created(null).body(professor);
    }

    @PostMapping("/professor/{idProfessor}/disponibilidade") //cadastra disponibilidade nova em um professor existente
    public ResponseEntity<Professor> cadastrarDisponibilidade(@PathVariable int idProfessor, @RequestBody Disponibilidade disponibilidade) {
        Professor professor = this.usuarioService.cadastrarDisponibilidade(idProfessor, disponibilidade);
        return ResponseEntity.created(null).body(professor);
    }

    @GetMapping("/professor/{idProfessor}/formacoes")
    public ResponseEntity<List<FormacaoResumoDto>> listarFormacoes(@PathVariable int idProfessor) {
        List<FormacaoResumoDto> formacoes = this.usuarioService.getFormacoes(idProfessor);
        if(formacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(formacoes);
    }

    @GetMapping("/professor/{idProfessor}/disponibilidades")
    public ResponseEntity<List<DisponibilidadeResumoDto>> listarDisponibilidades(@PathVariable int idProfessor) {
        List<DisponibilidadeResumoDto> disponibilidades = this.usuarioService.getDisponibilidades(idProfessor);
        if(disponibilidades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(disponibilidades);
    }

    @GetMapping("professor/busca")
    @Tag(name = "Encontrar professor", description = "Devolve um professor pesquisado pelo id")
    @ApiResponse(responseCode = "200", description = "Professor encontrado")
    @ApiResponse(responseCode = "404", description = "Professor não existe!", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Optional<Professor>> buscarProfessor(@RequestParam Integer id){
        Optional<Professor> prof = usuarioService.encontraProfessorID(id);
        return ResponseEntity.status(200).body(prof);
    }


    @GetMapping("professor/busca/lista")
    @Tag(name = "Encontrar professor", description = "Devolve um professor pesquisado pelo id")
    @ApiResponse(responseCode = "200", description = "Professor encontrado")
    @ApiResponse(responseCode = "404", description = "Professor não existe!", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<Professor>> buscarListaProfessor(@RequestParam String nome){
        TabelaHashProfessor prof = usuarioService.buscarListaProfessor(nome);
        List<Professor> list = prof.listAll();
        popularTabelaHash(list);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("professor/busca/lista/letra")
    @Tag(name = "Encontrar professor", description = "Devolve um professor pesquisado pelo id")
    @ApiResponse(responseCode = "200", description = "Professor encontrado")
    @ApiResponse(responseCode = "404", description = "Professor não existe!", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<Professor>> buscarListaProfessorPorLetra(@RequestParam String nome){
        List<Professor> professors = tabelaHashProfessor.buscaLista(nome);
        return ResponseEntity.status(200).body(professors);
    }
    @GetMapping("/professor/{email}")
    public ResponseEntity<Usuario> buscarProfessorPorEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOptional = usuarioService.encontraPorEmail(email);
        return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/materias")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Listar matérias", description = "Devolve uma lista de disciplinas")
    @ApiResponse(responseCode = "200", description = "Disciplinas encontradas")
    @ApiResponse(responseCode = "204", description = "Não há disciplinas cadastradas", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Login não foi realizado", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<ListaObj<Materia>> listarMaterias(){
        Long qtdMaterias = materiaRepository.count();
        ListaObj<Materia> materias = new ListaObj<>(Math.toIntExact(qtdMaterias));
        materias.adiciona(materiaRepository.findAll().toArray(new Materia[Math.toIntExact(qtdMaterias)]));

        if (materias.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materias);
    }

    @PostMapping("/cadastrar")
    @Tag(name = "Cadastrar usuários", description = "Cadastra um usuário do sistema")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "406", description = "Erro: o aluno informado não respeita as validações", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "409", description = "Erro: o e-mail informado já possui cadastro", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Usuario> adicionaAluno(@RequestBody @Valid UsuarioCriacaoDto alunoNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O ALUNO NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioService.existePorEmail(alunoNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> ALUNO COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }

        //usuariosLogados.adiciona(new UsuarioDto(aluno));
        return ResponseEntity.status(201).body(usuarioService.criarAluno(alunoNovo));
    }


    @PostMapping("/professor/cadastrar")
    @Tag(name = "Cadastrar professor", description = "Cadastra um professor")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "406", description = "Erro: o professor informado não respeita as validações", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "409", description = "Erro: o e-mail informado já possui cadastro", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<ProfessorCriacaoDto> adicionaProfessor(@RequestBody @Valid ProfessorCriacaoDto professorNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O PROFESSOR NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioService.existePorEmail(professorNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> PROFESSOR COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }

        //usuariosLogados.adiciona(new UsuarioDto(professor));
        return ResponseEntity.status(201).body(usuarioService.criarProfessor(professorNovo));
    }


    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Listar usuários", description = "Lista os usuários cadastrados")
    @ApiResponse(responseCode = "204", description = "Não há usuários cadastrados", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    public ResponseEntity<ListaObj<Usuario>> listar(){
        int qtdUsuarios = usuarioService.qtdeUsuario();
        ListaObj<Usuario> usuarios = new ListaObj<>(qtdUsuarios);
        usuarios.adiciona(usuarioService.todosUsuarios().toArray(new Usuario[qtdUsuarios]));

        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/buscar-por-id")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Usuario> listarPorId(@RequestParam int id){
        Optional<Usuario> usuario = usuarioService.encontraUsuarioId(id);

        return usuario.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(400).build());
    }

    @PostMapping("/{id}/conectar-com-google")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Usuario> conectarComGoogle(@PathVariable int id, @RequestParam String googleEmail) {
        Optional<Usuario> usuarioOptional = usuarioService.encontraUsuarioId(id);

        if(usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario retorno = usuarioService.cadastrarEmailGoogle(usuarioOptional.get(), googleEmail);

        return ResponseEntity.ok(retorno);
    }


    @PostMapping("/login")
    @Tag(name = "Login", description = "Autentica os usuários no sistema")
    @ApiResponse(responseCode = "409", description = "Usuário já está logado", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLogar){

        UsuarioTokenDto usuarioToken = usuarioService.autenticar(usuarioLogar);

        return ResponseEntity.status(200).body(usuarioToken);
    }

    @GetMapping("/professor/insignia/{idProfessor}")
    public ResponseEntity<Map<Avaliacao.Insignia, Integer>> countInsigniasProfessor(@PathVariable Integer idProfessor) {
        Optional<Professor> professor = this.usuarioService.encontraProfessorID(idProfessor);
        if (professor.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Professor não encontrado");
        }
        Map<Avaliacao.Insignia, Integer> insignias = usuarioService.countInsigniasProfessor(idProfessor);

        return ResponseEntity.ok(insignias);
    }

    @GetMapping("professor/experiencia")
    public ResponseEntity<Integer> getExperienciaProfessor(@RequestParam Integer idProfessor) {
        Integer experienciaProfessor = usuarioService.getExperienciaProfessor(idProfessor);
        return ResponseEntity.ok(experienciaProfessor);
    }


    @GetMapping("professor/experiencia/top")
    public ResponseEntity<List<Professor>> getTopExperienciaProfessor() {
        return ResponseEntity.ok(usuarioService.listTop5Professors());
    }


    private void popularTabelaHash(List<Professor> professores) {
        tabelaHashProfessor = new TabelaHashProfessor(2);
        for (Professor profesor:
                professores) {
            tabelaHashProfessor.insere(profesor);
        }
    }
    @GetMapping("conta-professores")
    @ApiResponse(responseCode = "404", description = "qtd professores não encontrado", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "qtd professores recuperado com sucesso")
    public ResponseEntity<Long> countTotalAulasConcluidas() {
        Long qtdProfessores = usuarioService.countTotalProfessores();
        return ResponseEntity.ok(qtdProfessores);
    }
}
