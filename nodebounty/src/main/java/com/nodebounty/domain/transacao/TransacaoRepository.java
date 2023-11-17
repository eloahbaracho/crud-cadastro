package com.nodebounty.domain.transacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nodebounty.domain.contacorrente.ContaCorrente;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {

	List<Transacao> findAllByEmissor(ContaCorrente conta);
	List<Transacao> findAllByReceptor(ContaCorrente conta);
	
}
