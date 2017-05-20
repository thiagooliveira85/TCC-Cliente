package entity;

import java.io.Serializable;

//MEAN (MONGO, EXPRESS , ANGULARJS , NodeJS) 

public class Endereco implements Serializable{

	private Integer idEndereco;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	
	private Double latitude;
	private Double longitude;
	
	private Empresa empresa;
	
	
	public Endereco() {
		// TODO Auto-generated constructor stub
	}


	public Endereco(Integer idEndereco, String logradouro, String bairro,
			String cidade, String uf, Double latitude, Double longitude,
			Empresa empresa) {
		super();
		this.idEndereco = idEndereco;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.latitude = latitude;
		this.longitude = longitude;
		this.empresa = empresa;
	}


	@Override
	public String toString() {
		return "Endereco [idEndereco=" + idEndereco + ", logradouro="
				+ logradouro + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", uf=" + uf + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}


	public Integer getIdEndereco() {
		return idEndereco;
	}


	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getUf() {
		return uf;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
}
