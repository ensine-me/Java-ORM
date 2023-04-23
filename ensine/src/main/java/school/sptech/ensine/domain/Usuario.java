package school.sptech.ensine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isProfessor;

    @NotBlank
    @Size(min = 2, max = 80)
    private String nome;

    @NotBlank
    @Email
    @Size(min = 9, max = 40)
    private String email;

    @NotBlank
    @Size(min = 8, max = 30)
    private String senha;

    @PastOrPresent
    private LocalDate dataNasc;

    @OneToMany(mappedBy = "usuario")
    private List<Materia> materias;

//    @ManyToMany(cascade=CascadeType.PERSIST)
//    @JoinTable(name = "usuario_materia",
//        joinColumns = @JoinColumn,
//        inverseJoinColumns = @JoinColumn)

    public static int converterEmAnos(Usuario usuario) {
        LocalDate dataNasc = usuario.getDataNasc();
        LocalDate dataAtual = LocalDate.now();

        Period periodo = Period.between(dataNasc, dataAtual);
        return periodo.getYears();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public List<Materia> getMaterias() {

        return materias;
    }

    public void setMaterias(List<Materia> materias) {

        this.materias = materias;
    }

//    public boolean getLogado(){
//        return ;
//    }
}
