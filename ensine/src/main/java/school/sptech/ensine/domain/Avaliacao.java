package school.sptech.ensine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Professor professor;
    @ManyToOne
    private Usuario usuario;
    @DecimalMin("0.00")
    @DecimalMax("5.00")
    private Double nota;

    public Avaliacao() {
    }

    public Avaliacao(int id, Professor professor, Usuario usuario, Double nota) {
        this.id = id;
        this.professor = professor;
        this.usuario = usuario;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
