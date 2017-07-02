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
import business.GoogleAPI;
import business.PesquisaBusiness;
import dao.EstacionamentoDAO;
import dao.PesquisaDAO;

@ManagedBean(name="mb")
@SessionScoped
public class ManagerBean implements Serializable{
	
	private static final String ENDERECO_MARRETA 	= "rua da assembleia 10 Centro Rio de Janeiro";
	private static final String ESTACIONAMENTO_ICON = "https://cdn4.iconfinder.com/data/icons/summer-and-holidays-9/90/447-64.png";
	private static final String POSICAO_ICON 		= "https://cdn4.iconfinder.com/data/icons/location-position-map-1-1/256/Location-1-1-48.png";
	
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
		
		Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String latitude 					= requestParamMap.get("lat");	
        String longitude 					= requestParamMap.get("long");
        
        try {
        	coordInicial = new Coordenadas(latitude, longitude);
        	centerGeoMap = coordInicial.toString();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			//marretaPosicaoAtualCasoNaoResgate();
			//coordenadas = new Coordenadas("-22.9041053", "-43.1774482");
			//centerGeoMap = coordInicial.toString();
			//coordenadas = new Coordenadas("35.7102219","139.7315379");
		}

		marcaPosicaoNoMapa(coordInicial, "Você está aqui!", estacionamento, POSICAO_ICON, false);
	}
	
	public void atualizaInformacoesEmTempoReal(){
		setLocalizacaoAtual();
	}
	
	public void buscaInformacoesMapa() {
		try {
			
			if (tipoPesquisa.equals("tipoVaga")){
				carregarEnderecosPorTipoVaga(valorPesquisa);
			
			}else if (tipoPesquisa.equals("estacionamento")){
				carregarEnderecosPorNomeEstacionamento(valorPesquisa);
			
			}else if (tipoPesquisa.equals("endereco")){
				Coordenadas coord = new GoogleAPI().buscaCoordenadasPorEndereco(valorPesquisa);
				centerGeoMap = coord != null ? coord.toString() : centerGeoMap;
				
				marcaPosicaoNoMapa(coord, "", null, null, false);
			}
			
		} catch(RuntimeException re){ 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Endereço não encontrado!", "");		
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			valorPesquisa = null;
			tipoPesquisa = null;
		}
	}
	
	public void onMarkerSelect(OverlaySelectEvent event){
		marker = (Marker) event.getOverlay();
	}
	
	public void onrate(RateEvent rateEvent) {
		
		/*Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String strId 		= params.get("ident");*/
        
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Obrigado pela avaliação!", "");		
		FacesContext.getCurrentInstance().addMessage(null, message);
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
    
    
    
    
    // METODOS PRIVADOS
    
    /**
     * Marca o ponto no mapa com as informações
     * 
     * @param coordenadas Lat e long do ponto exato no mapa
     * @param descricao nome do title ao passar o cursos no ponto
     * @param estacionamento Objeto com as informações do estacionamento
     * @param posicaoIcon Link com o ícone da marcação
     * @param clickable Se esta marcação tm evento de clique ou não
     */
    private void marcaPosicaoNoMapa(Coordenadas coordenadas, String descricao, EstacionamentoBean estacionamento, String posicaoIcon, boolean clickable) {
		Marker marker = new Marker(coordenadas.getLatLng(), descricao, estacionamento, posicaoIcon);
		marker.setClickable(clickable);
		simpleModel.addOverlay(marker);
	}
    
    /**
	 * metodo para carregar os estacionamentos no mapa
	 */
	private void carregarEnderecosMapa(){
		
		geoModel			= new DefaultMapModel();
		simpleModel 		= new DefaultMapModel();		
		
		// LISTA TODOS OS ESTACIONAMENTOS ATIVOS CADASTRADOS
		lstEstacionamento 	= new EstacionamentoDAO().listaTodos();
		
		for(EstacionamentoBean estacionamento : lstEstacionamento)
			marcaPosicaoNoMapa(estacionamento.getEnderecoBean().getCoordenadas(), estacionamento.getNomeFantasia(), estacionamento, ESTACIONAMENTO_ICON, true);
	}
    
    /**
	 * metodo para carregar os estacionamentos por tipo de vaga
	 */
	private void carregarEnderecosPorTipoVaga(String tipoVaga){
		
		geoModel			= new DefaultMapModel();
		simpleModel 		= new DefaultMapModel();		
		
		lstEstacionamento 	= new PesquisaDAO().listaEstacionamentosPorTipo(tipoVaga);
		
		if (lstEstacionamento != null && !lstEstacionamento.isEmpty()){
			
			String icon = new PesquisaBusiness().buscaIconePorTipo(tipoVaga);
			for(EstacionamentoBean estacionamento : lstEstacionamento)
				marcaPosicaoNoMapa(estacionamento.getEnderecoBean().getCoordenadas(), estacionamento.getNomeFantasia(), estacionamento, icon, true);
			
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Tipo de vaga não encontrado!", "");		
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	/**
	 * metodo para carregar os estacionamentos por nome
	 */
	private void carregarEnderecosPorNomeEstacionamento(String nome){
		
		geoModel			= new DefaultMapModel();
		simpleModel 		= new DefaultMapModel();		
		
		lstEstacionamento 	= new PesquisaDAO().listaEstacionamentosPorNome(nome);
		
		if (lstEstacionamento != null && !lstEstacionamento.isEmpty()){
			
			for(EstacionamentoBean estacionamento : lstEstacionamento){
				//http://icon-icons.com/icons2/933/PNG/48/parking-sign_icon-icons.com_72642.png    http://icon-icons.com/icons2/409/PNG/48/garage_40803.png
				marcaPosicaoNoMapa(estacionamento.getEnderecoBean().getCoordenadas(), estacionamento.getNomeFantasia(), estacionamento, ESTACIONAMENTO_ICON, true);
				Coordenadas coord = estacionamento.getEnderecoBean().getCoordenadas();
				centerGeoMap = coord != null ? coord.toString() : centerGeoMap;
			}
			
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Nome não encontrado!", "");		
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
    
    private void marretaPosicaoAtualCasoNaoResgate() {
		try {
			coordInicial = new PesquisaBusiness().buscaLocalizacaoPorTipo("endereco", ENDERECO_MARRETA);
			centerGeoMap = coordInicial.toString();
		}catch(IOException e){
			e.printStackTrace();	
		}
	}

    
    
    // GETTERS AND SETTERS
    
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
