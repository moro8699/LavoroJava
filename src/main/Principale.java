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

import eccezioni.ElementoGiaEsistente;
import eccezioni.ElementoNonTrovato;
import generici.Controllo;
import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;
import packageDipendenti.SetDipendenteSW;
import packageImpianti.GestioneImpianto;
import packageImpianti.Impianti;
import packageImpianti.ImpiantiSW;
import packageImpianti.Impianto;
import turni.GestioneAssenze;

public class Principale extends JFrame {

	private static final long serialVersionUID = -7652191700375937203L;
	
	public static final byte COLONNA_MATRICOLA = 0;
	public static final byte COLONNA_COGNOME_NOME = 1;
	public static final byte COLONNA_IMPIANTO = 2;
	public static final byte COLONNA_DATA_ASSUNZIONE = 3;
	public static final byte COLONNA_STATO_FISICO = 4;

	private static JMenuBar barradeiMenu;
	private JToolBar toolbarDip;
	private JMenu file, inserimenti;
	private static JMenu impianti;
	private static JMenuItem gestioneImpianti, gestioneAssenze;
	private JButton addDipendente, remDipendente, setDipendente;
	private TitledBorder titleLista;
	private static JTable tabellaPersonale;
	private static DefaultTableModel modelloTable;
	private Vector<String> header; 
	private Vector<Vector<String>> listaPersonale;
	
	public  Principale(){
		
		super("GEST 1.0 MENU PRINCIPALE");
		
		barradeiMenu = new JMenuBar();
		file = new JMenu("File");
		inserimenti = new JMenu("Inserimenti");
		impianti = new JMenu("Impianti");
		inizializzaJMenuImpianti();
		barradeiMenu.add(file);
		barradeiMenu.add(inserimenti);
		barradeiMenu.add(impianti);
		gestioneAssenze = new JMenuItem("Assenze dal Servizio");
		inserimenti.add(gestioneAssenze);
		
		gestioneAssenze.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GestioneAssenze();
				
			}
		});
		
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
		addDipendente.addActionListener(new AddDipendente());
		remDipendente.addActionListener(new RemDipendente());
		setDipendente.addActionListener(new SetDipendente());
		toolbarDip.add(addDipendente);
		toolbarDip.add(remDipendente);
		toolbarDip.add(setDipendente);
		
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
	
	private static void inizializzaJMenuImpianti(){	
		
		gestioneImpianti = new JMenuItem("Gestione Impianti");
		impianti.add(gestioneImpianti);
		gestioneImpianti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ImpiantiSW();	
			}
		});
		
		impianti.addSeparator();
		
		for(int i =0; i < Impianti.getSize(); i++){
			Impianto impianto = Impianti.getImpiantoSelezionato(i);
			JMenuItem item = new JMenuItem(impianto.getNomeImpianto());
			impianti.add(item);
			item.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new GestioneImpianto(impianto);	
				}
			});
		}
		
	}
	
	public static void updateJMenuImpianti(){
		impianti.removeAll();
		inizializzaJMenuImpianti();
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
			getModelloTable().setValueAt(Controllo.cercaImpiantoDiAppartenenza(d), i, COLONNA_IMPIANTO);
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
	
	// rimuove un dipendente dalla Lista
	public void rimuoviDipendente(String matricola) {
		try{
			ListaDipendenti.rimuoviDipendente(new Dipendente("","", matricola));
			modelloTable.removeRow(tabellaPersonale.getSelectedRow());
		} catch (ElementoNonTrovato e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
		
	public static Vector<String> dipendenteToVector(Dipendente d){
		Vector<String> dipendenteVector = new Vector<String>();
		dipendenteVector.addElement(d.getMatricola());
		dipendenteVector.addElement(d.getCognome() + " " + d.getNome());
		dipendenteVector.addElement(d.getImpiantoDiAppartenenza());
		dipendenteVector.addElement("" + d.getStatoDiSalute());
		
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
				new SetDipendenteSW(ListaDipendenti.cercaDipendente(dip.elementAt(0)));
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