package school.sptech.ensine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class ReportAula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Usuario aluno;

    @ManyToOne
    private Aula aula;

    private Acontecimento acontecimento;

    private String descricao;

    private LocalDateTime dataHora;

    public enum Acontecimento {

        AUSENTE("Professor não compareceu para a aula"),
        ATRASADO("Professor se atrasou para a aula"),
        FUGIU_ASSUNTO("Aula não foi do assunto combinado"),
        OUTROS("Outros");

        @Override
        public String toString() {
            return displayName;
        }

        private final String displayName;

        Acontecimento(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public ReportAula() {

        this.dataHora = LocalDateTime.now();
    }

    public ReportAula(int id, Usuario aluno, Aula aula, Acontecimento acontecimento, String descricao, LocalDateTime dataHora) {
        this.id = id;
        this.aluno = aluno;
        this.aula = aula;
        this.acontecimento = acontecimento;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Acontecimento getAcontecimento() {
        return acontecimento;
    }

    public void setAcontecimento(Acontecimento acontecimento) {
        this.acontecimento = acontecimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
