package school.sptech.ensine.domain;

import jakarta.persistence.*;
import school.sptech.ensine.enumeration.TiposFormacao;

import java.time.LocalDateTime;

@Entity
public class Formacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dtInicio;
    private LocalDateTime dtTermino;
    private String instituicao;
    private String nomeCurso;
    private TiposFormacao tipoFormacao;
    @ManyToOne
    private Professor professor;
}
