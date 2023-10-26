package school.sptech.ensine.util;

import school.sptech.ensine.domain.Aula;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvMaker {
    public static void gravaArquivoCsv(List<Aula> lista, String nomeArq) {
        FileWriter arq = null;  // objeto que representa o arquivo para escrita
        Formatter saida = null; // objeto que sera usado para escrita no arquivo

        nomeArq += ".csv";  // acrescenta a extensao .csv ao nome do arquivo

        // Bloco try-catch para abrir o arquivo
        try {
            // instancia o objeto de FileWriter - equivale a abrir o arquivo
            arq = new FileWriter(nomeArq);
            // instancia o objeto de Formatter, associando com o arq
            saida = new Formatter(arq);
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);     // encerra o programa com status de erro
        }

        // Bloco try-catch para gravar o arquivo
        try {
            // Percorre a lista de cachorros, obtendo um objeto dog por vez
            saida.format("Professor;Email;Preco;Titulo;Duracao(H);Materia;Hora e Data;Participantes;Privacidade\n");
            for (Aula aula : lista) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String dataFormatada = aula.getDataHora().format(formatter);
                saida.format("%s;%s;%.2f;%s;%.1f;%s;%s;%d;%s\n",
                        aula.getProfessor().getNome(),
                        aula.getProfessor().getEmail(),
                        aula.getProfessor().getPrecoHoraAula(),
                        aula.getTitulo(),
                        (float)(aula.getDuracaoSegundos() / 60) / 60,
                        aula.getMateria().getNome(),
                        dataFormatada,
                        aula.getLimiteParticipantes(),
                        aula.getPrivacidade().toString());
            }
        }
        catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar no arquivo");
        }
        finally {
            saida.close();    // fecha o objeto saida
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
            }
        }
    }
}
