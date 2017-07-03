package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.BairroBean;
import bean.CidadeBean;
import bean.Coordenadas;
import bean.EnderecoBean;
import bean.EstacionamentoBean;
import bean.StatusBean;
import db.DB;

public class PesquisaDAO extends DB {
	
	private static final String LISTAR_ESTACIONAMENTOS_POR_TIPO = " SELECT e.id, s.id as id_status, s.nome as status, e.nome_fantasia,  "
			+ " end.nome_logradouro, "
			+ " end.numero, "
			+ " b.id as id_bairro, "
			+ " b.nome AS bairro, "
			+ " c.id AS id_cidade, "
			+ " c.nome AS cidade, "
			+ " end.latitude, "
			+ " end.longitude "
			+ " FROM "
			+ " estacionamento e, "
			+ " endereco end, "
			+ " bairro b, "
			+ " cidade c, "
			+ " status s, "
			+ " tipo_vaga tv "
			+ " WHERE "
			+ " e.id_endereco = end.id "
			+ " AND b.id = end.id_bairro "
			+ " AND c.id = end.id_cidade "
			+ " AND s.id = e.id_status " 
			+ " AND tv.id_estacionamento = e.id "
			+ " AND s.nome = 'ATIVO'"
			+ " AND upper(tv.nome) like ";
	
	private static final String LISTAR_ESTACIONAMENTOS_POR_NOME = " SELECT e.id, s.id as id_status, s.nome as status, e.nome_fantasia, "
			+ " end.nome_logradouro, "
			+ " end.numero, "
			+ " b.id as id_bairro, "
			+ " b.nome AS bairro, "
			+ " c.id AS id_cidade, "
			+ " c.nome AS cidade, "
			+ " end.latitude, "
			+ " end.longitude "
			+ " FROM "
			+ " estacionamento e, "
			+ " endereco end, "
			+ " bairro b, "
			+ " cidade c, "
			+ " status s "
			+ " WHERE "
			+ " e.id_endereco = end.id "
			+ " AND b.id = end.id_bairro "
			+ " AND c.id = end.id_cidade "
			+ " AND s.id = e.id_status " 
			+ " AND s.nome = 'ATIVO'"
			+ " AND upper(e.nome_fantasia) like ";
	
	
	
	public List<EstacionamentoBean> listaEstacionamentosPorTipo(String tipo) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<EstacionamentoBean> listaEstacionamentoBean =	null;

		try {

			conn	=	getMyqslConnection();
			
			if (tipo != null)
				tipo = tipo.toUpperCase();
			
			String sql = LISTAR_ESTACIONAMENTOS_POR_TIPO + "'%" + tipo + "%'";
			
			pstmt	=	conn.prepareStatement(sql);
			rs		=	pstmt.executeQuery();
			listaEstacionamentoBean 		=	new ArrayList<EstacionamentoBean>();

			while(rs.next()) {
				criaObjEstacionamento(conn, rs, listaEstacionamentoBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return listaEstacionamentoBean;
	}
	
	public List<EstacionamentoBean> listaEstacionamentosPorNome(String nome) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<EstacionamentoBean> listaEstacionamentoBean =	null;

		try {

			conn	=	getMyqslConnection();
			
			if (nome != null)
				nome = nome.toUpperCase();
			
			String sql = LISTAR_ESTACIONAMENTOS_POR_NOME + "'%" + nome + "%'";
			
			pstmt	=	conn.prepareStatement(sql);
			rs		=	pstmt.executeQuery();
			listaEstacionamentoBean 		=	new ArrayList<EstacionamentoBean>();

			while(rs.next()) {
				criaObjEstacionamento(conn, rs, listaEstacionamentoBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return listaEstacionamentoBean;
	}

	private void criaObjEstacionamento(Connection conn, ResultSet rs, List<EstacionamentoBean> listaEstacionamentoBean) throws SQLException {
		
		EstacionamentoBean estacionamentoBean		=	new EstacionamentoBean();
		estacionamentoBean.setId(rs.getInt("ID"));
		estacionamentoBean.setNomeFantasia(rs.getString("NOME_FANTASIA"));
		
		StatusBean statusBean = new StatusBean();
		statusBean.setId(rs.getInt("ID_STATUS"));
		statusBean.setNome(rs.getString("STATUS"));
		estacionamentoBean.setStatusBean(statusBean);
		
		
		EnderecoBean end = new EnderecoBean();
		end.setNomeLogradouro(rs.getString("NOME_LOGRADOURO"));
		end.setNumero(rs.getInt("NUMERO"));
		end.setBairroBean(new BairroBean(rs.getInt("ID_BAIRRO"), rs.getString("BAIRRO")));
		end.setCidadeBean(new CidadeBean(rs.getInt("ID_CIDADE"), rs.getString("CIDADE")));
		end.setCoordenadas(new Coordenadas(rs.getString("LATITUDE"), rs.getString("LONGITUDE")));
		estacionamentoBean.setEnderecoBean(end);
		
		estacionamentoBean.setTiposPagamentos(new TipoPagamentoDAO().buscaTiposPorEstacionamento(rs.getInt("ID")));
		
		estacionamentoBean.setTiposVaga(new VagasDAO().listaInformacoes(rs.getInt("ID")));
		
		PreparedStatement pstmt2	=	conn.prepareStatement(" select avaliacao as pontuacao, qtd_avaliacao from estacionamento where id = ? ");
		pstmt2.setInt(1, estacionamentoBean.getId());
		ResultSet rs2				=	pstmt2.executeQuery();
		
		while(rs2.next()){
			estacionamentoBean.setPontuacao(rs2.getInt("pontuacao"), rs2.getInt("qtd_avaliacao"));
		}
		
		listaEstacionamentoBean.add(estacionamentoBean);
	}
	
}
