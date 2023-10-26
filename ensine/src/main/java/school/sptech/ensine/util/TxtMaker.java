package school.sptech.ensine.util;

import school.sptech.ensine.domain.Aula;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TxtMaker {
    public static void gravaRegistro(String nomeArq, String registro) {
        BufferedWriter saida = null;

        // Abre o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo!");
            erro.printStackTrace();
        }

        // Grava o registro e finaliza
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao gravar ou fechar o arquivo");
            erro.printStackTrace();
        }
    }

    public static void gravaArquivoTxt(List<Aula> lista, String nomeArq) {
        int contaRegistroDado = 0;

        // Monta o registro de header
        String header = "00AULA2023";
        header+= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header+= "01";

        // Grava o registro de header
        gravaRegistro(nomeArq, header);

        // Monta e grava o corpo (registros de dados)
        for (Aula a : lista) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String dataFormatada = a.getDataHora().format(formatter);

            String corpo = "02";
            corpo += String.format("%-20.20s",a.getProfessor().getNome());
            corpo += String.format("%-30.30s",a.getProfessor().getEmail());
            corpo += String.format("%06.2f",a.getProfessor().getPrecoHoraAula());
            corpo += String.format("%-25.25s",a.getTitulo());
            corpo += String.format("%4.1f",(float)(a.getDuracaoSegundos() / 60) / 60);
            corpo += String.format("%-17.17s",a.getMateria().getNome());
            corpo += String.format("%16.16s",dataFormatada);
            corpo += String.format("%2d",a.getLimiteParticipantes());
            corpo += String.format("%-8.8s",a.getPrivacidade().toString());

            gravaRegistro(nomeArq, corpo);
            contaRegistroDado++;
        }

        // Monta o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d", contaRegistroDado);

        // Grava o registro de trailer
        gravaRegistro(nomeArq, trailer);
    }

}
