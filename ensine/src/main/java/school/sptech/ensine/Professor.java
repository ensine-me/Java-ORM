package school.sptech.ensine;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Size;


@Entity
@PrimaryKeyJoinColumn()
public class Professor extends Usuario {

    @Size(max = 300)
    private String descricao;


    public Professor(boolean isProfessor, String nome, String email, String senha) {
        super(isProfessor, nome, email, senha);
    }

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


}
