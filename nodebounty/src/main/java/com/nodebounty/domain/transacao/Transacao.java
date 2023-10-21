package com.nodebounty.domain.transacao;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Transacao {

	static NumberFormat FormatadorMoeda = new DecimalFormat("R$ ##,#0.00");
	
	public static String DoubleToString(Double valor) {
		return FormatadorMoeda.format(valor);
	}
}
