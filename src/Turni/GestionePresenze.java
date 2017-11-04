package Turni;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import packageImpianti.Impianto;

public class GestionePresenze extends JFrame {

	private static final long serialVersionUID = -9102017953366760739L;
	private Impianto i;
	private JTable tabellaPresenze;
	private DefaultTableModel modelTabellaPresenze;
	private Vector<Vector<String>> elencoPresenze;
	private Vector<String> header;
	
	public GestionePresenze(Impianto i){
		
		super ("Presenze utilizabili nell'impianto di " +i.toString());
		this.i = i;
		
		tabellaPresenze = new JTable(modelTabellaPresenze);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1024, 768);
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		inizializzaElenco();
	}
	
	private void inizializzaElenco(){
		header = new Vector<String>();
		header.addElement("Presenza");
		header.addElement("Identificativo");
		header.addElement("Ora Inizio");
		header.addElement("Ora Fine");
		header.addElement("Pausa");
		
		inizializzaEncoPresenze();
	}
	
	private void inizializzaEncoPresenze(){
		elencoPresenze = new Vector<Vector<String>>();
		Iterator<Presenza> iterator = ElencoPresenze.getElencoPresenze().iterator();
		while (iterator.hasNext()){
			Impianto impiantoPresenza = iterator.next().getImpianto();
		}
	}

	public Impianto getImpianto(){
		return i;
	}
}
