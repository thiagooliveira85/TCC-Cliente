package bean;

public enum TipoPagamento {
	
	DINHEIRO ("img/dinheiro.png"),
	MASTER("img/master.png"),
	VISA("img/visa.png"),
	AMEX("img/amex.png");
	
	private String caminhoImagem;

	TipoPagamento(String caminhoImagem){
		this.setCaminhoImagem(caminhoImagem);
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

}
