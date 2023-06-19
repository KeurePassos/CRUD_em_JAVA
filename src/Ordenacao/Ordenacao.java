package Ordenacao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Contato.Contato;
import ContatoDao.ContatoDao;

public class Ordenacao extends JFrame {

	private DefaultTableModel modelo = new DefaultTableModel();
	private JButton btOrdemAlfabetica;
	
	
	public Ordenacao(DefaultTableModel md) {
		super("Contatos");
		modelo = md;
	}
	
	private class BtOrdemAlfabeticaListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}