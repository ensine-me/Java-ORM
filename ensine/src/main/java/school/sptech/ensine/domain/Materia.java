package school.sptech.ensine.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import school.sptech.ensine.enumeration.Disciplinas;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 4, max = 30)
    // Disciplinas
    private String nome;

    @ManyToMany(mappedBy = "materias")
    private List<Usuario> usuarios;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Disciplinas
    public String getNome() {
        return nome;
    }
    // Disciplinas
    public void setNome(String nome) {
        this.nome = nome;
    }

//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }

}
