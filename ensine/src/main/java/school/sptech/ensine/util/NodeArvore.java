package school.sptech.ensine.util;

import java.util.ArrayList;
import java.util.List;
import school.sptech.ensine.domain.Aula;

public class NodeArvore{

    private NodeArvore esquerda;
    private Integer idAluno;
    private List<Aula> aulas;
    private NodeArvore direita;

    public NodeArvore(Integer idAluno) {
        this.esquerda = null;
        this.idAluno = idAluno;
        this.aulas = new ArrayList<>();
        this.direita = null;
    }

    public NodeArvore getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NodeArvore esquerda) {
        this.esquerda = esquerda;
    }


    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public NodeArvore getDireita() {
        return direita;
    }

    public void setDireita(NodeArvore direita) {
        this.direita = direita;
    }


    @Override
    public String toString() {
        return "NodeArvore{" +
                "esquerda=" + esquerda +
                ", idAluno=" + idAluno +
                ", aulas=" + aulas +
                ", direita=" + direita +
                '}';
    }
}
