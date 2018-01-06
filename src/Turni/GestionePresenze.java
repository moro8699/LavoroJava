package Turni;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import Eccezioni.ElementoNonTrovato;
import packageImpianti.Impianto;

public class GestionePresenze extends JFrame {

	private static final long serialVersionUID = -9102017953366760739L;
	protected static Impianto i;
	private JToolBar strumenti;
	private JButton aggiungiPresenza, modificaPresenza, eliminaPresenza;
	private JScrollPane scrollPane;
	private JTable tabellaPresenze;
	private static DefaultTableModel modelTabellaPresenze;
	private static Vector<Vector<String>> presenze;
	private static Vector<String> header;
	
	public GestionePresenze(Impianto i){
		
		super ("Presenze utilizabili nell'impianto di " +i.toString());
		GestionePresenze.i = i;
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlToolbar = new JPanel();
		pnlToolbar.setLayout(new BorderLayout());
		strumenti = new JToolBar();
		strumenti.setFloatable(false);
		pnlToolbar.add(strumenti);
		
		aggiungiPresenza = new JButton(" + ");
		aggiungiPresenza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SetPresenza(getImpianto());
			}
		});
		strumenti.add(aggiungiPresenza);
		
		modificaPresenza = new JButton("Modifica");
		modificaPresenza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabellaPresenze.getSelectedRow()>=0)
					new SetPresenza(vectorToPresenza(tabellaPresenze.getSelectedRow()), tabellaPresenze.getSelectedRow());
			}
		});
		strumenti.add(modificaPresenza);	
		
		eliminaPresenza = new JButton("Elimina Presenza");
		eliminaPresenza.addActionListener(new EliminaPresenza());
		strumenti.add(eliminaPresenza);
		
		inizializzaElenco();		
		modelTabellaPresenze = new DefaultTableModel(presenze, header){
			private static final long serialVersionUID = 8359235299989940736L;		    
			@Override
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }			
		};
		tabellaPresenze = new JTable(modelTabellaPresenze);
		scrollPane = new JScrollPane(tabellaPresenze);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.add(scrollPane);
		
		getContentPane().add(pnlToolbar, BorderLayout.NORTH);
		getContentPane().add(center, BorderLayout.CENTER);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1024, 768);
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
	}
	
	
	private static void inizializzaElenco(){
		header = new Vector<String>();
		header.addElement("Identificativo");
		header.addElement("Descrizione");
		header.addElement("Ora Inizio");
		header.addElement("Ora Fine");
		header.addElement("Pausa");
		header.addElement("Impegno");
		
		presenze = caricaElencoPresenze();
	}
	
	public static void aggiungiPresenzaAModel(Presenza p){
		modelTabellaPresenze.addRow(presenzaToVector(p));
	}
	
	private static Vector<String> presenzaToVector(Presenza p){
		Vector<String> temp = new Vector<String>();
		
		temp.add(0, p.getIdentificativo());
		temp.add(1, p.getDescrizione());
		temp.add(2, p.getInizio().toString());
		temp.add(3, p.getFine().toString());
		temp.add(4, p.getPausa().toString());
		temp.add(5, p.impegno().toString());
		
		return temp;
	}
	
	private Presenza vectorToPresenza (int indice){
		Vector<String> temp = presenze.elementAt(indice);
		Presenza presenzaTemp = new PresenzaLavorativa(i, temp.elementAt(0), "", null, null, null);	
		return ElencoPresenze.restituisciPresenzaInElenco(presenzaTemp);
	}
	
	private static Vector<Vector<String>> caricaElencoPresenze(){
		Vector<Vector<String>> temp = new Vector<Vector<String>>();
		Iterator<Presenza> iterator = ElencoPresenze.getElencoPresenze().iterator();
		while (iterator.hasNext()){
			Presenza p = iterator.next();
			if (p.getImpianto().equals(i)) temp.add(aggiungiPresenza(p.getIdentificativo(), p.getDescrizione(), 
					p.getInizio().toString(), p.getFine().toString(), p.getPausa().toString(), p.impegno().toString()));
		}
		return temp;
	}
	
	private static Vector<String> aggiungiPresenza(String identificativo, String descrizione, String oraInizio, String oraFine, String pausa,
			String impegno){
		Vector<String> temp = new Vector<String>();
		
		temp.add(0, identificativo);
		temp.add(1, descrizione);
		temp.add(2, oraInizio);
		temp.add(3, oraFine);
		temp.add(4, pausa);
		temp.add(5, impegno);
		
		return temp;
	}
	
	public static void ModificaPresenzaAModel(Presenza p, int indice){
		
		modelTabellaPresenze.setValueAt(p.getIdentificativo(), indice, 0);
		modelTabellaPresenze.setValueAt(p.getDescrizione(), indice, 1);
		modelTabellaPresenze.setValueAt(p.getInizio().toString(), indice, 2);
		modelTabellaPresenze.setValueAt(p.getFine().toString(), indice, 3);
		modelTabellaPresenze.setValueAt(p.getPausa().toString(), indice, 4);
		modelTabellaPresenze.setValueAt(p.impegno().toString(), indice, 5);
		
	}

	public static Impianto getImpianto(){
		return i;
	}
	
	class EliminaPresenza implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabellaPresenze.getSelectedRow()>=0){
				Presenza p = vectorToPresenza(tabellaPresenze.getSelectedRow());
				int conferma = JOptionPane.showConfirmDialog(null, "Sei Sicuro?", "Attesa Conferma", JOptionPane.YES_NO_OPTION);
				if (conferma== JOptionPane.YES_OPTION){
					try {
						ElencoPresenze.rimuoviPresenza(p);
						modelTabellaPresenze.removeRow(tabellaPresenze.getSelectedRow());
						ElencoPresenze.salvaElenco();
					} catch (ElementoNonTrovato exc) {
						JOptionPane.showMessageDialog(null, exc.toString());
					}	
				}
			}
		}
		
	}
	
}
