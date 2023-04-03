package school.sptech.ensine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> adicionaAluno(@RequestBody @Valid Usuario alunoNovo){
        Usuario aluno = usuarioRepository.save(alunoNovo);
        return ResponseEntity.status(201).body(aluno);
    }

    @PostMapping("/professor")
    public ResponseEntity<Professor> adicionaProfessor(@RequestBody @Valid Professor professorNovo){
        Professor professor = usuarioRepository.save(professorNovo);
        return ResponseEntity.status(201).body(professor);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(202).body(usuarios);
    }
}
