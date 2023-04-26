package school.sptech.ensine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import school.sptech.ensine.domain.Aula;
//import school.sptech.ensine.observer.MetodoObserver;
import school.sptech.ensine.observer.AulaObserver;
import school.sptech.ensine.repository.AulaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class EnsineApplication {

	public static void main(String[] args) throws InterruptedException {
	SpringApplication.run(EnsineApplication.class, args);
//
//		TimerTask timerTask = new MetodoObserver();
//		Timer timer = new Timer(true);
//		timer.scheduleAtFixedRate(timerTask, 0, 10);
	}

}
