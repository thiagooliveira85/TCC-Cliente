package manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import bean.Coordenadas;
import persistence.EmpresaDao;
//import persistence.EmpresaDao;
import entity.Empresa;
import entity.Endereco;

@ManagedBean(name="mb")
@SessionScoped
public class ManagerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Endereco endereco;
	private Empresa empresa;
	
	private List<Empresa> listaEmpresa;
	
	private String centerGeoMap;
	private MapModel geoModel;
	
	private MapModel simpleModel;
	private Marker marker;
	
	
	@PostConstruct
	public void init() {
		endereco = new Endereco();
		empresa = new Empresa();
		geoModel = new DefaultMapModel();
		listaEmpresa = new EmpresaDao().findAll();
		carregarEnderecosMapa();
	}
	
	// metodo para marcar vários pontos no mapa - com base nas empresas do banco
	public void carregarEnderecosMapa(){
		simpleModel = new DefaultMapModel();		
		
		for(Empresa e : listaEmpresa){
			simpleModel.addOverlay(new Marker(new LatLng(e.getEndereco().getLatitude(), e.getEndereco().getLongitude()),e.getRazaoSocial()));

		}
	}

	public void cadastrar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String msg="";
		try {
			empresa.setEndereco(endereco);
			endereco.setEmpresa(empresa);
			 // grava no banco
			new EmpresaDao().create(empresa);
			 msg="Cadastro Realizado ! ";
			 init();
		} catch (Exception ex) {
			msg="Error no Cadastro .. : " + ex.getMessage();
		}finally{
			fc.addMessage("form1", new FacesMessage(msg));
		}
	}
	
	public void onMarkerSelect(OverlaySelectEvent event){
		marker = (Marker) event.getOverlay();
		/*FacesContext.getCurrentInstance().addMessage("form1", 
					new FacesMessage("Endereco Marcado: "+ marker.getTitle() + "-" 
							+ marker.getLatlng()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("teste", marker.getTitle());*/
	}
	
	public void setLocalizacaoAtual(){
		
		LatLng coord = null;
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String latitude 		= requestParamMap.get("lat");	
        String longitude 		= requestParamMap.get("long");
        Coordenadas coordenadas = null; 
        
        try {
        	coordenadas = new Coordenadas(latitude, longitude);
		} catch (Exception e) {
			//coordenadas = new Coordenadas("-22.911189", "-43.29720140000001");
			coordenadas = new Coordenadas("-22.9723749", "-43.1880018");
		}

        coord = coordenadas.getLatLng();
        centerGeoMap = coordenadas.toString();
		simpleModel.addOverlay(new Marker(coord, "Você está aqui!", "", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
	}

	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}

	public MapModel getGeoModel() {
		return geoModel;
	}

	public void setGeoModel(MapModel geoModel) {
		this.geoModel = geoModel;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}
	
	// recebe os dados retornados pelo WebServices ...
	public void onGeoCode(GeocodeEvent event) {

		System.out.println(endereco);

		FacesContext.getCurrentInstance().addMessage("form1",
				new FacesMessage("Endereco Marcado: " + endereco));
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("teste", "hahauahauhauhauh");

		List<GeocodeResult> resultado = event.getResults();

		if (resultado != null && !resultado.isEmpty()) {
			LatLng center = resultado.get(0).getLatLng();
			// passo a latitude e longitude, para a string , que posiciona o
			// MAPA na view
			centerGeoMap = center.getLat() + "," + center.getLng();
			// para gravar no banco...
			endereco.setLatitude(center.getLat());
			endereco.setLongitude(center.getLng());
			GeocodeResult result = resultado.get(0);
			System.out.println(resultado.get(0).getAddress());
			geoModel.addOverlay(new Marker(result.getLatLng(), result
					.getAddress()));
		}

	}
	
}
