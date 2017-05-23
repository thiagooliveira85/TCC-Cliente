package bean;

public class Vagas {
	
	private TipoVaga tipo;
	private String altura;
	private String largura;
	private String comprimento;
	private String codigo;
	private boolean ocupada;
	
	public Vagas(TipoVaga tipo, String altura, String largura,
			String comprimento, String codigo, boolean ocupada) {
		super();
		this.tipo = tipo;
		this.altura = altura;
		this.largura = largura;
		this.comprimento = comprimento;
		this.codigo = codigo;
		this.ocupada = ocupada;
	}
	public TipoVaga getTipo() {
		return tipo;
	}
	public void setTipo(TipoVaga tipo) {
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public boolean isOcupada() {
		return ocupada;
	}
	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
}
