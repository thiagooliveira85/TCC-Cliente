package manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RateEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import dao.EstacionamentoDAO;
import bean.Coordenadas;
import bean.EstacionamentoBean;

@ManagedBean(name="mb")
@SessionScoped
public class ManagerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EstacionamentoBean estacionamento;
	
	private List<EstacionamentoBean> lstEstacionamento;
	
	private String centerGeoMap;
	private MapModel geoModel;
	
	private MapModel simpleModel;
	private Marker marker;
	
	
	@PostConstruct
	public void init() {
		geoModel 			= new DefaultMapModel();
		lstEstacionamento 	= new EstacionamentoDAO().buscaTodos();
		carregarEnderecosMapa();
	}
	
	/**
	 * metodo para marcar vários pontos no mapa - com base nas empresas do banco
	 */
	private void carregarEnderecosMapa(){
		simpleModel = new DefaultMapModel();		
		for(EstacionamentoBean estacionamento : lstEstacionamento){
			LatLng latLng = estacionamento.getEnderecoBean().getCoordenadas().getLatLng();
			simpleModel.addOverlay(new Marker(latLng, estacionamento.getNomeFantasia(), estacionamento));
		}
	}

	public void onMarkerSelect(OverlaySelectEvent event){
		marker = (Marker) event.getOverlay();
		FacesContext.getCurrentInstance().addMessage("form1", 
					new FacesMessage("Endereco Marcado: "+ marker.getTitle() + "-" 
							+ marker.getLatlng()));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("teste", marker.getTitle());
	}
	
	public void setLocalizacaoAtual(){
		
		LatLng coord 						= null;
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String latitude 					= requestParamMap.get("lat");	
        String longitude 					= requestParamMap.get("long");
        Coordenadas coordenadas 			= null; 
        
        try {
        	coordenadas = new Coordenadas(latitude, longitude);
		} catch (Exception e) {
			coordenadas = new Coordenadas("-22.9094818","-43.2969057");
			//coordenadas = new Coordenadas("-22.9723749", "-43.1880018");
		}

        coord = coordenadas.getLatLng();
        centerGeoMap = coordenadas.toString();
		//simpleModel.addOverlay(new Marker(coord, "Você está aqui!", "", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
		simpleModel.addOverlay(new Marker(coord, "Você está aqui!", estacionamento));
	}
	
	public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Obrigado pela avaliação!");
        FacesContext.getCurrentInstance().addMessage(null, message);
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

	public List<EstacionamentoBean> getLstEstacionamento() {
		return lstEstacionamento;
	}

	public void setLstEstacionamento(List<EstacionamentoBean> lstEstacionamento) {
		this.lstEstacionamento = lstEstacionamento;
	}
	
}
