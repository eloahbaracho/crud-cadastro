package com.nodebounty.domain.cartao;

import java.time.LocalDate;
import java.util.Random;

import com.nodebounty.domain.plano.Plano;

	@@ -20,27 +21,58 @@
 * Como o H2 vai salvar as propriedades tudo em maiúsculo, preciso dizer que no BD a propriedade 'titularCartao' vai ser 'TITULARCARTAO'
 * Por isso uso o @Column em todas. O @Table faz o mesmo mas com a tabela em si. */

@Getter
@Entity
@AllArgsConstructor
@Table(name = "CARTOES")
public class Cartao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Preencher o objeto cartao1 com valores
	@Column(name = "IDCARTAO")
	private Long idCartao = 1L;
	@Column(name = "VALIDADECARTAO")
	private LocalDate validadeCartao = LocalDate.of(2025, 12, 31); // Representa 31 de dezembro de 2025
	@Column(name = "NUMEROCARTAO")
	private String numeroCartao = "1234567890123456"; // Número do cartão como String
	@Column(name = "CVCCARTAO")
	private Short cvcCartao = 123;
	
	@OneToOne // Indica o relacionamento 1:1
	@JoinColumn(name = "PLANONOME", referencedColumnName = "IDPLANO") // Cria a coluna no banco chamada "plano_nome" e faz a referência ao id do plano
	private Plano plano; // Adiciona um campo para representar o Plano atrelado ao cartão

	public Cartao() {
		gerarCartao();
	}

	public void gerarCartao(){
		this.validadeCartao = gerarValidade();
		this.numeroCartao = gerarNumero();
		this.cvcCartao = gerarCvc();
	}

	private static Short gerarCvc() {
        Random random = new Random();
        return (short) (100 + random.nextInt(900));
    }

	private static String gerarNumero() {
        Random random = new Random();
        StringBuilder numeroCartao = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int digito = random.nextInt(10);
            numeroCartao.append(digito);
        }

        return numeroCartao.toString();
    }

	private static LocalDate gerarValidade() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate validadeCartao = dataAtual.plusYears(1); // Adicione 1 ano à data atual
        return validadeCartao;
    }

}
