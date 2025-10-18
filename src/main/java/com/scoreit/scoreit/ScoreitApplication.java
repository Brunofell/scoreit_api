package com.scoreit.scoreit;

import com.scoreit.scoreit.dto.member.Role;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ScoreitApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ScoreitApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(MemberRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByEmail("admin@scoreit.com") == null) {
				Member admin = new Member();
				admin.setName("Administrador");
				admin.setEmail("admin@scoreit.com");
				admin.setPassword(encoder.encode("admin123"));
				admin.setRole(Role.ROLE_ADMIN);
				admin.setEnabled(true);
				admin.setHandle("admin");
				repo.save(admin);
				System.out.println("Admin criado com sucesso");
			} else {
				System.out.println("Admin já existente");
			}
		};
	}

}
