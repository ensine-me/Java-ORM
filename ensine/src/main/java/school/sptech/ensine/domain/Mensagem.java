package school.sptech.ensine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Aula aula;
    @ManyToOne
    private Usuario usuario;
    @Size(min = 1, max = 700)
    private String texto;
    @CreationTimestamp
    LocalDateTime dataEnvio;

    public Mensagem(Aula aula, Usuario usuario, String texto) {
        this.aula = aula;
        this.usuario = usuario;
        this.texto = texto;
    }

    public Mensagem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
