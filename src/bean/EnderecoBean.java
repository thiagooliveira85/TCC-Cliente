package bean;

import java.io.Serializable;

public class EnderecoBean implements Serializable{

	private static final long serialVersionUID = -7483888490913726022L;
	
	private int id;
	private TipoLogradouroBean tipoLogradouroBean;
	private String nomeLogradouro;
	private int numero;
	private String cep;
	private BairroBean bairroBean;
	private CidadeBean cidadeBean;
	private EstadoBean estadoBean;
	private PaisBean paisBean;
	
	private Coordenadas coordenadas;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TipoLogradouroBean getTipoLogradouroBean() {
		return tipoLogradouroBean;
	}
	public void setTipoLogradouroBean(TipoLogradouroBean tipoLogradouroBean) {
		this.tipoLogradouroBean = tipoLogradouroBean;
	}
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public BairroBean getBairroBean() {
		return bairroBean;
	}
	public void setBairroBean(BairroBean bairroBean) {
		this.bairroBean = bairroBean;
	}
	public CidadeBean getCidadeBean() {
		return cidadeBean;
	}
	public void setCidadeBean(CidadeBean cidadeBean) {
		this.cidadeBean = cidadeBean;
	}
	public EstadoBean getEstadoBean() {
		return estadoBean;
	}
	public void setEstadoBean(EstadoBean estadoBean) {
		this.estadoBean = estadoBean;
	}
	public PaisBean getPaisBean() {
		return paisBean;
	}
	public void setPaisBean(PaisBean paisBean) {
		this.paisBean = paisBean;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bairroBean == null) ? 0 : bairroBean.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result
				+ ((cidadeBean == null) ? 0 : cidadeBean.hashCode());
		result = prime * result
				+ ((estadoBean == null) ? 0 : estadoBean.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((nomeLogradouro == null) ? 0 : nomeLogradouro.hashCode());
		result = prime * result + numero;
		result = prime * result
				+ ((paisBean == null) ? 0 : paisBean.hashCode());
		result = prime
				* result
				+ ((tipoLogradouroBean == null) ? 0 : tipoLogradouroBean
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoBean other = (EnderecoBean) obj;
		if (bairroBean == null) {
			if (other.bairroBean != null)
				return false;
		} else if (!bairroBean.equals(other.bairroBean))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidadeBean == null) {
			if (other.cidadeBean != null)
				return false;
		} else if (!cidadeBean.equals(other.cidadeBean))
			return false;
		if (estadoBean == null) {
			if (other.estadoBean != null)
				return false;
		} else if (!estadoBean.equals(other.estadoBean))
			return false;
		if (id != other.id)
			return false;
		if (nomeLogradouro == null) {
			if (other.nomeLogradouro != null)
				return false;
		} else if (!nomeLogradouro.equals(other.nomeLogradouro))
			return false;
		if (numero != other.numero)
			return false;
		if (paisBean == null) {
			if (other.paisBean != null)
				return false;
		} else if (!paisBean.equals(other.paisBean))
			return false;
		if (tipoLogradouroBean == null) {
			if (other.tipoLogradouroBean != null)
				return false;
		} else if (!tipoLogradouroBean.equals(other.tipoLogradouroBean))
			return false;
		return true;
	}
	public Coordenadas getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	@Override
	public String toString() {
		if (tipoLogradouroBean == null || nomeLogradouro == null || bairroBean == null || cidadeBean == null)
			return "";
		return tipoLogradouroBean.getNome() + " " + nomeLogradouro+ " nº " + numero+ " - " + bairroBean.getNome()+ " - " + cidadeBean.getNome() + ".";
	}
}