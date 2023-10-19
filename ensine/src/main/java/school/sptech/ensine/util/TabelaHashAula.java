package school.sptech.ensine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import school.sptech.ensine.domain.Aula;

public class TabelaHashAula {
    private ListaLigadaAula[] tab;
    private int localLetra;

    private char[] caracteres = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    //letras = quantidade de letras prédefinidas
    public TabelaHashAula(int localLetra) {
        int letras = 27; // alfabeto + "restante"
        this.tab = new ListaLigadaAula[letras];
        for (int i = 0; i < letras; i++) {
            tab[i] = new ListaLigadaAula();
        }
        this.localLetra = localLetra;
    }

    private Integer funcaoHashBusca(String aula) {
        if (localLetra < aula.toUpperCase().length()) {
            char letra = aula.toUpperCase().charAt(localLetra);
            for (int i = 0; i < caracteres.length; i++) {
                if (letra == caracteres[i]) {
                    return i;
                }
            }
            return caracteres.length;
        }
        return null;
    }

    public void insere(Aula x) {
        Integer indice = funcaoHashBusca(x.getTitulo());
        if (!Objects.isNull(indice)) {
            tab[indice].insereNode(x);
        }
    }

    public boolean busca(String nomeAula) {
        Integer indice = funcaoHashBusca(nomeAula);
        if(Objects.isNull(indice)) {
            return false;
        }
        NodeAula nodeProfessor = tab[indice].buscaNode(nomeAula);
        return nodeProfessor != null;
    }

    public List<Aula> buscaLista(String nomeAula) {
        Integer indice = funcaoHashBusca(nomeAula);
        if(Objects.isNull(indice)) {
            return new ArrayList<>();
        }
        return tab[indice].buscaListaNode(nomeAula);
    }

    public List<Aula> listAll() {
        List<Aula> listaAulas = new ArrayList<>();

        for (ListaLigadaAula lista : tab) {
            NodeAula atual = lista.getHead().getNext();
            while (atual != null) {
                listaAulas.add(atual.getInfo());
                atual = atual.getNext();
            }
        }
        return listaAulas;
    }

    public void exibe() {
        for (int i = 0; i < tab.length; i++) {
            if (i != caracteres.length) {
                System.out.print("Entrada " + caracteres[i] + ": ");
            } else {
                System.out.print("Entrada restante: ");
            }
            if (tab[i].getTamanho() == 0) {
                System.out.println("Lista vazia");
            } else {
                tab[i].exibe();
            }
        }
    }
}
