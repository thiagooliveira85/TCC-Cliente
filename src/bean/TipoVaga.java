package bean;


public class TipoVaga {

	private int id;
	private double preco;
	private String nome;
	private int quantidadeVagas;
	
	public TipoVaga(double preco, String nome, int quantidadeVagas) {
		this.preco = preco;
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
		this.preco = preco;
	}
	public int getQuantidadeVagas() {
		return quantidadeVagas;
	}
	public void setQuantidadeVagas(int quantidadeVagas) {
		this.quantidadeVagas = quantidadeVagas;
	}
}