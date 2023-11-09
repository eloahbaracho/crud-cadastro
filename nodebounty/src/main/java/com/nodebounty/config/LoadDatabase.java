package com.nodebounty.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.contacorrente.ContaCorrente;
import com.nodebounty.domain.contacorrente.ContaCorrenteRepository;
import com.nodebounty.domain.plano.Plano;
import com.nodebounty.domain.plano.PlanoRepository;

@Configuration
public class LoadDatabase {

	@Autowired
	private ClienteRepository repositoryClientes;
	
	@Autowired
	private ContaCorrenteRepository repositoryConta;

	@Autowired
	private PlanoRepository repositoryPlanos;
	
	@Autowired /* Injetando classe para criptografar senha, no padrão que o springsecurity exige */
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner initDatabase(PlanoRepository repository) {
		return args -> {
			// Salvando clientes para testes
			var senha = passwordEncoder.encode("12345678");
			Cliente cliente1 = new Cliente("6f78fac2-fe0d-4634-a3bf-025803557095", "Matheus Porto", "Rua Ali Perto",
					"01234-560", 11, "12.345.678-9", "123.456.789-10", "2003-03-03", "+55 11 99999-9999",
					"matheus@email.com", senha);
			
			repositoryClientes.save(cliente1);

			// Salvando planos para testes
			Plano Beauty = new Plano("Beauty", 5.00, "MAC, MakeB, Vult");
			Plano Tech = new Plano("Tech", 5.00, "KaBum, Pichau, TeraByte Shop");
			Plano Health = new Plano("Health", 5.00, "Growth, OficialFarma, Drogasil");

			repositoryPlanos.saveAll(Arrays.asList(Beauty, Tech, Health));
			
			// Salvando conta para testes
			ContaCorrente conta = new ContaCorrente("6f78fac2-fe0d-4634-a3bf-025803557096", cliente1, Beauty, 0.0, "12345678912345678912");
			repositoryConta.save(conta); 
		};
	}

}
