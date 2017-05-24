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

}
