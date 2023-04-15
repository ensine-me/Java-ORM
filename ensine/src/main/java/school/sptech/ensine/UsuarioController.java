package school.sptech.ensine;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    // INSERT INTO usuario(isProfessor, nome, email, senha, dataNasc) values (false, 'Clébin', 'clebin@email.com', '1234senha', '2022-04-14');

//    @PostMapping("/materias")
//    public ResponseEntity<List<Materia>> cadastrarMaterias(){
//        materiaRepository.save(new Materia("Matematica"));
//        materiaRepository.save(new Materia("Portugues"));
//        materiaRepository.save(new Materia("Fisica"));
//        materiaRepository.save(new Materia("Quimica"));
//        materiaRepository.save(new Materia("Filosofia"));
//        materiaRepository.save(new Materia("Sociologia"));
//        materiaRepository.save(new Materia("Geografia"));
//        materiaRepository.save(new Materia("Historia"));
//        materiaRepository.save(new Materia("Biologia"));


//        List<Materia> materias = materiaRepository.findAll();
//        return ResponseEntity.status(201).body(materias);
//  }

    @GetMapping("/materias")
    public ResponseEntity<List<Materia>> listarMaterias(){
        List<Materia> materias = materiaRepository.findAll();

        if (materias.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materias);
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


        Professor professor = usuarioRepository.<Professor>save(professorNovo);
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

    // Hugo´s ordenations:

}
