package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

	public static final String DRIVER_MYSQL				=	"com.mysql.jdbc.Driver";
	public static final String CONNECTION_STRING_MYSQL	=	"jdbc:mysql://localhost:3306/VagasOnline";
	public static final String USER_BANCO_MYSQL			=	"root";	
	public static final String PASS_BANCO_MYSQL			=	"root";
	
	public static Connection getMyqslConnection() {
		try {
			Class.forName(DRIVER_MYSQL);
			return DriverManager.getConnection(CONNECTION_STRING_MYSQL, USER_BANCO_MYSQL, PASS_BANCO_MYSQL);
		} catch (Exception e) {
			System.out.println("Nao foi possivel conectar a base de dados: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		
		try {
			if(rs!=null) {
				rs.close();
				rs=null;
			}
			if(pstmt!=null) {
				pstmt.close();
				pstmt=null;
			}
			if(conn!=null) {
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			System.out.println("Erro ao fechar os objetos de banco: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}