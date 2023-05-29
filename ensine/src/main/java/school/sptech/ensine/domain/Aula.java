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
    private Professor professor;

    private String titulo;

    @OneToOne
    private Materia materia;

    private LocalDateTime dataHora;

    // limiteParticipantes
    private int qtdAlunos;

    @OneToMany
    private List<Usuario> alunos;
    @OneToMany
    private List<Mensagem> mensagems;
    // Status
    private String status;

    @OneToOne
    private Chat chat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Get e set professor aqui __________________________________________________
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
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
    // limiteParticipantes
    public int getQtdAlunos() {
        return qtdAlunos;
    }
    // limiteParticipantes
    public void setQtdAlunos(int qtdAlunos) {
        this.qtdAlunos = qtdAlunos;
    }

    public void setAlunos(List<Usuario> alunos) {
        this.alunos = alunos;
    }

    public List<Usuario> getAlunos() {
        return alunos;
    }
    // Status
    public String getStatus() {
        return status;
    }
    // Status
    public void setStatus(String status) {
        this.status = status;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}