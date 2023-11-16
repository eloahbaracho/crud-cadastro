package com.nodebounty.domain.transacao;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.nodebounty.domain.contacorrente.ContaCorrente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TRANSACOES")
public class Transacao {

	@Id @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "IDTRANSACAO")
	private String idTransacao;
	
	@CreationTimestamp
	@Column(name = "DATATRANSACAO")
	private LocalDateTime dataTransacao;
	
	@Column(name = "VALORTRANSACAO")
	private Double valorTransacao;
	
	@OneToOne
	@JoinColumn(name = "EMISSOR", referencedColumnName = "IDCONTA")
	private ContaCorrente emissor;
	
	@OneToOne
	@JoinColumn(name = "RECEPTOR", referencedColumnName = "IDCONTA")
	private ContaCorrente receptor;
	
}
