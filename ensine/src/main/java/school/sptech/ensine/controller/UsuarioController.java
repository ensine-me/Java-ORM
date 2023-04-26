package school.sptech.ensine.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.DTO.UsuarioDto;
import school.sptech.ensine.domain.*;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;
import school.sptech.ensine.service.usuario.UsuarioService;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.ensine.service.usuario.dto.ProfessorCriacaoDto;
import school.sptech.ensine.service.usuario.dto.ProfessorMapper;
import school.sptech.ensine.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.ensine.service.usuario.dto.UsuarioMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Usuário", description = "Requisições relacionadas aos usuários")
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

//    private List<UsuarioDto> usuariosLogados = new ArrayList<>();

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    //pode ocorrer um bug na lista de usuariosLogados ao tentar cadastrar um novo usuário e logá-lo
    //logo em seguida, porque o limite da lista será o de número de usuários cadastrados anteriormente.
    // TODO: desenvolver uma lógica para que isso não aconteça
    private ListaObj<UsuarioDto> usuariosLogados = new ListaObj<>();

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
    public ResponseEntity<UsuarioCriacaoDto> adicionaAluno(@RequestBody @Valid UsuarioCriacaoDto alunoNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O ALUNO NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioService.existeEmail(alunoNovo.getEmail())) {
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
        if (usuarioService.existeEmail(professorNovo.getEmail())) {
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
        int qtdUsuarios = Math.toIntExact(usuarioRepository.count());
        ListaObj<Usuario> usuarios = new ListaObj<>(qtdUsuarios);
        usuarios.adiciona(usuarioRepository.findAll().toArray(new Usuario[qtdUsuarios]));

        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

//    @GetMapping("teste")
//    @SecurityRequirement(name = "Bearer")
//    @Tag(name = "Cadastrar professor", description = "Cadastra um professor")
//    @ApiResponse(responseCode = "401", description = "Login não foi realizado", content = @Content(schema = @Schema(hidden = true)))
//    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
//    @ApiResponse(responseCode = "406", description = "Erro: o professor informado não respeita as validações", content = @Content(schema = @Schema(hidden = true)))
//    @ApiResponse(responseCode = "409", description = "Erro: o e-mail informado já possui cadastro", content = @Content(schema = @Schema(hidden = true)))
//    public ResponseEntity<List<Materia>> listarMateria(){
//        List<Usuario> usuarios = usuarioRepository.findAll();
//
//        if(usuarios.isEmpty()){
//            return ResponseEntity.status(204).build();
//        }
//        return ResponseEntity.status(200).body(usuarios.get(2).getMaterias());
//    }

    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Listar usuários logados", description = "Lista os usuários atualmente logados no sistema")
    @ApiResponse(responseCode = "401", description = "Login não foi realizado", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping("/logados")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    public ResponseEntity<ListaObj<UsuarioDto>> listarLogados(){
        ListaObj<UsuarioDto> usuariosLogadosDto = new ListaObj<>(usuariosLogados.size());
        for (int i = 0; i < usuariosLogados.size(); i ++) {
            UsuarioDto usuarioDto = new UsuarioDto(usuariosLogados.get(i));
            usuariosLogadosDto.adiciona(usuarioDto);
        }
        return ResponseEntity.status(200).body(usuariosLogadosDto);
    }

    @GetMapping("/isProfessor")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Is Usuario Professor", description = "Dado um usuário, devolve se é professor ou não")
    @ApiResponse(responseCode = "401", description = "Login não foi realizado", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Professor requisitado não foi encontrado", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Professor encontrado")
    public ResponseEntity<Boolean> isUsuarioProfessor(@RequestParam String nomeUsuario) {
        if (usuarioRepository.existsByNomeIgnoreCase(nomeUsuario)) {
            Usuario usuario = usuarioRepository.findByNomeIgnoreCase(nomeUsuario);
            if (usuario.isProfessor()) {
                return ResponseEntity.status(200).body(true);
            }
            return ResponseEntity.status(200).body(false);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/login")
    @Tag(name = "Login", description = "Autentica os usuários no sistema")
    @ApiResponse(responseCode = "409", description = "Usuário já está logado", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLogar){

        UsuarioTokenDto usuarioToken = usuarioService.autenticar(usuarioLogar);

        for(int i = 0; i < usuariosLogados.size(); i++){
            if (usuariosLogados.get(i).getEmail().equals(usuarioToken.getEmail())){
                return ResponseEntity.status(409).build();
            }
        }

        Optional<Usuario> usuario = usuarioRepository.findByEmailIgnoreCase(usuarioToken.getEmail());

        usuariosLogados.adiciona(new UsuarioDto (usuario.get()));

        return ResponseEntity.status(200).body(usuarioToken);
    }

    @DeleteMapping("/logoff")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Login", description = "Desautentica os usuários no sistema")
    @ApiResponse(responseCode = "404", description = "Usuário não existe ou não está logado")
    @ApiResponse(responseCode = "200", description = "Usuário deslogado com sucesso")
    @ApiResponse(responseCode = "401", description = "Login não foi realizado", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<String> logoff(@RequestBody String email){

        int tamanho = usuariosLogados.size();

        for(int i = 0; i < usuariosLogados.size(); i++){
            if(usuariosLogados.get(i).getEmail().equals(email)){
                usuariosLogados.remove(i);
                break;
            }
        }
        // por que estamos comparando o tamanho da lista com o tamanho da lista?
        // por que isso implica que o usuário não está logado?
        // remover o IF não dá no mesmo?
        if (usuariosLogados.size() == tamanho){
            return ResponseEntity.status(404).body("Usuario não está logado");
        }
        return ResponseEntity.status(200).body("Usuario deslogado com sucesso");
    }

}
