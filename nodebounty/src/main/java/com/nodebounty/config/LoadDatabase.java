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
			var cliente1 = new Cliente("6f78fac2-fe0d-4634-a3bf-025803557095", "Matheus Porto", "Rua Ali Perto",
					"01234-560", 11, "12.345.678-9", "123.456.789-10", "2003-03-03", "+55 11 99999-9999",
					"matheus@gmail.com", senha);
			var cliente2 = new Cliente("6f78fac2-f00d-4634-a3bf-025803557095", "Growth", "Rua Dois",
					"01244-560", 120, "13.345.678-9", "133.456.789-10", "2003-02-03", "+55 11 99999-9999",
					"eloah@gmail.com", senha);
			var clienteEmpresa1 = new Cliente("1f78fac2-f00d-4634-a3bf-025803557095", "MAC", "Rua Dois",
					"01244-560", 120, "33.345.678-9", "133.453.789-10", "2000-02-03", "+55 11 99999-9999",
					"mac@gmail.com", senha);
			var clienteEmpresa2 = new Cliente("1f98fac2-f00d-4634-a3bf-025803557095", "Kabum", "Rua Dois",
					"01244-560", 120, "39.345.678-9", "133.483.789-10", "2000-02-03", "+55 11 99999-9999",
					"kabum@gmail.com", senha);
			var clienteEmpresa3 = new Cliente("1998fac2-f00d-4634-a3bf-025803557095", "Growth", "Rua Dois",
					"01244-560", 120, "37.345.678-9", "133.493.889-10", "2000-02-03", "+55 11 99999-9999",
					"growth@gmail.com", senha);
			repositoryClientes.saveAll(Arrays.asList(cliente1, cliente2, clienteEmpresa1, clienteEmpresa2, clienteEmpresa3));

			// Salvando planos para testes
			Plano Beauty = new Plano("Beauty", 5.00, "MAC, MakeB, Vult");
			Plano Tech = new Plano("Tech", 5.00, "KaBum, Pichau, TeraByte Shop");
			Plano Health = new Plano("Health", 5.00, "Growth, OficialFarma, Drogasil");
			repositoryPlanos.saveAll(Arrays.asList(Beauty, Tech, Health));
			
			// Salvando conta para testes
			var conta1 = new ContaCorrente("6f78fac2-fe0d-4634-a3bf-025803557096", cliente1, Beauty, 0.0, 0.0,"12345678912345678912");
			var conta2 = new ContaCorrente("6f88fac2-fe0d-4634-a3bf-025803557096", cliente2, Tech, 0.0, 0.0,"12345678912945678912");
			var contaEmpresa1 = new ContaCorrente("6f78fac2-fe0d-4434-a3bf-025803557096", clienteEmpresa1, Beauty, 0.0, 0.0,"12345678912345678912");
			var contaEmpresa2 = new ContaCorrente("6f88fac2-fe0d-4834-a3bf-025803557096", clienteEmpresa2, Tech, 0.0, 0.0,"12345678912945678912");
			var contaEmpresa3 = new ContaCorrente("6f78fac2-fe0d-4934-a3bf-025803557096", clienteEmpresa3, Health, 0.0, 0.0,"12345678912345678912");
				
			 var contaEmpresa1Dados = repositoryConta.save(contaEmpresa1); 
			 var contaEmpresa2Dados = repositoryConta.save(contaEmpresa2);
			 var contaEmpresa3Dados = repositoryConta.save(contaEmpresa3);
			 
			 System.out.println(("Empresa: " + contaEmpresa1Dados.getCliente().getNome() + " Numero: " + contaEmpresa1Dados.getNumeroConta()));
			 System.out.println(("Empresa: " + contaEmpresa2Dados.getCliente().getNome() + " Numero: " + contaEmpresa2Dados.getNumeroConta()));
			 System.out.println(("Empresa: " + contaEmpresa3Dados.getCliente().getNome() + " Numero: " + contaEmpresa3Dados.getNumeroConta()));
		};
	}

}
