package Turni;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Generici.Controllo;
import packageImpianti.Impianto;

public class SetPresenza extends JFrame {

	private static final long serialVersionUID = 2147285437901518775L;
	private final static int R_IMPIANTO =0, R_IDENT =1, R_DESCR =2, R_INIZIO =3, R_FINE =4, R_PAUSA =5;
	private Vector<Vector<String>> datiPresenza;
	private Vector<String> header, nomeRiga, valorePresenza;
	private JTable presenza;
	private JScrollPane scrollpane;
	private Presenza p = null;
	private Impianto i = null;
	private DefaultTableModel modelPresenza;
	private JButton ok;
	private int indiceLista;
	
	public SetPresenza(Impianto i) {
		
		this.i = i;
		InizializzaModelNuovaPresenza();
		inizializzaForm();
			
	}
	
	public SetPresenza(Presenza p, int indiceLista){
		
		this.p = p;
		this.i = p.getImpianto();
		this.indiceLista = indiceLista;
		InizializzaModelModificaPresenza();
		inizializzaForm();
			
	}
	
	public void InizializzaModelNuovaPresenza(){
		
		modelPresenza = new DefaultTableModel(){
		private static final long serialVersionUID = -8087792651113476340L;

			@Override
		    public boolean isCellEditable(int row, int column) {
				if (row == R_IMPIANTO && column == 1) return false;
				if (column != 0) return true;
		        return false;
		    }		
		};
		
	}
	
	public void InizializzaModelModificaPresenza(){
		
		modelPresenza = new DefaultTableModel(){
		private static final long serialVersionUID = -8087792651113476340L;

			@Override
		    public boolean isCellEditable(int row, int column) {
				if ((row == R_IMPIANTO && column == 1) ||
						(row == R_IDENT && column ==1))return false;
				if (column != 0) return true;
		        return false;
		    }		
		};
		
	}
	
	public void inizializzaForm(){
		
		getContentPane().setLayout(new BorderLayout());
		

		
		inizializzaHeader();
			
		if (p != null) modificaPresenza(p);
		else nuovaPresenza(i);
		
		modelPresenza.setDataVector(datiPresenza, header);		
		presenza = new JTable(modelPresenza);
		scrollpane = new JScrollPane(presenza);
		
		getContentPane().add(scrollpane, BorderLayout.CENTER);
		
		JPanel sud = new JPanel();
		ok = new JButton("OK");
		if (p != null) ok.addActionListener(new ModificaPresenza());
		else ok.addActionListener(new InserisciPresenza());
		sud.add(ok);
		getContentPane().add(sud, BorderLayout.SOUTH);
		
		setSize(500,250);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
	}
	
	private void inizializzaHeader(){
		 
		//Header Orizzontale
		header = new Vector<String>();
		header.addElement("Attributo");
		header.addElement("Valore");
		 
		//Header Verticale
		nomeRiga = new Vector<String>();
		nomeRiga.add(R_IMPIANTO, "Impianto");
		nomeRiga.add(R_IDENT, "Identificativo");
		nomeRiga.add(R_DESCR, "Descizione");
		nomeRiga.add(R_INIZIO, "Ora Inizio");
		nomeRiga.add(R_FINE, "Ora Fine");
		nomeRiga.add(R_PAUSA, "Pausa");
	}
	
	private void nuovaPresenza(Impianto i) {
		
		valorePresenza = new Vector<String>();		
		
		valorePresenza.add(R_IMPIANTO, i.toString());	
		valorePresenza.add(R_IDENT, "");		
		valorePresenza.add(R_DESCR, "");		
		valorePresenza.add(R_INIZIO, "");
		valorePresenza.add(R_FINE, "");
		valorePresenza.add(R_PAUSA, "");
		
		caricaDatiPresenza();
		
	}

	private void modificaPresenza(Presenza p) {
		
		valorePresenza = new Vector<String>();
		
		valorePresenza.add(R_IMPIANTO, p.getImpianto().toString());
		valorePresenza.add(R_IDENT, p.getIdentificativo());
		valorePresenza.add(R_DESCR, p.getDescrizione());
		valorePresenza.add(R_INIZIO, p.getInizio().toString());
		valorePresenza.add(R_FINE, p.getFine().toString());
		valorePresenza.add(R_PAUSA, p.getPausa().toString());
		
		caricaDatiPresenza();
	}
	 
	private void caricaDatiPresenza(){
		
		datiPresenza = new Vector<Vector<String>>();
		
		for(int i=0; i<nomeRiga.size(); i++){
			
			Vector<String> temp = new Vector<String>();

			temp.addElement(nomeRiga.elementAt(i));
			temp.addElement(valorePresenza.elementAt(i));
			
			datiPresenza.add(temp);
			
		}
		 
	}
	
	public boolean verificaCampi (){		
		
		if (Controllo.verificaRigaVuota((String) presenza.getValueAt(R_IDENT, 1))){
			JOptionPane.showMessageDialog(null, "Identificativo Obbligatorio");
			return false;			
		}
		
		if (Controllo.verificaRigaVuota((String) presenza.getValueAt(R_DESCR, 1))){
			JOptionPane.showMessageDialog(null, "Inserire una descrizione della Presenza inserita");
			return false;			
		}
		
		if (!(Controllo.verificaOraInserita((String) presenza.getValueAt(R_INIZIO, 1)))) {
			JOptionPane.showMessageDialog(null, "Ora di Inizio Servizio non conforme");
			return false;
		}
		if (!(Controllo.verificaOraInserita((String) presenza.getValueAt(R_FINE, 1)))){
			JOptionPane.showMessageDialog(null, "Ora di Termine Servizio non conforme");
			return false;
		}
		if (!(Controllo.verificaOraInserita((String) presenza.getValueAt(R_PAUSA, 1)))){
			JOptionPane.showMessageDialog(null, "Orario di Pausa non conforme");
			return false;		
		}
		
		return true;
	}
	
	public static LocalTime stringtoLocalTime(String orario){
		
		String[] splitter = orario.split(":");
		
		int ore = Integer.parseInt(splitter[0]),
				minuti = Integer.parseInt(splitter[1]);
		
		return LocalTime.of(ore, minuti);
	}
	
	class InserisciPresenza implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Presenza pres;
			if (verificaCampi()) {
				pres = new PresenzaLavorativa(i, 
						(String) presenza.getValueAt(R_IDENT, 1),
						(String) presenza.getValueAt(R_DESCR, 1), 
						stringtoLocalTime((String) presenza.getValueAt(R_INIZIO, 1)),
						stringtoLocalTime((String) presenza.getValueAt(R_FINE, 1)),
						stringtoLocalTime((String) presenza.getValueAt(R_PAUSA, 1)));
				if (ElencoPresenze.aggiungiPresenza(pres)){
					GestionePresenze.aggiungiPresenzaAModel(pres);
					setVisible(false);
				}
				else JOptionPane.showMessageDialog(null, "Presenza già esistente");
			}
		}
		
	}

	class ModificaPresenza implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Presenza pres;
			if (verificaCampi()) {
				pres = new PresenzaLavorativa(i, 
						(String) presenza.getValueAt(R_IDENT, 1),
						(String) presenza.getValueAt(R_DESCR, 1), 
						stringtoLocalTime((String) presenza.getValueAt(R_INIZIO, 1)),
						stringtoLocalTime((String) presenza.getValueAt(R_FINE, 1)),
						stringtoLocalTime((String) presenza.getValueAt(R_PAUSA, 1)));
				if (ElencoPresenze.modificaPresenza(p, pres)){
					GestionePresenze.ModificaPresenzaAModel(pres, indiceLista);
					setVisible(false);
				}
			}
		}
		
	}
	
}	