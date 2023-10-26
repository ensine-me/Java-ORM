package school.sptech.ensine.util;

import org.w3c.dom.Node;
import school.sptech.ensine.domain.Aula;

public class Arvore {

    private NodeArvore raiz;

    public Arvore() {
        this.raiz = null;
    }

    public void adicionar(Integer valor, Aula aula){
        NodeArvore novoValor =  new NodeArvore(valor);
        if(raiz == null){
            novoValor.getAulas().add(aula);
            raiz = novoValor;
            System.out.println("RAIZ "+raiz);
        }else {
            NodeArvore atual = raiz;
            while (true) {
                if(novoValor.getIdAluno() == atual.getIdAluno()){
                    System.out.println("ATUAAAAAAAAAAAAAAAAAAAAL" + atual.getIdAluno());
                    for(Aula aulaAtual : atual.getAulas()){
                        if (aulaAtual.getId() != aula.getId()){
                            novoValor.getAulas().add(aulaAtual);
                        }
                    }
                    novoValor.getAulas().add(aula);
                    atual.setAulas(novoValor.getAulas());
                    break;
                } else if (novoValor.getIdAluno() < atual.getIdAluno()){
                    if(atual.getEsquerda() != null){
                        System.out.println("ESQUERDA " + atual);
                        if (atual.getIdAluno() == novoValor.getIdAluno() ){
                            for(Aula aulaAtual : atual.getAulas()){
                                if (aulaAtual.getId() != aula.getId()){
                                    novoValor.getAulas().add(aulaAtual);
                                }
                            }
                            novoValor.getAulas().add(aula);
                            atual.setAulas(novoValor.getAulas());
                            break;
                        }else{
                            atual = atual.getEsquerda();
                        }
                    } else {
                        System.out.println("Adiciona ESQUERDA " + atual);
                        novoValor.getAulas().add(aula);
                        atual.setEsquerda(novoValor);
                        break;
                    }
                } else {
                    if (atual.getDireita() != null){
                        System.out.println("Direita " + atual);
                        if (atual.getIdAluno() == novoValor.getIdAluno() ) {
                            for (Aula aulaAtual : atual.getAulas()) {
                                if (aulaAtual.getId() != aula.getId()){
                                    novoValor.getAulas().add(aulaAtual);
                                }
                            }
                            novoValor.getAulas().add(aula);
                            atual.setAulas(novoValor.getAulas());
                            break;
                        } else {
                            atual = atual.getDireita();
                        }
                    } else {
                        System.out.println("Adiciona DIREITA " + atual);
                        novoValor.getAulas().add(aula);
                        atual.setDireita(novoValor);
                        break;
                    }
                }
            }
        }
    }

    public NodeArvore procura(Integer idAluno, NodeArvore atual){
        if(atual == null){
            atual = raiz;
        }
        if (idAluno == atual.getIdAluno()){
            return atual;
        } else {
            if( idAluno < atual.getIdAluno()){
                if(atual.getEsquerda() == null){
                    return new NodeArvore(0);
                }else{
                    return procura(idAluno, atual.getEsquerda());
                }
            } else {
                if(atual.getDireita() == null){
                    return new NodeArvore(0);
                } else {
                    return procura(idAluno, atual.getDireita());
                }
            }
        }
    }


    public void exibe(NodeArvore atual){
        if(atual != null){
            exibe(atual.getEsquerda());
            System.out.println(atual.getAulas());
            exibe(atual.getDireita());
        }
    }
}
