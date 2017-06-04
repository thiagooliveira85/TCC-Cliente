package business;

import java.io.IOException;
import java.util.List;

import dao.EstacionamentoDAO;
import dao.PesquisaDAO;
import bean.Coordenadas;
import bean.EstacionamentoBean;

public class PesquisaBusiness {

	public Coordenadas buscaLocalizacaoPorTipo(String tipoPesquisa, String valorPesquisa) throws IOException {
		
		Coordenadas coord = null;
		
		if (tipoPesquisa.equals("endereco")){
			coord = new GoogleAPI().buscaCoordenadasPorEndereco(valorPesquisa);
		}else if (tipoPesquisa.equals("estacionamento")){
			coord = new EstacionamentoDAO().buscaCoordenadasPorEstacionamento(valorPesquisa);
		}else if (tipoPesquisa.equals("tipoVaga")){
			List<EstacionamentoBean> lst = new PesquisaDAO().listaEstacionamentosPorTipo(valorPesquisa);
			
			// Por enquanto buscando o primeiro da lista. TO DO
			coord = lst.get(0).getEnderecoBean().getCoordenadas();
		}
		
		return coord;
	}
	
	// ISSO AQUI TÁ UM LIXO... MAS POR ENQUANTO SÓ PRA BRINCAR
	public String buscaIconePorTipo(String tipoVaga) {
		
		if (tipoVaga.equalsIgnoreCase("MOTO")){
			return "http://icon-icons.com/icons2/577/PNG/64/TouringMotorcycle_Green_icon-icons.com_54907.png";
		}else if (tipoVaga.equalsIgnoreCase("CARRO")){
			return "http://icon-icons.com/icons2/577/PNG/64/Cabriolet_Red_icon-icons.com_54906.png";
		}else if (tipoVaga.equalsIgnoreCase("CAMINHAO")){
			return "http://icon-icons.com/icons2/577/PNG/64/Truck_Yellow_icon-icons.com_54884.png";
		}else if (tipoVaga.equalsIgnoreCase("PICKUP")){
			return "http://icon-icons.com/icons2/577/PNG/64/TowTruck_Yellow_icon-icons.com_54896.png";
		}
		
		return "";
		 //; //;//"http://icon-icons.com/icons2/577/PNG/48/ExecutiveCar_Black_icon-icons.com_54904.png"; //
	}

}
