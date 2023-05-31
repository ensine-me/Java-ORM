package school.sptech.ensine.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Professor extends Usuario {

    @Size(max = 900)
    private String descricao;

    @OneToMany(mappedBy = "professor")
    private List<Aula> aulas;

    private Double precoHoraAula;

    @OneToMany
    private List<Disponibilidade> disponibilidades;

    @OneToMany(mappedBy = "professor")
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

//    public List<Aula> getAulas() {
//        return aulas;
//    }
//
//    public void setAulas(List<Aula> aulas) {
//        this.aulas = aulas;
//    }

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
