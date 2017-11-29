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
	protected Impianto i;
	private JToolBar strumenti;
	private JButton aggiungiPresenza;
	private JScrollPane scrollPane;
	private JTable tabellaPresenze;
	private DefaultTableModel modelTabellaPresenze;
	private Vector<Vector<String>> elencoPresenze;
	private Vector<String> header;
	
	public GestionePresenze(Impianto i){
		
		super ("Presenze utilizabili nell'impianto di " +i.toString());
		this.i = i;
		
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
	
	private void inizializzaElenco(){
		header = new Vector<String>();
		header.addElement("Identificativo");
		header.addElement("Descrizione");
		header.addElement("Ora Inizio");
		header.addElement("Ora Fine");
		header.addElement("Pausa");
		
		elencoPresenze = caricaElencoPresenze();
	}
	
	private Vector<Vector<String>> caricaElencoPresenze(){
		Vector<Vector<String>> temp = new Vector<Vector<String>>();
		Iterator<Presenza> iterator = ElencoPresenze.getElencoPresenze().iterator();
		while (iterator.hasNext()){
			Presenza p = iterator.next();
			if (p.getImpianto().equals(i)) temp.add(aggiungiPresenza(p.getIdentificativo(), p.getDescrizione(), 
					p.getInizio().toString(), p.getFine().toString(), p.getPausa().toString()));
		}
		return temp;
	}
	
	private Vector<String> aggiungiPresenza(String idetificativo, String descrizione, String oraInizio, String oraFine, String pausa){
		Vector<String> temp = new Vector<String>();
		
		temp.add(0, idetificativo);
		temp.add(1, descrizione);
		temp.add(2, oraInizio);
		temp.add(3, oraFine);
		temp.add(4, pausa);
		
		return temp;
	}

	public Impianto getImpianto(){
		return i;
	}
	
}
