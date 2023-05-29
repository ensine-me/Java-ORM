package school.sptech.ensine.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.ensine.api.security.jwt.GerenciadorTokenJwt;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.ListaObj;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.repository.AulaRepository;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AulaService {
    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ListaObj<Aula> getAulasPorStatus(String status) {
        ListaObj<Aula> listaObj = new ListaObj<>(Math.toIntExact(aulaRepository.countByStatus(status)));
        listaObj.adiciona(aulaRepository.findByStatus(status));
        return listaObj;
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
    //        aula.setProfessor(usuarioRepository.findProfessorById(idProfessorAula));
    public Aula aulaNova(Aula aula){
        Integer idProfessorAula = aula.getProfessor().getId();
        Optional<Professor> byId = usuarioRepository.findProfessorById(idProfessorAula);
        aula.setProfessor(byId.get());
        String nome = aula.getMateria().getNome();
        Optional<Materia> materia = materiaRepository.findByNomeContainingIgnoreCase(nome);
        if(materia.isEmpty()){
            throw new IllegalArgumentException("MATERIA NAO EXISTE!");
        }
        aula.setMateria(materia.get());
        Aula novaAula = aulaRepository.save(aula);

        var usuarioClass = new Professor();

        aula.getAlunos().forEach(usuarioClass::addObserver);
        usuarioClass.notifyObservers(aula, "Uma aula que você tinha interesse foi agendada!");
        return novaAula;
    }

    public Optional<Aula> atualizarStatusAula(int id, String status) {
        Optional<Aula> aula = aulaRepository.findById(id);
        aula.get().setStatus(status);
        aulaRepository.save(aula.get());
        return aula;
    }
}
