package school.sptech.ensine.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Professor extends Usuario {

    @NotBlank
    @Size(max = 900)
    private String descricao;

    @OneToOne
    private Aula aula;

    private Double precoHoraAula;

    @OneToMany
    private List<Disponibilidade> disponibilidades;

    @OneToMany
    private List<Formacao> formacoes;

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

    public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }

    public void addDisponibilidade(Disponibilidade disponibilidade) {
        if(this.disponibilidades == null) {
            this.disponibilidades = new ArrayList<>();
        }
        this.disponibilidades.add(disponibilidade);
    }

    public void addFormacao(Formacao formacao) {
        if(this.formacoes == null) {
            this.formacoes = new ArrayList<>();
        }
        this.formacoes.add(formacao);
    }

    public Double getPrecoHoraAula() {
        return precoHoraAula;
    }

    public void setPrecoHoraAula(Double precoHoraAula) {
        this.precoHoraAula = precoHoraAula;
    }
}
