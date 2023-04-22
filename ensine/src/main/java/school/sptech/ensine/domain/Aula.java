package school.sptech.ensine.domain;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import school.sptech.ensine.observer.AulaObserver;
import school.sptech.ensine.observer.AulaObserverClass;
import school.sptech.ensine.repository.AulaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Entity
public class Aula implements Flow.Publisher<Aula> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Professor professor;

    private String titulo;

    @OneToOne
    private Materia materia;

    private LocalDateTime dataHora;

    private int qtdAlunos;

    @OneToMany
    private List<Usuario> alunos;


    public void notificarObservadores() {
        for (AulaObserver observer : AulaObserverClass.getObservers()) {
            observer.update(this);
        }
    }

    public void notificar() {
        SubmissionPublisher<Aula> publisher = new SubmissionPublisher<>();
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime umaHoraAntesDaAula = dataHora.minusHours(1);
        if (agora.isAfter(umaHoraAntesDaAula) && agora.isBefore(dataHora)) {
            String mensagem = String.format("Olá, sua aula de %s com o professor %s começa em uma hora!", titulo, professor.getNome());
            publisher.submit(this);
            publisher.close();
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Aula> subscriber) {
        SubmissionPublisher<Aula> publisher = new SubmissionPublisher<>();
        publisher.subscribe(subscriber);
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
}
