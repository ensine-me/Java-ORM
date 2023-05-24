package school.sptech.ensine.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ensine.DTO.UsuarioDto;
import school.sptech.ensine.api.security.jwt.GerenciadorTokenJwt;
import school.sptech.ensine.domain.ListaObj;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioLoginDto;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioTokenDto;
import school.sptech.ensine.service.usuario.dto.ProfessorCriacaoDto;
import school.sptech.ensine.service.usuario.dto.UsuarioCriacaoDto;
import school.sptech.ensine.service.usuario.dto.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Boolean existeNomeIgnoreCase(String nome){
        boolean existe = usuarioRepository.existsByNomeIgnoreCase(nome);
        return existe;
    }
    public Optional<Usuario> encontraPorNome(String nome){
        Optional<Usuario> nomeEncontrado = usuarioRepository.findByNomeIgnoreCase(nome);
        return nomeEncontrado;
    }
    public Optional<Usuario> encontraPorEmail(String email){
        Optional<Usuario> emailEncontrado = usuarioRepository.findByEmailIgnoreCase(email);
        return emailEncontrado;
    }
    public List<Usuario> todosUsuarios(){
        List<Usuario> todos = usuarioRepository.findAll();
        return todos;
    }
    public Integer qtdeUsuario(){
        int qtd = Math.toIntExact(usuarioRepository.count());
        return qtd;
    }
    public ListaObj<Usuario> listar(){
        int qtdUsuarios = Math.toIntExact(usuarioRepository.count());
        ListaObj<Usuario> usuarios = new ListaObj<>(qtdUsuarios);
        usuarios.adiciona(usuarioRepository.findAll().toArray(new Usuario[qtdUsuarios]));

        if(usuarios.isEmpty()){
            return null;
        }

        return usuarios;
    }

    public UsuarioCriacaoDto criarAluno(UsuarioCriacaoDto alunoNovo){

        alunoNovo.setProfessor(false);
        List<String> materiasNome = new ArrayList<>();
        alunoNovo.getMaterias().forEach(materia -> materiasNome.add(materia.getNome()));
        alunoNovo.getMaterias().clear(); // salva vidas
        for (int i = 0; i < materiasNome.size(); i++) {
            Optional<Materia> materiaCorreta = materiaRepository.findByNomeContainingIgnoreCase(materiasNome.get(i));

            alunoNovo.addMateria(materiaCorreta.get());
        }
        String senhaCripto = passwordEncoder.encode(alunoNovo.getSenha());
        alunoNovo.setSenha(senhaCripto);

        Usuario aluno = usuarioRepository.save(UsuarioMapper.of(alunoNovo));
        return alunoNovo;

    }
    public ProfessorCriacaoDto criarProfessor(ProfessorCriacaoDto profNovo){

        profNovo.setProfessor(true);

        List<String> materiasNome = new ArrayList<>();
        profNovo.getMaterias().forEach(materia -> materiasNome.add(materia.getNome()));
        profNovo.getMaterias().clear(); // salva vidas2
        for (int i = 0; i < materiasNome.size(); i++) {
            Optional<Materia> materiaCorreta = materiaRepository.findByNomeContainingIgnoreCase(materiasNome.get(i));

            profNovo.addMateria(materiaCorreta.get());
        }


        String senhaCripto = passwordEncoder.encode(profNovo.getSenha());
        profNovo.setSenha(senhaCripto);

        Usuario professor = usuarioRepository.save(UsuarioMapper.of(profNovo));
       // adicionarMateriaUsuario(professor.getId(), materias);
        return profNovo;
    }


    public boolean existeEmail(String email) {
           return usuarioRepository.existsByEmailIgnoreCase(email);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmailIgnoreCase(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

}
