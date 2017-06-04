package manager;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RateEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import util.Util;
import bean.Coordenadas;
import bean.EstacionamentoBean;
import business.PesquisaBusiness;
import dao.EstacionamentoDAO;
import dao.PesquisaDAO;

@ManagedBean(name="mb")
@SessionScoped
public class ManagerBean implements Serializable{
	
	private static final String ENDERECO_MARRETA = "rua camarista meier 831 engenho de dentro";

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
	
	private String valorPesquisa;
	private String tipoPesquisa;
	private String linkLogotipo;
	private int zoom;
	
	private Coordenadas coordInicial;
	
	
	@PostConstruct
	public void init() {
		setLinkLogotipo();
		setZoom(16);
		marretaPosicaoAtualCasoNaoResgate();
	}

	public void setLocalizacaoAtual(){
		
		carregarEnderecosMapa();
		
		LatLng coord 						= null;
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String latitude 					= requestParamMap.get("lat");	
        String longitude 					= requestParamMap.get("long");
        Coordenadas coordenadas 			= null; 
        
        try {
        	coordInicial = new Coordenadas(latitude, longitude);
        	centerGeoMap = coordInicial.toString();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			//coordenadas = new Coordenadas("-22.9723749", "-43.1880018");
			//coordenadas = new Coordenadas("35.7102219","139.7315379");
		}

        coord = coordInicial.getLatLng();
		Marker atualMarker = new Marker(coord, "Você está aqui!", estacionamento, "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png");
		atualMarker.setClickable(false);
		simpleModel.addOverlay(atualMarker);
	}
	
	public void atualizaInformacoesEmTempoReal(){
		setLocalizacaoAtual();
	}
	
	/**
	 * metodo para carregar os estacionamentos no mapa
	 */
	private void carregarEnderecosMapa(){
		
		geoModel			= new DefaultMapModel();
		simpleModel 		= new DefaultMapModel();		
		
		// LISTA TODOS OS ESTACIONAMENTOS ATIVOS CADASTRADOS
		lstEstacionamento 	= new EstacionamentoDAO().listaTodos();
		
		for(EstacionamentoBean estacionamento : lstEstacionamento){
			LatLng latLng = estacionamento.getEnderecoBean().getCoordenadas().getLatLng();
			simpleModel.addOverlay(new Marker(latLng, estacionamento.getNomeFantasia(), estacionamento));
		}
	}
	
	/**
	 * metodo para carregar os estacionamentos por tipo de vaga
	 */
	private void carregarEnderecosPorTipoVaga(String tipoVaga){
		
		geoModel			= new DefaultMapModel();
		simpleModel 		= new DefaultMapModel();		
		
		lstEstacionamento 	= new PesquisaDAO().listaEstacionamentosPorTipo(tipoVaga);
		
		String icon = new PesquisaBusiness().buscaIconePorTipo(tipoVaga);
		
		for(EstacionamentoBean estacionamento : lstEstacionamento){
			LatLng latLng = estacionamento.getEnderecoBean().getCoordenadas().getLatLng();
			simpleModel.addOverlay(new Marker(latLng, estacionamento.getNomeFantasia(), estacionamento, icon));
		}
	}

	public void onMarkerSelect(OverlaySelectEvent event){
		marker = (Marker) event.getOverlay();
	}
	
	public void onrate(RateEvent rateEvent) {
		
		/*Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String strId 		= params.get("ident");*/
        
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Obrigado pela avaliação!");		
		FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void buscaInformacoesMapa() {
		try {
			
			if (tipoPesquisa.equals("tipoVaga")){
				carregarEnderecosPorTipoVaga(valorPesquisa);
			}else{
				Coordenadas coord = new PesquisaBusiness().buscaLocalizacaoPorTipo(tipoPesquisa, valorPesquisa);
				centerGeoMap = coord != null ? coord.toString() : centerGeoMap;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			valorPesquisa = null;
			tipoPesquisa = null;
		}
	}
	
	/**
	 * Seta o link do logotipo na página com o IP da máquina
	 */
	public void setLinkLogotipo() {
		String hostAddress = "";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			linkLogotipo = "http://" + hostAddress + ":9091/EstacionamentoOnlineEntradaCliente";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void onStateChange(StateChangeEvent event) {
        zoom = event.getZoomLevel();
        centerGeoMap = event.getCenter().getLat() + "," + event.getCenter().getLng();
    }
      
    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
    }
    
    private void marretaPosicaoAtualCasoNaoResgate() {
		try {
			coordInicial = new PesquisaBusiness().buscaLocalizacaoPorTipo("endereco", ENDERECO_MARRETA);
			centerGeoMap = coordInicial.toString();
		}catch(IOException e){
			e.printStackTrace();	
		}
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

	public String getValorPesquisa() {
		return valorPesquisa;
	}

	public void setValorPesquisa(String valorPesquisa) {
		valorPesquisa = Util.retiraAcentos(valorPesquisa);
		this.valorPesquisa = valorPesquisa;
	}

	public String getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public String getLinkLogotipo() {
		return linkLogotipo;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public Coordenadas getCoordInicial() {
		return coordInicial;
	}

	public void setCoordInicial(Coordenadas coordInicial) {
		this.coordInicial = coordInicial;
	}
}
