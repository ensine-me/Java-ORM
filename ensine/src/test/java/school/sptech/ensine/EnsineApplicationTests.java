package school.sptech.ensine;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") // Para rodar os testes com o profile de test
class EnsineApplicationTests {

	@Test
	void contextLoads() {
	}

}
