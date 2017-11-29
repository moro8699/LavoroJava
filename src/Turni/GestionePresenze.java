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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import packageImpianti.Impianto;

public class GestionePresenze extends JFrame {

	private static final long serialVersionUID = -9102017953366760739L;
	protected static Impianto i;
	private JToolBar strumenti;
	private JButton aggiungiPresenza;
	private JScrollPane scrollPane;
	private JTable tabellaPresenze;
	private static DefaultTableModel modelTabellaPresenze;
	private static Vector<Vector<String>> elencoPresenze;
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
				new SetPresenza(i);
			}
		});
		strumenti.add(aggiungiPresenza);
		
		inizializzaElenco();
		modelTabellaPresenze = new DefaultTableModel(elencoPresenze, header);
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
		
		elencoPresenze = caricaElencoPresenze();
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
	
	private static Vector<String> aggiungiPresenza(String idetificativo, String descrizione, String oraInizio, String oraFine, String pausa,
			String impegno){
		Vector<String> temp = new Vector<String>();
		
		temp.add(0, idetificativo);
		temp.add(1, descrizione);
		temp.add(2, oraInizio);
		temp.add(3, oraFine);
		temp.add(4, pausa);
		temp.add(5, impegno);
		
		return temp;
	}

	public Impianto getImpianto(){
		return i;
	}
	
}
