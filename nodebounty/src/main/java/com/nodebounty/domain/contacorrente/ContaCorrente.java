package com.nodebounty.domain.contacorrente;

import java.util.Random;

import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.plano.Plano;
import com.nodebounty.domain.transacao.Transacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CONTACORRENTE")
public class ContaCorrente {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "IDCONTA")
	private String idConta;
	
	/* Associação 1:1, uma conta corrente possui somente um cliente, a ligação ocorre por meio do atributo de Cliente.java -> IDCLIENTE */
	@OneToOne
	@JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
	private Cliente cliente;

	/* Associação 1:1, uma conta corrente possui somente um plano, a ligação ocorre por meio do atributo de Plano.java -> IDPLANO */
	@OneToOne
	@JoinColumn(name = "IDPLANO", referencedColumnName = "IDPLANO") 
	private Plano plano;

	@Column(name = "SALDOCONTA")
	protected double saldoConta = 0.0;
	
	/* Como a gente não vai usar o número para nenhuma operação matématica, acho que String é mais performático */
	@Column(name = "NUMEROCONTA")
	private String numeroConta;
	
	/* Método que será executado ANTES de salvar a conta no banco. Ele vai gerar um número aleatório de 20 digitos
	 * para a conta corrente e converter para string*/
    @PrePersist
    protected void onCreate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        this.numeroConta = sb.toString();
    }

	
	public String getnumeroConta() {
		return numeroConta;
	}
	
	public void setnumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Double getSaldo() {
		return saldoConta = 0.0;
		
	}
	
	public void setSaldo(Double saldoConta) {
		this.saldoConta = saldoConta;
	}
	
	
	
}
