package school.sptech.ensine.service.usuario;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.ensine.api.security.jwt.GerenciadorTokenJwt;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.AvaliacaoVisualizada;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.ListaObj;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.enumeration.Privacidade;
import school.sptech.ensine.enumeration.Status;
import school.sptech.ensine.repository.AulaRepository;
import school.sptech.ensine.repository.AvaliacaoVisualizadaRepository;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.UsuarioRepository;
import school.sptech.ensine.service.usuario.dto.ContagemAula;
import school.sptech.ensine.util.CsvMaker;
import school.sptech.ensine.util.TxtMaker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import school.sptech.ensine.util.Arvore;
import school.sptech.ensine.util.NodeArvore;

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

    @Autowired
    private AvaliacaoVisualizadaRepository avaliacaoVisualizadaRepository;

    public List<Aula> getProfessorIdSolicitado(int id){
        return this.aulaRepository.findByProfessorIdUsuarioSolicitado(id);
    }

    public List<Aula> getAulasConcluidasPorProfessorAndUsuario(Usuario aluno, Professor professor) {
        return this.aulaRepository.findByUsuarioAndProfessorAndStatusConcluida(aluno, professor);
    }

    public List<Aula> getAulasPorDescricao(String termoDeBusca) {
        return this.aulaRepository.findByDescricaoContainingIgnoreCaseAndNormalize(termoDeBusca);
    }

    public List<Aula> getAulasPorTitulo(String termoDeBusca) {
        return this.aulaRepository.findByTituloContainingIgnoreCaseAndNormalize(termoDeBusca);
    }

    public List<Aula> getAulasPorMateria(String termoDeBusca) {
        return this.aulaRepository.findByMateriaContainingIgnoreCaseAndNormalize(termoDeBusca);
    }


//    public List<Aula> getAulaPorSubMateria(String subMateria){
//
//    }

    public int qtdeAulas(){
        return (int) aulaRepository.count();
    }

    public List<Aula> aulas(){
        return aulaRepository.findAll();
    }
    public ListaObj<Aula> getAulasPorStatus(String status) {
        ListaObj<Aula> listaObj = new ListaObj<>(Math.toIntExact(aulaRepository.countByStatus(status)));
        listaObj.adiciona(aulaRepository.findByStatus(status));
        return listaObj;
    }
    public List<Aula> getAulasPorPrivacidade(Privacidade privacidade) {
        List<Aula> aulas = aulaRepository.findByPrivacidadeAndStatus(privacidade, Status.AGENDADO);
        return aulas;
    }
    public Optional<Aula> encontraAulaId(int id){
        Optional<Aula> aulaEncontrada = aulaRepository.findById(id);

        if (aulaEncontrada.isEmpty()){

            throw new IllegalArgumentException("Essa aula não existe");
        }

        return aulaEncontrada;
    }
    public Boolean existePorId(int id){
        return aulaRepository.existsById(id);
    }
    public List<Aula> encontraAulaPeloIdAluno(int id) {

        List<Aula> aulas = aulaRepository.findAll();

        Arvore arvore = new Arvore();

        for (Aula aula : aulas) {
            for (Usuario aluno : aula.getAlunos()) {
                System.out.println("ALUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUNO "+aluno.getIdUsuario());
                arvore.adicionar(aluno.getIdUsuario(), aula);
            }
        }
        arvore.exibe(null);

        NodeArvore nodeAula = arvore.procura(id, null);

//        Aula aulaAtual = aulaRepository.getById(aula.getIdAula());

        return nodeAula.getAulas();
    }

    public Aula referenciaId(int id){
        return aulaRepository.getReferenceById(id);
    }
    public Long countProfessorNome(String nome){
        return aulaRepository.countByProfessorNomeEqualsIgnoreCase(nome);
    }
    public Long countProfessorId(int id) {return aulaRepository.countByProfessorIdUsuario(id);}
    public Long countProfessorIdConcluida(int id) {return aulaRepository.countConcluidasByProfessorIdUsuario(id);}
    public Long countProfessorIdAgendada(int id) {return aulaRepository.countAgendadasByProfessorIdUsuario(id);}
    public Aula aulaNova(Aula aula){
        aula.setProfessor(usuarioRepository.findProfessorByIdUsuario(aula.getProfessor().getIdUsuario()).get());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA "+aula.getMateria());
        String nome = aula.getMateria().getNome();

        Optional<Materia> materia = materiaRepository.findByNomeContainingIgnoreCase(nome);
        if(materia.isEmpty()){
            throw new IllegalArgumentException("MATERIA NAO EXISTE!");
        }
        aula.setMateria(materia.get());
        Aula novaAula = aulaRepository.save(aula);

        var usuarioClass = new Usuario();

        aula.getAlunos().forEach(usuarioClass::addObserver);
        usuarioClass.notifyObservers(aula, "Olá, uma aula que você tinha interesse foi agendada!");
        return novaAula;
    }

    public Optional<Aula> atualizarStatusAula(int id, Status status) {
        Optional<Aula> aula = aulaRepository.findById(id);

        if (aula.isEmpty()){
            throw new IllegalArgumentException("Essa aula não existe");
        }
        aula.get().setStatus(status);
        aulaRepository.save(aula.get());
        return aula;
    }

    public Optional<Aula> adicionarAluno(Aula aula, Usuario usuario) {
        List<Usuario> alunos = aula.getAlunos();
        alunos.add(usuario);
        aula.setAlunos(alunos);
        return Optional.of(aulaRepository.save(aula));
    }

    public List<ContagemAula> contagemAulas(int idProfessor){
        Optional<Professor> professor = usuarioRepository.findProfessorByIdUsuario(idProfessor);
       return aulaRepository.contagemAulas(professor.get());
    }

    public List<Aula> listAulasByProfessorId (int idProfessor){
        List<Aula> aulas = aulaRepository.findByProfessor_IdUsuario(idProfessor);
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");
        String dataFormatada = agora.format(formatter);
        try {
            CsvMaker.gravaArquivoCsv(aulas, aulas.get(0).getProfessor().getNome() + dataFormatada);
        } catch (Exception e) {

        }
        try {
            TxtMaker.gravaArquivoTxt(aulas, aulas.get(0).getProfessor().getNome() + dataFormatada);
        } catch (Exception e) {

        }
        return aulaRepository.findByProfessor_IdUsuario(idProfessor);
    }

    public List<Aula> listAulasByAlunoId (int idAluno){
        return aulaRepository.findByAlunos_IdUsuario(idAluno);
    }

    public void finalizarAula (int id){
        Aula aula = referenciaId(id);
        aula.setStatus(Status.CONCLUIDA);
        aulaRepository.save(aula);
        AvaliacaoVisualizada a;
        for (Usuario u:
             aula.getAlunos()) {
            a = new AvaliacaoVisualizada(u, aula);
            avaliacaoVisualizadaRepository.save(a);
        }
    }
}
