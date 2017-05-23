package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.TipoVaga;
import bean.Vagas;
import db.DB;

public class VagasDAO extends DB{

	/*private static final String LISTAR_VAGAS_POR_ESTACIONAMENTO = " select v.id, tv.id as id_tipo, tv.nome as tipo, v.codigo, v.largura, v.altura, v.status, v.comprimento, tv.preco from vaga v, tipo_vaga tv " +
			" where v.id_tipo_vaga = tv.id " +
			" and v.id_estacionamento = ? ";*/
	
	private static final String LISTAR_INFORMACOES_POR_TIPO = " select tv.nome as tipo, tv.preco, count(tv.nome) as quantidade from vaga v, tipo_vaga tv " + 
			" where v.id_tipo_vaga = tv.id " +
			" and v.id_estacionamento = ?  " +
			" and v.status = 'D' " +
			" group by tv.nome, tv.preco ";

	
	/*public List<Vagas> listaVagasPorEstacionamento(int idEstacionamento) {
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<Vagas> lista 			=	null;
		
		try {
			conn	=	getMyqslConnection();
			pstmt	=	conn.prepareStatement(LISTAR_VAGAS_POR_ESTACIONAMENTO);
			pstmt.setInt(1, idEstacionamento);
			rs		=	pstmt.executeQuery();

			lista = new ArrayList<Vagas>();
			
			while(rs.next()) {
				int idTipo 			= rs.getInt("ID_TIPO");
				String nomeTipo 	= rs.getString("TIPO");
				String altura 		= rs.getInt("ALTURA") + "";
				String largura 		= rs.getInt("LARGURA") + "";
				String comprimento 	= rs.getInt("COMPRIMENTO") + "";
				String codigo 		= rs.getString("CODIGO");
				boolean ocupada 	= rs.getString("STATUS").equalsIgnoreCase("D") ? true : false;
				int quantidade 		= 100;
				double valor 		= rs.getDouble("PRECO");
				
				TipoVaga tipoVaga = new TipoVaga(idTipo, valor, nomeTipo);
				
				lista.add(new Vagas(tipoVaga, altura, largura, comprimento, codigo, ocupada));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return lista;	
	}*/

	public List<TipoVaga> listaInformacoes(int idEstacionamento) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<TipoVaga> lista 			=	null;
		
		try {
			conn	=	getMyqslConnection();
			pstmt	=	conn.prepareStatement(LISTAR_INFORMACOES_POR_TIPO);
			pstmt.setInt(1, idEstacionamento);
			rs		=	pstmt.executeQuery();

			lista = new ArrayList<TipoVaga>();
			while(rs.next())
				lista.add(new TipoVaga(rs.getDouble("PRECO"), rs.getString("TIPO"), rs.getInt("QUANTIDADE")));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return lista;	
	}

}