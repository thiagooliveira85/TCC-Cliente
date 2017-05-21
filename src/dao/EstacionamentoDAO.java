package dao;

import java.util.ArrayList;
import java.util.List;

import bean.Coordenadas;
import bean.EnderecoBean;
import bean.EstacionamentoBean;
import bean.TipoPagamento;

public class EstacionamentoDAO {

	public List<EstacionamentoBean> buscaTodos() {
		
		List<EstacionamentoBean> lst = new ArrayList<EstacionamentoBean>();
		
		EstacionamentoBean e = new EstacionamentoBean();
		EnderecoBean end = new EnderecoBean();
		end.setCoordenadas(new Coordenadas("-22.911189", "-43.29720140000001"));
		
		e.setEnderecoBean(end);
		e.setNomeFantasia("Central Park");
		e.setQuantidadeVagas(6);
		
		List<TipoPagamento> tiposPagamentos = new ArrayList<>();
		
		tiposPagamentos.add(TipoPagamento.DINHEIRO);
		tiposPagamentos.add(TipoPagamento.VISA);
		e.setTiposPagamentos(tiposPagamentos);
		
		lst.add(e);
		
		return lst;
	}

}
