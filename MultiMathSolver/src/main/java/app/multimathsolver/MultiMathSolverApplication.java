package app.multimathsolver;

import app.multimathsolver.choletskymethod.CholetskyController;
import app.multimathsolver.user.Role;
import app.multimathsolver.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MultiMathSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiMathSolverApplication.class, args);
	}
//    @Bean
//    public CommandLineRunner demo(RoleRepository roleRepo) {
//        return (args) -> {
//            Role role=new Role();
//            role.setName("ROLE_USER");
//            roleRepo.save(role);
//        };
//    }

}
