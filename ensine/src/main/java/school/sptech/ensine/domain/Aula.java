package school.sptech.ensine.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Usuario professor;

    private String titulo;

    @OneToOne
    private Materia materia;

    private LocalDateTime dataHora;

    private int qtdAlunos;

    @OneToMany
    private List<Usuario> alunos;
    @OneToMany
    private List<Mensagem> mensagems;

    private String status; // se a aula está iniciada, programada ou finalizada


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getQtdAlunos() {
        return qtdAlunos;
    }

    public void setQtdAlunos(int qtdAlunos) {
        this.qtdAlunos = qtdAlunos;
    }

    public void setAlunos(List<Usuario> alunos) {
        this.alunos = alunos;
    }

    public List<Usuario> getAlunos() {
        return alunos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
