package app.multimathsolver;

import app.multimathsolver.choletskymethod.CholetskyController;
import app.multimathsolver.user.Role;
import app.multimathsolver.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class MultiMathSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiMathSolverApplication.class, args);
	}

//	@KafkaListener(topics="msg")
//	public void msgListener(String msg){
//		System.out.println(msg);
//	}

}
