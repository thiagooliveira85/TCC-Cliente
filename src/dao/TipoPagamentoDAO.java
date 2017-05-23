package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TipoPagamento;
import db.DB;

public class TipoPagamentoDAO extends DB {
	
	private static final String LISTAR_TIPOS_POR_ESTACIONAMENTO = "select id_tipo_pagamento from  estacionamento_tp_pagamento where id_estacionamento = ?";

	public List<TipoPagamento> buscaTiposPorEstacionamento(int id) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<TipoPagamento> lista	=	null;

		try {

			conn	=	getMyqslConnection();
			pstmt	=	conn.prepareStatement(LISTAR_TIPOS_POR_ESTACIONAMENTO);
			pstmt.setInt(1, id);
			rs		=	pstmt.executeQuery();
			
			lista = new ArrayList<>();
			while (rs.next())
				lista.add(TipoPagamento.getTipoPagamentoPorID(rs.getInt("ID_TIPO_PAGAMENTO")));
			
			return lista;
			
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			close(conn, pstmt, rs);
		}
		return null;
	}

}
