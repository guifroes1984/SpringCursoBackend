package br.com.cadastro.curso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.cadastro.curso.services.AuditingService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditingService")
public class CursoApplication {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(CursoApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Inicio da Api de cadastro");
		
		SpringApplication.run(CursoApplication.class, args);
		LOGGER.info("Sucesso ao iniciar a Api de cadastro");
	}
	
	@Bean
    AuditorAware<String> auditorProvider() {
     return new AuditingService();
    }
}

