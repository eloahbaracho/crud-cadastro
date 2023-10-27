package com.nodebounty.domain.transacao;

import com.nodebounty.domain.contacorrente.ContaCorrente;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TransacaoController extends ContaCorrente {

	void depositar(Double valor) {
		saldoConta = getSaldo() + valor;
	}
	
	void sacar(Double valor) {
		saldoConta = getSaldo() - valor;
	}
	

	/* public class Moeda {

	static NumberFormat FormatadorMoeda = new DecimalFormat("R$ ##,#0.00");
	
	public static String DoubleToString(Double valor) {
		return FormatadorMoeda.format(valor);
		}
	}
	*/
	
	public void transferir(String numeroConta, Double valor) {
		
	}
}
