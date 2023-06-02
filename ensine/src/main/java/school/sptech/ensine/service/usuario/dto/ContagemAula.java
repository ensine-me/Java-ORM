package school.sptech.ensine.service.usuario.dto;


public class ContagemAula {

    private String nome;
    private Long total;


    public ContagemAula(String materiaNome, Long total) {
        this.nome = materiaNome;
        this.total = total;
    }

    // Getters and setters

    public String getMateriaNome() {
        return nome;
    }

    public void setMateriaNome(String materiaNome) {
        this.nome = materiaNome;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
