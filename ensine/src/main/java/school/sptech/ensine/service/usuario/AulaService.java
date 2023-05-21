package school.sptech.ensine.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.ensine.api.security.jwt.GerenciadorTokenJwt;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.AulaRepository;
import school.sptech.ensine.repository.MateriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AulaService {
    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public int qtdeAulas(){
      int qtd = (int) aulaRepository.count();
      return qtd;
    }
    public List<Aula> aulas(){
        List<Aula> aulas = aulaRepository.findAll();
        return aulas;
    }
    public Optional<Aula> encontraAulaId(int id){
        Optional<Aula> aulaEncontrada = aulaRepository.findById(id);
        return aulaEncontrada;
    }
    public Boolean existePorId(int id){
        boolean existe = aulaRepository.existsById(id);
        return existe;
    }
    public List<Aula> encontraAulaPeloIdAluno(int id){
        List<Aula> aulasPeloIdAluno = aulaRepository.findByAlunosId(id);
        return aulasPeloIdAluno;
    }
    public Aula referenciaId(int id){
        Aula aula = aulaRepository.getReferenceById(id);
        return aula;
    }
    public Long countProfessorNome(String nome){
        Long qtde = aulaRepository.countByProfessorNomeEqualsIgnoreCase(nome);
        return qtde;
    }
    public Aula aulaNova(Aula aula){
        Aula novaAula = aulaRepository.save(aula);
        var usuarioClass = new Usuario();
        aula.getAlunos().forEach(usuarioClass::addObserver);
        usuarioClass.notifyObservers(aula, "Uma aula que você tinha interesse foi agendada!");
        return novaAula;
    }
}
