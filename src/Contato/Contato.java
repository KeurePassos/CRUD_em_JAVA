package Contato;

/*
 * DefaultTableModel
 * O DefaultTableModel é uma classe do pacote javax.swing.table e implementa a interface
 * TableModel, fornecendo todo o controle dos dados da JTable.
 * 
 * Inicialmente vamos criar a classe de negócio Contato. * 
 */

public class Contato {
	private int id;
	private String nome;
	private String telefone;
	private String email;

	public Contato() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}