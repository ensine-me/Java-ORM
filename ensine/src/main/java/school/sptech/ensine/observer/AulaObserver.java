package school.sptech.ensine.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.repository.AulaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AulaObserver {

    @Autowired
    private AulaRepository aulaRepository;


    @Scheduled(fixedDelay = 2000)
    public void verificarAulas() {
        List<Aula> aulas = aulaRepository.findAll();
        LocalDateTime horarioAtual = LocalDateTime.now();

        for (Aula aula : aulas) {
            if (aula.getDataHora().isEqual((horarioAtual).minusHours(1))) {
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
                System.out.println("A aula " + aula.getTitulo() + " começa em 1 hora");
            }
        }
    }
}
