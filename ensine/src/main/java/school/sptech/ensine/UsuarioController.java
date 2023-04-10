package school.sptech.ensine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/adiciona-varios") // Adiciona 15 usuários ao banco, apenas para teste
    public ResponseEntity<List<Usuario>> adicionarUsuariosParaTeste() {
        usuarioRepository.save(new Usuario(false, "testeNome1", "email1@email.com", "testeSenha1"));
        usuarioRepository.save(new Usuario(true, "testeNome2", "email2@email.com", "testeSenha2"));
        usuarioRepository.save(new Usuario(false, "testeNome3", "email3@email.com", "testeSenha3"));
        usuarioRepository.save(new Usuario(true, "testeNome4", "email4@email.com", "testeSenha4"));
        usuarioRepository.save(new Usuario(true, "testeNome5", "email5@email.com", "testeSenha5"));
        usuarioRepository.save(new Usuario(false, "testeNome6", "email6@email.com", "testeSenha6"));
        usuarioRepository.save(new Usuario(true, "testeNome7", "email7@email.com", "testeSenha7"));
        usuarioRepository.save(new Usuario(true, "testeNome8", "email8@email.com", "testeSenha8"));
        usuarioRepository.save(new Usuario(false, "testeNome9", "email9@email.com", "testeSenha9"));
        usuarioRepository.save(new Usuario(true, "testeNome10", "email10@email.com", "testeSenha10"));
        usuarioRepository.save(new Usuario(true, "testeNome11", "email11@email.com", "testeSenha11"));
        usuarioRepository.save(new Usuario(false, "testeNome12", "email12@email.com", "testeSenha12"));
        usuarioRepository.save(new Usuario(true, "testeNome13", "email13@email.com", "testeSenha13"));
        usuarioRepository.save(new Usuario(true, "testeNome14", "email14@email.com", "testeSenha14"));
        usuarioRepository.save(new Usuario(false, "testeNome15", "email15@email.com", "testeSenha15"));

        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.status(201).body(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> adicionaAluno(@RequestBody @Valid Usuario alunoNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O ALUNO NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioRepository.existsByEmail(alunoNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> ALUNO COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }
        alunoNovo.setProfessor(false);
        Usuario aluno = usuarioRepository.save(alunoNovo);
        return ResponseEntity.status(201).body(aluno);
    }

    @PostMapping("/professor")
    public ResponseEntity<Professor> adicionaProfessor(@RequestBody @Valid Professor professorNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O PROFESSOR NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioRepository.existsByEmail(professorNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> PROFESSOR COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }
        professorNovo.setProfessor(true);
        Professor professor = usuarioRepository.save(professorNovo);
        return ResponseEntity.status(201).body(professor);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/usuarios-por-idade") // Retorna todos os usuários, do mais novo para o mais velho
    public ResponseEntity<List<Usuario>> ordenadosPorIdade() {
        List<Usuario> usuariosOrdenadosPorIdade = usuarioRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Usuario::converterEmAnos).reversed())
                .toList();
        return ResponseEntity.status(200).body(usuariosOrdenadosPorIdade);
    }

}
