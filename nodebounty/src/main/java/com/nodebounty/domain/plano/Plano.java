package com.nodebounty.domain.plano;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* Nenhuma mudança relevante. Apenas mudou o tipo do id pra String, pois como sugerido, o nome do plano será o próprio ID.
 * Como isso é um valor impossível de se gerar automáticamente, então eu não uso a notação @GeneratedValue
 * 
 * Como o H2 vai salvar as propriedades tudo em maiúsculo, preciso dizer que no BD a propriedade 'nome' vai ser 'NOME'
 * Por isso uso o @Column em todas propriedas. O @Table faz o mesmo mas com a tabela em si.
 *  */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "PLANOS")
public class Plano {

	@Id
	@Column(name = "IDPLANO")
	private String idPlano;
	@Column(name = "PORCENTAGEMCASHBACK")
	private double porcentagemCashback;
	@Column(name = "PARCERIAS")
	private String parcerias;

}
