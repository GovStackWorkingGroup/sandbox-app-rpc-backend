package global.govstack.rpcbackend;

import global.govstack.rpcbackend.model.Role;
import global.govstack.rpcbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RpcbackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(RpcbackendApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(RoleRepository roleRepo) {
    return (args) -> {
      Role role = new Role();
      role.setId(1);
      role.setName("ROLE_USER");
      roleRepo.save(role);
    };
  }
}
