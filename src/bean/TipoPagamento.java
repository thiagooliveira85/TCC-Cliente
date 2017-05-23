package bean;

public enum TipoPagamento {
	
	DINHEIRO(1, "img/dinheiro.png"),
	VISA	(2, "img/visa.png"),
	MASTER	(3, "img/master.png"),
	AMEX	(4, "img/amex.png");
	
	private String caminhoImagem;
	private Integer id;

	TipoPagamento(Integer id, String caminhoImagem){
		this.setId(id);
		this.setCaminhoImagem(caminhoImagem);
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public static TipoPagamento getTipoPagamentoPorID(Integer id){
		for (TipoPagamento t : TipoPagamento.values()) {
			if (t.getId() == id)
				return t;
		}
		return null;
	}

}
