package school.sptech.ensine.util;

import java.util.Objects;

public class ListaLigada {
    private Node head;

    public ListaLigada() {
        this.head = new Node(0);
    }

    public Node getHead() {
        return head;
    }

    public void insereNode(int valor) {
        Node novo = new Node(valor);
        novo.setNext(head.getNext());
        this.head.setNext(novo);
    }

    public void exibe() {
        Node atual = head.getNext();
        while (!Objects.isNull(atual)){
            System.out.println(atual.getInfo());
            atual = atual.getNext();
        }
    }

    public Node buscaNode(int valor) {
        Node atual = this.head.getNext();
        while(!Objects.isNull(atual)) {
            if (atual.getInfo() == valor) {
                return atual;
            }
            atual = atual.getNext();
        }
        return null;
    }

    public boolean removeNode(int valor) {
        Node anterior = this.head;
        Node atual = this.head.getNext();
        while(!Objects.isNull(atual)) {
            if (atual.getInfo() == valor) {
                anterior.setNext(atual.getNext());
                return true;
            }
            anterior = atual;
            atual = atual.getNext();
        }
        return false;
    }

    public int getTamanho() {
        Node atual = this.head.getNext();
        int tamanho = 0;
        while(!Objects.isNull(atual)) {
            tamanho++;
            atual = atual.getNext();
        }
        return tamanho;
    }

    public void concatena(ListaLigada lista) {
        Node ultimo = this.getHead();
        while (ultimo.getNext() != null) {
            ultimo = ultimo.getNext();
        }
        ultimo.setNext(lista.getHead().getNext());
    }

    public boolean compara(ListaLigada lista) {
        Node atual1 = this.getHead().getNext();
        Node atual2 = lista.getHead().getNext();

        while (atual1 != null && atual2 != null) {
            if (atual1.getInfo() != atual2.getInfo()) {
                return false;
            }
            atual1 = atual1.getNext();
            atual2 = atual2.getNext();
        }

        return atual1 == null && atual2 == null;
    }

    public void inverte() {
        Node atual = getHead().getNext();
        Node anterior = null;
        Node proximo;

        while (atual != null) {
            proximo = atual.getNext();
            atual.setNext(anterior);
            anterior = atual;
            atual = proximo;
        }

        getHead().setNext(anterior);
    }

// Versões recursivas

    public void exibeRecursivo(Node node) {
        if (node != null) {
            System.out.println(node.getInfo());
            exibeRecursivo(node.getNext());
        }
    }

    public Node buscaNodeRecursivo(Node node, int valor) {
        if (node == null || node.getInfo() > valor) {
            return null;
        }
        if (node.getInfo() == valor) {
            return node;
        }
        return buscaNodeRecursivo(node.getNext(), valor);
    }

    public boolean removeNodeRecursivo(Node anterior, Node atual, int valor) {
        if (atual == null) {
            return false;
        }
        if (atual.getInfo() == valor) {
            anterior.setNext(atual.getNext());
            return true;
        }
        return removeNodeRecursivo(atual, atual.getNext(), valor);
    }

    public int getTamanhoRecursivo(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getTamanhoRecursivo(node.getNext());
    }
    public void inserirAposPrimeiroImpar(int valor) {
        Node novo = new Node(valor);
        Node atual = head.getNext();
        while (!Objects.isNull(atual)){
            if(atual.getInfo() % 2 != 0) {
                novo.setNext(atual.getNext());
                atual.setNext(novo);
                return;
            }
            if(Objects.isNull(atual.getNext())) {
                atual.setNext(novo);
                novo.setNext(null);
                return;
            }
            atual = atual.getNext();
        }
    }

    public int getElemento(int indice) {
        int controle = 0;
        Node atual = head.getNext();
        while (!Objects.isNull(atual) && controle < indice){
            atual = atual.getNext();
            controle++;
        }
        return atual.getInfo();
    }

    public boolean removeOcorrencias(int valor) {
        boolean controle = false;
        Node atual = head.getNext();
        Node anterior = head;
        while (!Objects.isNull(atual)){
            if(atual.getInfo() == valor) {
                anterior.setNext(atual.getNext());
                controle = true;
            }
            anterior = atual;
            atual = atual.getNext();
        }
        return controle;
    }

    public void arrayToList(int[] vetor) {
        if(vetor.length == 0) {
            System.out.println("Operação inválida");
            return;
        }
        ListaLigada listaLigada = new ListaLigada();
        for(int i = 0; i < vetor.length - 1; i++) {
            listaLigada.insereNode(i);
        }
        listaLigada.inverte();
    }

}
