package com.nodebounty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nodebounty.domain.contacorrente.Transacao;


@SpringBootApplication

public class NodebountyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NodebountyApplication.class, args);
		
		//As duas linhas abaixo foram criadas com o propósito de testar as classes transações e exibir o resultado no console.
		//Futuramente eu e a Eloah vamos retirar, a ideia é exibir um alert no navegador (JS) ou usar um JOptionPane SMD caso
		//Não dê certo.
		Transacao transacao = new Transacao();
        transacao.TesteSaque();
	}

}
