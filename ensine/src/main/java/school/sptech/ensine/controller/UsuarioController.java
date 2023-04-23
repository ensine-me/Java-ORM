package school.sptech.ensine.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.DTO.UsuarioDto;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;
import school.sptech.ensine.service.usuario.UsuarioService;
import school.sptech.ensine.service.usuario.dto.UsuarioCriacaoDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private List<UsuarioDto> usuariosLogados = new ArrayList<>();

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/materias")
    public ResponseEntity<List<Materia>> listarMaterias(){
        List<Materia> materias = materiaRepository.findAll();

        if (materias.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materias);
    }

    @PostMapping
    public ResponseEntity<UsuarioCriacaoDto> adicionaAluno(@RequestBody @Valid UsuarioCriacaoDto alunoNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O ALUNO NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioService.existeEmail(alunoNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> ALUNO COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).body(usuarioService.criarAluno(alunoNovo));
    }


    @PostMapping("/professor")
    public ResponseEntity<Professor> adicionaProfessor(@RequestBody @Valid Professor professorNovo, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("ERRO(CADASTRO) >>> O PROFESSOR NÃO RESPEITA AS VALIDAÇÕES");
            return ResponseEntity.status(406).build();
        }
        if (usuarioService.existeEmail(professorNovo.getEmail())) {
            System.out.println("ERRO(CADASTRO) >>> PROFESSOR COM EMAIL JÁ CADASTRADO");
            return ResponseEntity.status(409).build();
        }
        professorNovo.setProfessor(true);

        List<String> materias = new ArrayList<>();
        professorNovo.getMaterias().forEach(materia -> materias.add(materia.getNome()));
        professorNovo.getMaterias().clear();

        Professor professor = usuarioService.criarProfessor(professorNovo);
        adicionarMateriaUsuario(professor.getId(), materias);
        usuariosLogados.add(new UsuarioDto(professor));
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

    @GetMapping("teste")
    public ResponseEntity<List<Materia>> listarMateria(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios.get(2).getMaterias());
    }

    @GetMapping("/logados")
    public ResponseEntity<List<UsuarioDto>> listarLogados(){

//        if (usuariosLogados.isEmpty()){
//         return ResponseEntity.status(204).build();
//        }
        return ResponseEntity.status(200).body(usuariosLogados);
    }

    @GetMapping("/isProfessor")
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
    public ResponseEntity<UsuarioDto> login(@RequestBody Usuario usuarioLogar){

        Optional<Usuario> usuarioTemp = usuarioRepository.findByEmailIgnoreCase(usuarioLogar.getEmail());

        if(usuarioTemp.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Usuario usuario = usuarioTemp.get();

        for(UsuarioDto usuarioDto :usuariosLogados){
            if (usuarioDto.getEmail().equals(usuario.getEmail())){
                return ResponseEntity.status(409).build();
            }
        }

        if (usuario.getSenha().equals(usuarioLogar.getSenha())){
            usuariosLogados.add(new UsuarioDto(usuario));
        }else{
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.status(200).body(new UsuarioDto(usuario)) ;
    }

    @DeleteMapping("/logoff")
    public ResponseEntity<String> logoff(@RequestBody String email){

        int tamanho = usuariosLogados.size();

        for(int i = 0; i < usuariosLogados.size(); i++){
            if(usuariosLogados.get(i).getEmail().equals(email)){
                usuariosLogados.remove(i);
                break;
            }
        }
        if (usuariosLogados.size() == tamanho){
            return ResponseEntity.status(404).body("Usuario não está logado");
        }
        return ResponseEntity.status(200).body("Usuario deslogado com sucesso");
    }



    // Hugo´s ordenations:

}
