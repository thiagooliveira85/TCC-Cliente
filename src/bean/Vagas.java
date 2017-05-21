package bean;

public class Vagas {
	
	private String tipo;
	private String altura;
	private String largura;
	private String comprimento;
	private int quantidade;
	private double valor;
	
	public Vagas(String tipo, String altura, String largura,
			String comprimento, int quantidade, double valor) {
		super();
		this.tipo = tipo;
		this.altura = altura;
		this.largura = largura;
		this.comprimento = comprimento;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAltura() {
		return altura;
	}
	public void setAltura(String altura) {
		this.altura = altura;
	}
	public String getLargura() {
		return largura;
	}
	public void setLargura(String largura) {
		this.largura = largura;
	}
	public String getComprimento() {
		return comprimento;
	}
	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
