package school.sptech.ensine.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class UsuarioDto {

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.isProfessor = usuario.isProfessor();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dataNasc = usuario.getDataNasc();
        this.materias = usuario.getMaterias();
    }

    private int id;

    private boolean isProfessor;

    private String nome;

    private String email;

    private String senha;

    private LocalDate dataNasc;

    private List<Materia> materias;


    public static int converterEmAnos(school.sptech.ensine.domain.Usuario usuario) {
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

    public String pegarSenha() {
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

