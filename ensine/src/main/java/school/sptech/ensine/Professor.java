package school.sptech.ensine;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 300)
    private String descricao;

    // tirei diasTrabalho pois isso talvez funcione melhor em outra tabela
    // também nao entendi exatamente o conceito dessa Lista qnd se vai cadastrar.

    // Competências teria de ser um List mas também dá erro.
    private String competencias;


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


    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }
}
