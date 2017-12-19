package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Eccezioni.ElementoGiaEsistente;
import Generici.Controllo;
import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;
import packageDipendenti.SetDipendenteSW;
import packageImpianti.ImpiantiSW;

public class Principale extends JFrame {

	private static final long serialVersionUID = -7652191700375937203L;
	
	public static final byte COLONNA_MATRICOLA = 0;
	public static final byte COLONNA_COGNOME_NOME = 1;
	public static final byte COLONNA_IMPIANTO = 2;
	public static final byte COLONNA_DATA_ASSUNZIONE = 3;
	public static final byte COLONNA_STATO_FISICO = 4;

	
	private JMenuBar barradeiMenu;
	private JToolBar toolbarDip;
	private JMenu file, modifica, impianti;
	private JMenuItem aggiungiImpianto;
	private JButton addDipendente, remDipendente, setDipendente, Impianti;
	private TitledBorder titleLista;
	private static JTable tabellaPersonale;
	private static DefaultTableModel modelloTable;
	private Vector<String> header; 
	private Vector<Vector<String>> listaPersonale;
	
	public  Principale(){
		
		super("GEST 1.0 MENU PRINCIPALE");
		
		barradeiMenu = new JMenuBar();
		file = new JMenu("File");
		modifica = new JMenu("Modifica");
		impianti = new JMenu("Impianti");
		aggiungiImpianto = new JMenuItem("Aggiungi Impianto");
		barradeiMenu.add(file);
		barradeiMenu.add(modifica);
		barradeiMenu.add(impianti);
		impianti.add(aggiungiImpianto);
		setJMenuBar(barradeiMenu);

		getContentPane().setLayout(new BorderLayout());

		//Costruzione Frame Principale

		toolbarDip = new JToolBar();
		toolbarDip.setFloatable(false);
		JPanel pnlNord = new JPanel();
		getContentPane().add(pnlNord, BorderLayout.NORTH);
		pnlNord.setLayout(new BorderLayout());
		pnlNord.add(toolbarDip, BorderLayout.CENTER);
		addDipendente = new JButton();
		addDipendente.setIcon(new ImageIcon("./Icons/newUser.png"));
		remDipendente = new JButton();
		remDipendente.setIcon(new ImageIcon("./Icons/remUser.png"));
		setDipendente = new JButton();
		setDipendente.setIcon(new ImageIcon("./Icons/setUser.png"));
		Impianti = new JButton();
		Impianti.setIcon(new ImageIcon("./Icons/setImpianto.png"));
		addDipendente.addActionListener(new AddDipendente());
		remDipendente.addActionListener(new RemDipendente());
		setDipendente.addActionListener(new SetDipendente());
		Impianti.addActionListener(new GestImpianti());
		toolbarDip.add(addDipendente);
		toolbarDip.add(remDipendente);
		toolbarDip.add(setDipendente);
		toolbarDip.add(Impianti);

		JPanel pnlCentro = new JPanel();
		getContentPane().add(pnlCentro, BorderLayout.CENTER);		
		pnlCentro.setLayout(new GridLayout(2, 0));
		
		JPanel pnlCentroNord = new JPanel();
		pnlCentroNord.setLayout(new BorderLayout());
		pnlCentro.add(pnlCentroNord, BorderLayout.CENTER);
		
		modelloTable = new DefaultTableModel() {
			private static final long serialVersionUID = 8359235299989940736L;		    
			@Override
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }			
		};
		inizializzaTableModel();
		modelloTable.setDataVector(listaPersonale, header);
		tabellaPersonale = new JTable(modelloTable);
		tabellaPersonale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		titleLista = new TitledBorder("Lista Dipendenti");
		pnlCentroNord.setBorder(titleLista);
		JScrollPane scroll = new JScrollPane(tabellaPersonale);
		pnlCentroNord.add(scroll);	
		
		setSize(700,700);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static DefaultTableModel getModelloTable () {
		return modelloTable;
	}
	public Vector<Vector<String>> getListaPersonale(){
		return listaPersonale;
	}
	public static int posizioneAttualeTable() {
		return tabellaPersonale.getSelectedRow();
	}
	
	private void inizializzaTableModel() {
		header = new Vector<String>();
		header.addElement("Matricola");
		header.addElement("Dipendente");
		header.addElement("Impianto");
		header.addElement("Stato Attuale");
		
		inizializzaTabellaPersonale();
	}
	
	public static void aggiornaImpiantiModel() {
		int inizio =0, fine = getModelloTable().getRowCount();
		for(int i = inizio; i<fine; i++) {
			Dipendente d = ListaDipendenti.cercaDipendente((String) getModelloTable().getValueAt(i, COLONNA_MATRICOLA));
			getModelloTable().setValueAt(d.getImpiantoDiAppartenenza(), i, COLONNA_IMPIANTO);
		}
	}
	
	private void inizializzaTabellaPersonale() {
		listaPersonale = new Vector<Vector<String>>();
		Iterator<Dipendente> i = ListaDipendenti.getListaDipendenti().iterator();
		while (i.hasNext()) {
			Dipendente d = i.next();
			listaPersonale.addElement(dipendenteToVector(d));
		}
	}
	/*
	//Visualizza i Dati del Dipendente selezionato dalla Lista
	class ViewDatiDipendente implements ListSelectionListener {

	     @Override   
		 public void valueChanged(ListSelectionEvent e) { 
			 if ((modelDip.size() >0) && (listaD.getSelectedValue() != null)) {
				 Dipendente d = modelToDipendente(listaD.getSelectedValue());
				 Dipendente real = ListaDipendenti.confronta(d);
				 nome.setText(real.getNome());
				 cognome.setText(real.getCognome());
				 matricola.setText(real.getMatricola());
				 impianto.setText(real.labelImpianto());
				 telefoni.setText(ElencoTelefonicoDipendenti.cercaTelefoni(real));
			}
	    }
	}*/
	
	// rimuove un dipendente dalla Lista
	public void rimuoviDipendente(String matricola) {
		if (ListaDipendenti.rimuoviDipendente(new Dipendente("","", matricola))) {
			modelloTable.removeRow(tabellaPersonale.getSelectedRow());
		}
	}
		
	public static Vector<String> dipendenteToVector(Dipendente d){
		Vector<String> dipendenteVector = new Vector<String>();
		dipendenteVector.addElement(d.getMatricola());
		dipendenteVector.addElement(d.getCognome() + " " + d.getNome());
		dipendenteVector.addElement(d.getImpiantoDiAppartenenza());
		dipendenteVector.addElement("" + d.StatoDiSaluteToString());
		
		return dipendenteVector;
	}
	
	class RemDipendente implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int conferma = JOptionPane.showConfirmDialog(null , "Sei sicuro ?", "ATTENZIONE!!", JOptionPane.YES_NO_OPTION);		
			if (conferma == JOptionPane.YES_OPTION)	{
				try{
					Vector<String> dipendente = listaPersonale.elementAt(tabellaPersonale.getSelectedRow());
					rimuoviDipendente(dipendente.elementAt(0));
				}
				catch (NullPointerException d) { 
					JOptionPane.showMessageDialog(null, "Nessun Dipendente selezionato");
				}
			}
		}
		
	}
	class AddDipendente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new FrameDipendenti();
			}	
	}	
	class SetDipendente implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabellaPersonale.getSelectedRow() >= 0) {
				Vector<String> dip = listaPersonale.elementAt(tabellaPersonale.getSelectedRow());
				Dipendente d = new Dipendente("","",dip.elementAt(0));
				new SetDipendenteSW(ListaDipendenti.confronta(d));
			}
		}
		
	}
	class FrameDipendenti extends JFrame {
		
		private static final long serialVersionUID = -971152471889661132L;
		JPanel pnlNord = new JPanel();
		JPanel pnlCentro = new JPanel();
		JPanel pnlSud = new JPanel();
		JTextField txtMatricola = new JTextField(10);
		JTextField txtNome = new JTextField(10);
		JTextField txtCognome = new JTextField(10);
		JButton btnOK = new JButton("OK");
		JButton btnCancel = new JButton("Annulla");
		
		public FrameDipendenti(){
			
			super("Inserimento Dipendente");
			pnlNord.add(new JLabel("Dati Dipendente"));
			pnlCentro.setLayout(new GridLayout(3, 2));
			pnlCentro.add(new JLabel("Matricola"));
			pnlCentro.add(txtMatricola);
			pnlCentro.add(new JLabel("Nome"));
			pnlCentro.add(txtNome);
			pnlCentro.add(new JLabel("Cognome"));
			pnlCentro.add(txtCognome);
			pnlSud.add(btnOK);
			btnOK.addActionListener(new AddDipendente());
			pnlSud.add(btnCancel);
			btnCancel.addActionListener(new Cancel());
			getContentPane().add(pnlNord, BorderLayout.NORTH);
			getContentPane().add(pnlCentro, BorderLayout.CENTER);
			getContentPane().add(pnlSud, BorderLayout.SOUTH);
			setSize(350,150);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
			setVisible(true);
		}
		
		class AddDipendente implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {			
				
				Dipendente d = new Dipendente(txtNome.getText(), txtCognome.getText(), txtMatricola.getText());
				
				if ((inserimentoCorretto(d))) { 
					try {
						ListaDipendenti.aggiungiDipendente(d);
						modelloTable.addRow(dipendenteToVector(d));
						ListaDipendenti.salvaElencoDipendenti();
						setVisible(false);
					} catch (ElementoGiaEsistente exc) {
						JOptionPane.showMessageDialog(null, exc.toString());
					}		
				} else JOptionPane.showMessageDialog(null, "Dati Non Corretti");		
			}
		}
		
		private boolean inserimentoCorretto(Dipendente d){
			if (Controllo.verificaMatricolaDipendente(d.getMatricola()) 
					&& d.getNome()!= ""
					&& d.getCognome()!= "") return true;
			return false;
		}
		
		class Cancel implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);	
			}
			
		}
		
	}
	
	class GestImpianti implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new ImpiantiSW();
		}
		
	}
}