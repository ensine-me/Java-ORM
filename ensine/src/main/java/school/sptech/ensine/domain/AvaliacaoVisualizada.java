package school.sptech.ensine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class AvaliacaoVisualizada {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Usuario aluno;
    @OneToOne
    private Aula aula;
    private boolean visualizada;

    public AvaliacaoVisualizada(int id, Usuario aluno, Aula aula) {
        this.id = id;
        this.aluno = aluno;
        this.aula = aula;
        this.visualizada = false;
    }

    public AvaliacaoVisualizada(Usuario aluno, Aula aula) {
        this.aluno = aluno;
        this.aula = aula;
        this.visualizada = false;
    }

    public AvaliacaoVisualizada() {
        this.visualizada = false;
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

    public boolean getVisualizada() {
        return visualizada;
    }

    public void setVisualizada(Boolean visualizada) {
        this.visualizada = visualizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
