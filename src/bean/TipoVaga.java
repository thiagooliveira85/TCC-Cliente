package bean;

import java.text.NumberFormat;


public class TipoVaga {

	private int id;
	private double preco;
	private String nome;
	private int quantidadeVagas;
	
	private String precoFormatado;
	
	public TipoVaga(double preco, String nome, int quantidadeVagas) {
		setPreco(preco);
		setNome(nome);
		setQuantidadeVagas(quantidadeVagas);;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	
	/**
	 * Seta o preço double e formata em reais R$ 00,00 para exibição em tela
	 * @param preco 
	 * @param precoFormatado
	 */
	public void setPreco(double preco) {
		this.preco = preco;
		this.precoFormatado = NumberFormat.getCurrencyInstance().format(this.preco);
	}
	public int getQuantidadeVagas() {
		return quantidadeVagas;
	}
	public void setQuantidadeVagas(int quantidadeVagas) {
		this.quantidadeVagas = quantidadeVagas;
	}
	public String getPrecoFormatado() {
		return precoFormatado;
	}
	
	public void formataPreco(double preco) {
		
	}
}