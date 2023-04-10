package school.sptech.ensine;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 300)
    private String descricao;

    // tirei diasTrabalho pois isso talvez funcione melhor em outra tabela
    // também nao entendi exatamente o conceito dessa Lista qnd se vai cadastrar.

    // Competências teria de ser um List mas também dá erro.
    @NotEmpty
    @ElementCollection
    private List<String> competencias = new ArrayList<>();

    public Professor(boolean isProfessor, String nome, String email, String senha) {
        super(isProfessor, nome, email, senha);
    }

    public Professor() {}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<String> competencias) {
        this.competencias = competencias;
    }
}
