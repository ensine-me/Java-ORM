package school.sptech.ensine;

import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.util.TabelaHashProfessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesteVannucchiParaAplicacaoHashNaoExcluiPfv {
    public static void main(String[] args) {
        List<Professor> lista = new ArrayList<>();

        List<String> nomes = Arrays.asList(
                "Adriana Alves", "Adriano Barbosa", "Aline Carvalho", "André Castro", "Beatriz Cardoso", "Bruno Cunha",
                "Camila Costa", "Carlos Dias", "Carolina Duarte", "César Fernandes", "Cláudia Ferreira", "Daniel Gomes",
                "Daniela Gonçalves", "Diego Lima", "Eduarda Lopes", "Eduardo Machado", "Fernanda Martins", "Fernando Melo",
                "Gabriela Mendes", "Giovanna Moreira", "Gustavo Nunes", "Helena Oliveira", "Igor Pinto", "Isabela Ramos",
                "Isadora Rodrigues", "Jorge Silva", "Joana Soares", "João Souza", "José Santos", "Juliana Tavares",
                "Júlio Teixeira", "Karen Torres", "Karine Vieira", "Laura Viana", "Lucas Xavier", "Luis Yamasaki",
                "Mariana Zanetti", "Mariane Alencar", "Matheus Alves", "Mirella Araújo", "Natalia Barros", "Natália Bittencourt",
                "Nelson Castro", "Nina Cunha", "Otávio Dias", "Otto Freitas", "Patrícia Gonçalves", "Paula Lima", "Paulina Lopes",
                "Pedro Marques", "Priscila Martins", "Quitéria Mendonça", "Rafael Mendes", "Raquel Oliveira", "Renato Pereira",
                "Ricardo Ramos", "Roberto Rocha", "Sandro Rodrigues", "Sara Silva", "Sérgio Soares", "Sofia Souza", "Thais Santos",
                "Thiago Teixeira", "Tiago Vaz", "Umberto Vasconcelos", "Ursula Ventura", "Valentina Vieira", "Valesca Xavier",
                "Vitor Zanetti", "Wagner Almeida", "Wellington Alves", "Wilma Barbosa", "Xavier Cabral", "Ximena Campos",
                "Yago Carvalho", "Yasmin Castro", "Yuri Cunha", "Zara Dantas", "Zeca Ferreira", "Zélia Gomes", "Zuleika Lima"
        );
        for (String nome:
             nomes) {
            Professor professor = new Professor();
            professor.setNome(nome);
            lista.add(professor);
        }

        System.out.println(lista);

        TabelaHashProfessor tabelaHashProfessor = new TabelaHashProfessor(0);
        for (Professor profesor:
             lista) {
            tabelaHashProfessor.insere(profesor);
        }

        tabelaHashProfessor.exibe();

        System.out.println(tabelaHashProfessor.busca("Adriana Alves"));
        System.out.println(tabelaHashProfessor.buscaLista(""));
        System.out.println();

    }
}
