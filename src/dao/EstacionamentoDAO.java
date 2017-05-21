package dao;

import java.util.ArrayList;
import java.util.List;

import bean.Coordenadas;
import bean.EnderecoBean;
import bean.EstacionamentoBean;
import bean.TipoPagamento;
import bean.Vagas;

public class EstacionamentoDAO {

	public List<EstacionamentoBean> buscaTodos() {
		
		List<EstacionamentoBean> lst = new ArrayList<EstacionamentoBean>();
		
		EstacionamentoBean e = new EstacionamentoBean();
		EnderecoBean end = new EnderecoBean();
		end.setCoordenadas(new Coordenadas("-22.911189", "-43.29720140000001"));
		
		e.setEnderecoBean(end);
		e.setNomeFantasia("Central Park");
		
		List<Vagas> vagas = new ArrayList<>();		
		vagas.add(new Vagas("Moto", "", "", "", 20, 4.0));
		vagas.add(new Vagas("Passeio", "", "", "", 10, 8.0));
		vagas.add(new Vagas("Pickup", "", "", "", 3, 15.0));
		e.setVagas(vagas);
		
		List<TipoPagamento> tiposPagamentos = new ArrayList<>();
		
		tiposPagamentos.add(TipoPagamento.DINHEIRO);
		tiposPagamentos.add(TipoPagamento.VISA);
		e.setTiposPagamentos(tiposPagamentos);
		
		EstacionamentoBean e2 = new EstacionamentoBean();
		EnderecoBean end2 = new EnderecoBean();
		end2.setCoordenadas(new Coordenadas("-22.9083076","-43.2983308"));
		
		e2.setEnderecoBean(end2);
		e2.setNomeFantasia("Catulo Park");
				
		List<Vagas> vagas2 = new ArrayList<>();		
		vagas2.add(new Vagas("Caminhão", "", "", "", 1, 20.0));
		e2.setVagas(vagas2);
		
		List<TipoPagamento> tiposPagamentos2 = new ArrayList<>();
		
		tiposPagamentos2.add(TipoPagamento.AMEX);
		e2.setTiposPagamentos(tiposPagamentos2);
		
		lst.add(e);
		lst.add(e2);
		
		return lst;
	}

}
