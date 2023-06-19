package FabricaConexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* 
 * Para efetuar a conexão, é necessário criar uma base de dados no MySQL com o nome de
 * TrabalhoA3.
 * Deve ser criada a fábrica de conexão com o banco de dados. Nesse exemplo, está
 * sendo utilizado o banco de dados MySQL. É importante perceber que nessa classe além do
 * método de conexão com o banco, também estão sobrecarregados três métodos para fechar a
 * conexão: o primeiro deles fecha apenas a Connection, o segundo fecha o PreparedStatement e
 * chama o primeiro para fechar a Connection e o terceiro fecha o ResultSet e chama o segundo
 * para o fechamento do PreparedStatement e a Connection.
 */

public class FabricaConexao {

	//Dados para a conexão com o banco
		private static final String USUARIO = "root";
		private static final String SENHA = "keure123";
		private static final String DATABASE = "agenda";
		private static final String DRIVER_CONEXAO =
		"com.mysql.cj.jdbc.Driver";
		private static final String STR_CONEXAO =
		"jdbc:mysql://127.0.0.1:3306/";

		public static Connection getConexao() throws SQLException,
		ClassNotFoundException {

			Connection conn = null;
			try {
				Class.forName(DRIVER_CONEXAO);
				conn = DriverManager.getConnection(STR_CONEXAO
				+ DATABASE, USUARIO, SENHA);

				return conn;

			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException(
						"Driver MySql não foi encontrado "
						+ e.getMessage());

			} catch (SQLException e) {
				throw new SQLException("Erro ao conectar "
						+ "com a base de dados" +
						e.getMessage());
			}
		}
		
		public static void fechaConexao(Connection conn) {

			try {
				if (conn != null) {
					conn.close();
					System.out.println("Fechada a conexão com o banco de dados");
				}

			} catch (Exception e) {
				System.out.println("Não foi possível fechar	a conexão com o banco de dados " + e.getMessage());
			}
		}
		
		public static void fechaConexao(Connection conn, PreparedStatement stmt) {

					try {
						if (conn != null) {
							fechaConexao(conn);
						}
						if (stmt != null) {
							stmt.close();
							System.out.println("Statement fechado com sucesso");
						}


					} catch (Exception e) {
						System.out.println("Não foi possível fechar o statement " + e.getMessage());
					}
				}
		
		public static void fechaConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {

					try {
						if (conn != null || stmt != null) {
							fechaConexao(conn, stmt);
						}
						if (rs != null) {
							rs.close();
							System.out.println("ResultSet fechado com sucesso");
						}


					} catch (Exception e) {
						System.out.println("Não foi possível fechar o ResultSet " + e.getMessage());
					}
				}
}