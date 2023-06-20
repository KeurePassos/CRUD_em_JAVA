package ContatoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import Contato.Contato;
import FabricaConexao.FabricaConexao;

/*
 * O próximo passo é implementar os métodos para atualização da base de dados.
 * Inicialmente ficam as Strings correspondentes aos comandos SQL (inserção, atualização, remoção e listagem.
 * Após os métodos responsáveis pela inserção, atualização e remoção na base de dados.
 * O método getContatos é responsável por trazer todos os contatos armazenados na base de dados.
 * O método getContatoById busca o contato correspondente ao id enviado por parâmetro na base de dados.
 */

public class ContatoDao {

	private final String INSERT = "INSERT INTO CONTATO (NOME, TELEFONE, EMAIL) VALUES (?,?,?)";
	private final String UPDATE = "UPDATE CONTATO SET NOME=?, TELEFONE=?, EMAIL=? WHERE ID=?";
	private final String DELETE = "DELETE FROM CONTATO WHERE ID =?";
	private final String LIST = "SELECT * FROM CONTATO";
	private final String LISTBYID = "SELECT * FROM CONTATO WHERE ID=?";
	private final String LISTORDERBY = "SELECT * FROM CONTATO ORDER BY NOME";
	
	public void inserir(Contato contato) {
		if (contato != null) {
			Connection conn = null;
			try {
				conn = FabricaConexao.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(INSERT);

				pstm.setString(1, contato.getNome());
				pstm.setString(2, contato.getTelefone());
				pstm.setString(3, contato.getEmail());

				pstm.execute();
				JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso");
				FabricaConexao.fechaConexao(conn, pstm);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir contato no banco de"
						+ "dados " + e.getMessage());
			}
		} else {
			System.out.println("O contato enviado por parâmetro está vazio");
		}
	}
	
	public void atualizar(Contato contato) {
		if (contato != null) {
			Connection conn = null;
			try {
				conn = FabricaConexao.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(UPDATE);

				pstm.setString(1, contato.getNome());
				pstm.setString(2, contato.getTelefone());
				pstm.setString(3, contato.getEmail());
				pstm.setInt(4, contato.getId());

				pstm.execute();
				JOptionPane.showMessageDialog(null, "Contato alterado com sucesso");
				FabricaConexao.fechaConexao(conn);

			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar contato no banco de"
						+ "dados " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "O contato enviado por parâmetro está vazio");
		}


	}
	
	public void remover(int id) {
		Connection conn = null;
		try {
			conn = FabricaConexao.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(DELETE);

			pstm.setInt(1, id);

			pstm.execute();
			FabricaConexao.fechaConexao(conn, pstm);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir contato do banco de"
					+ "dados " + e.getMessage());
		}
	}
	
	public List<Contato> getContatos() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		try {
			conn = FabricaConexao.getConexao();
			pstm = conn.prepareStatement(LIST);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Contato contato = new Contato();

				contato.setId(rs.getInt("id"));
				contato.setNome(rs.getString("nome"));
				contato.setTelefone(rs.getString("telefone"));
				contato.setEmail(rs.getString("email"));
				contatos.add(contato);
			}
			FabricaConexao.fechaConexao(conn, pstm, rs);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contatos" + e.getMessage());
		}
		return contatos;
	}
	
	public Contato getContatoById(int id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Contato contato = new Contato();
		try {
			conn = FabricaConexao.getConexao();
			pstm = conn.prepareStatement(LISTBYID);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				contato.setId(rs.getInt("id"));
				contato.setNome(rs.getString("nome"));
				contato.setTelefone(rs.getString("telefone"));
				contato.setEmail(rs.getString("email"));
			}
			FabricaConexao.fechaConexao(conn, pstm, rs);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contatos" + e.getMessage());
		}
		return contato;
	}
	
	public Contato getContatoOrderBy(String nome) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Contato contatoOrdenado = new Contato();
		try {
			conn = FabricaConexao.getConexao();
			pstm = conn.prepareStatement(LISTORDERBY);
			pstm.setString(2, nome);
			rs = pstm.executeQuery();
			while (rs.next()) {
				contatoOrdenado.setId(rs.getInt("id"));
				contatoOrdenado.setNome(rs.getString("nome"));
				contatoOrdenado.setTelefone(rs.getString("telefone"));
				contatoOrdenado.setEmail(rs.getString("email"));
			}
			FabricaConexao.fechaConexao(conn, pstm, rs);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contatos" + e.getMessage());
		}
		return contatoOrdenado;
	}
	}
