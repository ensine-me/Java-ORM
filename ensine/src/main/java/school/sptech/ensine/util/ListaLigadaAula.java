package school.sptech.ensine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import school.sptech.ensine.domain.Aula;

public class ListaLigadaAula {
    private NodeAula head;

    public ListaLigadaAula() {
        this.head = new NodeAula(null);
    }

    public NodeAula getHead() {
        return head;
    }

    private final char[] caracteres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public void insereNode(Aula valor) {
        NodeAula novo = new NodeAula(valor);
        novo.setNext(head.getNext());
        this.head.setNext(novo);
    }

    public void exibe() {
        NodeAula atual = head.getNext();
        while (!Objects.isNull(atual)){
            System.out.println(atual.getInfo());
            atual = atual.getNext();
        }
    }

    public NodeAula buscaNode(String valor) {
        NodeAula atual = this.head.getNext();
        while(!Objects.isNull(atual)) {
            if (atual.getInfo().getTitulo().equalsIgnoreCase(valor.toUpperCase())) {
                return atual;
            }
            atual = atual.getNext();
        }
        return null;
    }

    public List<Aula> buscaListaNode(String valor) {
        List<Aula> list = new ArrayList<>();
        buscaRecursivaNode(head.getNext(), valor.toUpperCase(), list);
        return list;
    }

    private void buscaRecursivaNode(NodeAula node, String valor, List<Aula> list) {
        if (node != null) {
            if (node.getInfo().getTitulo().toUpperCase().startsWith(valor)) {
                list.add(node.getInfo());
            }
            buscaRecursivaNode(node.getNext(), valor, list);
        }
    }

    public boolean removeNode(String valor) {
        NodeAula anterior = this.head;
        NodeAula atual = this.head.getNext();
        while(!Objects.isNull(atual)) {
            if (atual.getInfo().getTitulo().equals(valor)) {
                anterior.setNext(atual.getNext());
                return true;
            }
            anterior = atual;
            atual = atual.getNext();
        }
        return false;
    }

    public int getTamanho() {
        NodeAula atual = this.head.getNext();
        int tamanho = 0;
        while(!Objects.isNull(atual)) {
            tamanho++;
            atual = atual.getNext();
        }
        return tamanho;
    }
    public void inverte() {
        NodeAula atual = getHead().getNext();
        NodeAula anterior = null;
        NodeAula proximo;

        while (atual != null) {
            proximo = atual.getNext();
            atual.setNext(anterior);
            anterior = atual;
            atual = proximo;
        }
        getHead().setNext(anterior);
    }
}
