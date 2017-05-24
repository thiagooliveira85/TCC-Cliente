package bean;

import java.text.DecimalFormat;


public class TipoVaga {

	private int id;
	private double preco;
	private String nome;
	private int quantidadeVagas;
	
	public TipoVaga(double preco, String nome, int quantidadeVagas) {
		setPreco(preco);
		this.nome = nome;
		this.quantidadeVagas = quantidadeVagas;
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
	public void setPreco(double preco) {
		double valor = preco;
		DecimalFormat formato = new DecimalFormat("#.##");      
		valor = Double.valueOf(formato.format(valor));
		this.preco = valor;
	}
	public int getQuantidadeVagas() {
		return quantidadeVagas;
	}
	public void setQuantidadeVagas(int quantidadeVagas) {
		this.quantidadeVagas = quantidadeVagas;
	}
}