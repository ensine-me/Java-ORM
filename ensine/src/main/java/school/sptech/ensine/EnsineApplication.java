package school.sptech.ensine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.repository.AulaRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EnsineApplication {
	@Autowired
	private static AulaRepository aulaRepository;

	public static void main(String[] args) {
		SpringApplication.run(EnsineApplication.class, args);
		System.out.println("amongus");

		List<Aula> aulas = aulaRepository.findAll();
		LocalDateTime horarioAtual = LocalDateTime.now();

		for (Aula aula : aulas) {
			if (aula.getDataHora().isEqual((horarioAtual).minusHours(1))) {
				System.out.println("HORA DA AULA AMIGOES");
			}
		}
	}

}
