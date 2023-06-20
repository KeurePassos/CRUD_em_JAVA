package InserirContato;

/*
 * Na visualização, existe uma classe responsável por exibir o formulário de inserção de um novo contato.
 * Quando o botão salvar é clicado, todos os dados presentes nas caixas de texto são
 * armazenados nos respectivos atributos de um contato. Em seguida, o método inserir da classe
 * ContatoDAO é invocado, efetuando a inserção na base de dados. Como o id do contato é gerado
 * apenas na base de dados, torna-se necessária a consulta na mesma para o preenchimento
 * deste novo contato na tabela. Deste modo, o método pesquisar criado na classe ListarContato é invocado. * 
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Contato.Contato;
import ContatoDao.ContatoDao;
import ListarContato.ListarContato;

public class InserirContato extends JFrame {

	private DefaultTableModel modelo = new DefaultTableModel();
	private JPanel painelFundo;
	private JButton btSalvar;
	private JButton btLimpar;
	private JLabel lbNome;
	private JLabel lbTelefone;
	private JLabel lbEmail;
	private JTextField txNome;
	private JTextField txTelefone;
	private JTextField txEmail;
	
	public InserirContato(DefaultTableModel md) {
		super("Contatos");
		criaJanela();
		modelo = md;
	}
	
	public void criaJanela() {
		btSalvar = new JButton("Salvar");
		btLimpar = new JButton("Limpar");
		lbNome = new JLabel("         Nome:   ");
		lbTelefone = new JLabel("         Telefone:   ");
		lbEmail = new JLabel("         Email:   ");
		txNome = new JTextField(10);
		try {
		    MaskFormatter mascara = new MaskFormatter("(##) #####-####");
		    txTelefone = new JFormattedTextField(mascara);
		} catch (Exception e) {
		    txTelefone = new JTextField();
		}
		txEmail = new JTextField();
		
		painelFundo = new JPanel();
		painelFundo.setLayout(new GridLayout(4, 2, 2, 4));
		painelFundo.add(lbNome);
		painelFundo.add(txNome);
		painelFundo.add(lbTelefone);
		painelFundo.add(txTelefone);
		painelFundo.add(lbEmail);
		painelFundo.add(txEmail);
		painelFundo.add(btLimpar);
		painelFundo.add(btSalvar);
		
		getContentPane().add(painelFundo);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setVisible(true);
		btSalvar.addActionListener(new BtSalvarListener());
		btLimpar.addActionListener(new BtLimparListener());
	}
	
	private class BtSalvarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Contato c = new Contato();
			c.setNome(txNome.getText());
			c.setTelefone(txTelefone.getText());
			c.setEmail(txEmail.getText());

			ContatoDao dao = new ContatoDao();
			dao.inserir(c);
			ListarContato.pesquisar(modelo);

			setVisible(false);
		}
	}
	
	private class BtLimparListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			txNome.setText("");
			txTelefone.setText("");
			txEmail.setText("");
		}
	}
	
}
