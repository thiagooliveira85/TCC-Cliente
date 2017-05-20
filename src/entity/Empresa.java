package entity;

import java.io.Serializable;

public class Empresa implements Serializable{
	
	private Integer idEmpresa;
	private String razaoSocial;
	
	private Endereco endereco;
	

	public Empresa() {
		// TODO Auto-generated constructor stub
	}


	public Empresa(Integer idEmpresa, String razaoSocial, Endereco endereco) {
		super();
		this.idEmpresa = idEmpresa;
		this.razaoSocial = razaoSocial;
		this.endereco = endereco;
	}


	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", razaoSocial="
				+ razaoSocial + ", endereco=" + endereco + "]";
	}


	public Integer getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	public String getRazaoSocial() {
		return razaoSocial;
	}


	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
	
}
