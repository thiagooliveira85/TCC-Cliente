package bean;

import java.io.Serializable;
import java.util.List;

import dao.EstacionamentoDAO;

public class EstacionamentoBean implements Serializable {
	
	private static final long serialVersionUID = 6750485015161261228L;

	private int id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoMunicipal;
	private String inscricaoEstadual;
	private StatusBean statusBean;
	private EnderecoBean enderecoBean;
	private Integer avaliacao;
	private int quantidadeFuncionarios;
	
	private List<TipoPagamento> tiposPagamentos;
	private List<TipoVaga> tiposVaga;
	
	private boolean avaliou;
	
	private Integer pontuacao;
	
	public int getQuantidadeFuncionarios() {
		return quantidadeFuncionarios;
	}
	public void setQuantidadeFuncionarios(int quantidadeFuncionarios) {
		this.quantidadeFuncionarios = quantidadeFuncionarios;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}
	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}
	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}
	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
	}
	public StatusBean getStatusBean() {
		return statusBean;
	}
	public void setStatusBean(StatusBean statusBean) {
		this.statusBean = statusBean;
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public List<TipoPagamento> getTiposPagamentos() {
		return tiposPagamentos;
	}
	public void setTiposPagamentos(List<TipoPagamento> tiposPagamentos) {
		this.tiposPagamentos = tiposPagamentos;
	}
	public Integer getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
		boolean avaliado = new EstacionamentoDAO().avaliarEstacionamento(this);
		if (avaliado)
			setAvaliou(true);
		else
			setAvaliou(false);
	}
	public boolean isAvaliou() {
		return avaliou;
	}
	public void setAvaliou(boolean avaliou) {
		this.avaliou = avaliou;
	}
	public List<TipoVaga> getTiposVaga() {
		return tiposVaga;
	}
	public void setTiposVaga(List<TipoVaga> tiposVaga) {
		this.tiposVaga = tiposVaga;
	}
	public Integer getPontuacao() {
		return pontuacao;
	}
	
	/**
	 * Seta a m�dia de avalia��es com as quantidades
	 * @param pontuacao
	 * @param qtdAvaliacao
	 */
	public void setPontuacao(Integer pontuacao, Integer qtdAvaliacao) {
		if (qtdAvaliacao > 0)
			this.pontuacao = pontuacao / qtdAvaliacao;
	}
}