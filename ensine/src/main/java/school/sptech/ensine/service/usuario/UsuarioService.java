package school.sptech.ensine.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.ensine.DTO.UsuarioDto;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;
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

        public UsuarioCriacaoDto criarAluno(UsuarioCriacaoDto alunoNovo){

            alunoNovo.setProfessor(false);

            List<String> materias = new ArrayList<>();
            alunoNovo.getMaterias().forEach(materia -> materias.add(materia.getNome()));
            alunoNovo.getMaterias().clear();

            Usuario aluno = usuarioRepository.save(UsuarioMapper.of(alunoNovo));
            adicionarMateriaUsuario(aluno.getId(), materias);
            return alunoNovo;
        }
    public UsuarioCriacaoDto criarProfessor(UsuarioCriacaoDto profNovo){

        profNovo.setProfessor(true);

        List<String> materias = new ArrayList<>();
        profNovo.getMaterias().forEach(materia -> materias.add(materia.getNome()));
        profNovo.getMaterias().clear();

        Usuario professor = usuarioRepository.save(UsuarioMapper.of(profNovo));
        adicionarMateriaUsuario(professor.getId(), materias);
        return profNovo;
    }

    public void adicionarMateriaUsuario(int id, List<String> nomesMaterias){
        //List<Materia> materias = new ArrayList<>();
        Optional<Usuario> usuarioTemp = usuarioRepository.findById(id);
        Usuario usuario = usuarioTemp.get();

        for(String nome: nomesMaterias){

            Optional<Materia> materiaAtual = materiaRepository.findByNomeContainingIgnoreCase(nome);
            Materia materia = materiaAtual.get();
            usuario.getMaterias().add(materia);
        }
        //materias.forEach(materia -> usuario.getMaterias().add(materia));

        usuarioRepository.save(usuario);
    }
    public boolean existeEmail(String email) {
           return usuarioRepository.existsByEmailIgnoreCase(email);
    }

}
