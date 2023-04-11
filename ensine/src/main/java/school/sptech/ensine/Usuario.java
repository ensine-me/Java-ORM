package school.sptech.ensine;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "tipo_usuario")
public  class Usuario {
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


//     Criação de materia com tabela associativa
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "usuario_materia",
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    private List<Materia> materias = new ArrayList<>();



    public Usuario(boolean isProfessor, String nome, String email, String senha) {
        this.isProfessor = isProfessor;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {}

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
}
