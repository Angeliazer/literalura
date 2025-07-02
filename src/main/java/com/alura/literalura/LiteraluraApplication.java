package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	private final LivroRepository livroRepository;
	public LiteraluraApplication(LivroRepository livroRepository){
		this.livroRepository = livroRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(livroRepository);

		principal.exibirMenu();

	}
}
