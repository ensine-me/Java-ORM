package school.sptech.ensine.util;

public class TabelaHash {
    private ListaLigada[] tab;

    public TabelaHash(int tamanho) {
        this.tab = new ListaLigada[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tab[i] = new ListaLigada();
        }
    }

    private int funcaoHash(int x) {
        return x % tab.length;
    }

    public void insere(int x) {
        int indice = funcaoHash(x);
        tab[indice].insereNode(x);
    }

    public boolean busca(int x) {
        int indice = funcaoHash(x);
        Node node = tab[indice].buscaNode(x);
        return node != null;
    }

    public boolean remove(int x) {
        int indice = funcaoHash(x);
        return tab[indice].removeNode(x);
    }

    public void exibe() {
        for (int i = 0; i < tab.length; i++) {
            System.out.print("Entrada " + i + ": ");
            if (tab[i].getTamanho() == 0) {
                System.out.println("Lista vazia");
            } else {
                tab[i].exibe();
            }
        }
    }
}
