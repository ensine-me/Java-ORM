package school.sptech.ensine.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Size;


@Entity
@PrimaryKeyJoinColumn()
public class Professor extends Usuario {

    @Size(max = 300)
    private String descricao;

    @OneToOne
    private Aula aula;

    public Professor() {}

    @Override
    public int getId() {
        return super.getId();
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
