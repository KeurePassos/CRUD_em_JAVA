package ListarContato;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import AtualizarContato.AtualizarContato;
import Contato.Contato;
import ContatoDao.ContatoDao;
import InserirContato.InserirContato;

/*
 * A classe ListarContato é responsável por exibir uma tabela com os contatos cadastrados 
 * na base de dados e chama os formulários de inserção, atualização e remoção de um contato.
 * 
 * O método criaJTable instancia um objeto do tipo JTable, adiciona colunas à tabela - addColumn- e
 * seta seus respectivos tamanhos - setPreferredWidth.
 * 
 * O método pesquisar é responsável por chamar o método getContatos da classe ContatoDAO e
 * adicionar os contatos vindos do banco à tabela. Isto é feito através do método addRow. Antes
 * de adicionar os dados na tabela, exclui-se todas as linhas presente nesta. Isso é feito através do
 * método setNumRows que seta em zero o número de linhas. Isto é feito para que não se
 * dupliquem dados cada vez que o método pesquisar é invocado. Um outro detalhe é que o
 * método pesquisar está declarado como static. A declaração foi feita desta forma porque
 * outras classes precisam acessar o método, veremos isso adiante.
 * 
 * No evento do botão inserir, como é possível observar no código, ao clicar no botão inserir, o
 * formulário de inserção de um novo contato é exibido na tela. Para o construtor da classe
 * InserirContato é necessário enviar o modelo da tabela para que a mesma seja reconstruída.
 * 
 * No botão atualizar, como necessita-se saber qual o contato deve ser atualizado, é indispensável
 * que uma linha da tabela esteja selecionada. O método getSelectedRow retorna o id referente à
 * linha selecionada. Com o método getValueAt é possível retornar o valor do dado preenchido
 * na tabela, desde que passados os parâmetros linha e coluna correspondentes. Após a obtenção
 * desses dados o formulário de edição é exibido na tela. Para o formulário da edição do contato
 * é necessário enviar o modelo da tabela, para que sua visualização seja alterada, o id do contato
 * a ser alterado e a linha selecionada.
 * 
 * No botão excluir,primeiramente é necessário identificar o id do contato a ser removido. Isso é
 * feito com a junção dos métodos getSelectedRow, que retorna o id da linha selecionada e o
 * método getValueAt que retorna o valor presente na linha e coluna enviados por parâmetro.
 * Em posse do id do Contato chama-se o método remover implementado na classe ContatoDAO,
 * para efetuar a remoção da base de dados. Em seguida, o método removeRow é invocado para
 * excluir a linha da tabela.
 */

public class ListarContato extends JFrame {

	private JPanel painelFundo;
	private JPanel painelBotoes;
	private JTable tabela;
	private JScrollPane barraRolagem;
	private JButton btInserir;
	private JButton btExcluir;
	private JButton btEditar;
	private JButton btOrdemAlfabetica;
	private DefaultTableModel modelo = new DefaultTableModel();
	
	public ListarContato() {
		super("Contatos");
		criaJTable();
		criaJanela();
	}
	
	public void criaJanela() {
		btInserir = new JButton("Inserir");
		btExcluir = new JButton("Excluir");
		btEditar = new JButton("Editar");
		btOrdemAlfabetica = new JButton("A↓Z");
		painelBotoes = new JPanel();
		barraRolagem = new JScrollPane(tabela);
		painelFundo = new JPanel();
		painelFundo.setLayout(new BorderLayout());
		painelFundo.add(BorderLayout.CENTER, barraRolagem);
		painelBotoes.add(btInserir);
		painelBotoes.add(btEditar);
		painelBotoes.add(btExcluir);
		painelBotoes.add(btOrdemAlfabetica);
		painelFundo.add(BorderLayout.SOUTH, painelBotoes);
		
		getContentPane().add(painelFundo);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 320);
		setVisible(true);
		btInserir.addActionListener(new BtInserirListener());
		btEditar.addActionListener(new BtEditarListener());
		btExcluir.addActionListener(new BtExcluirListener());
		btOrdemAlfabetica.addActionListener(new BtOrdemAlfabetica());
	}
	
	private void criaJTable() {
		tabela = new JTable(modelo);
		modelo.addColumn("Id");
		modelo.addColumn("Nome");
		modelo.addColumn("Telefone");
		modelo.addColumn("Email");
		tabela.getColumnModel().getColumn(0)
		.setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1)
		.setPreferredWidth(120);
		tabela.getColumnModel().getColumn(1)
		.setPreferredWidth(80);
		tabela.getColumnModel().getColumn(1)
		.setPreferredWidth(120);
		pesquisar(modelo);
	}
	
	public static void pesquisar(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		ContatoDao dao = new ContatoDao();

		for (Contato c : dao.getContatos()) {
			modelo.addRow(new Object[]{c.getId(), c.getNome(),
			c.getTelefone(), c.getEmail()});
		}
	}
	
	private class BtInserirListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			InserirContato ic = new InserirContato(modelo);
			ic.setVisible(true);
		}
	}
	
	private class BtEditarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int idContato = (int) tabela
				.getValueAt(linhaSelecionada, 0);
				AtualizarContato ic = new AtualizarContato(modelo, idContato, linhaSelecionada);
				ic.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null,
				"É necesário selecionar uma linha.");
			}
		}
	}
	
	private class BtExcluirListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int idContato = (int)
				tabela.getValueAt(linhaSelecionada, 0);
				ContatoDao dao = new ContatoDao();
				dao.remover(idContato);
				modelo.removeRow(linhaSelecionada);
			} else {
				JOptionPane.showMessageDialog(null,
				"É necesário selecionar uma linha.");
			}
		}
	}
	
	private class BtOrdemAlfabetica implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
						
			ContatoDao dao = new ContatoDao();
			dao.getContatoOrderBy(getName());

		}	
	}
	
	public static void main(String[] args) {
	ListarContato lc = new ListarContato();
	lc.setVisible(true);
	}
	
}