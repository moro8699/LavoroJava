package packageImpianti;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;

public class GestioneImpianto extends JFrame {

	private static final long serialVersionUID = 1573062133006083688L;
	private JPanel nord, nordOvest;
	private JTable turno;
	private Vector<String> personale, intestazioneColonne;
	private Vector<Vector<String>> listaPersonale;
	private Impianto impianto;
	DefaultTableModel model = new DefaultTableModel() {
		private static final long serialVersionUID = 5112828046840321739L;
		@Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }	    
	};
	public GestioneImpianto(Impianto i) {
		
		super("Gestione Impianto Di "+i.getNomeImpianto());
		
		impianto = i;
		
		getContentPane().setLayout(new GridLayout(2, 0));
		
		inizializzaTabellaPersonale();
		
		model.setDataVector(listaPersonale, intestazioneColonne);
		turno = new JTable(model);
		
		nord = new JPanel();
		nord.setLayout(new GridLayout(0,2));

		nordOvest = new JPanel();
		JScrollPane scrollPane = new JScrollPane(turno);
		nordOvest.add(scrollPane);
		
		nord.add(nordOvest);
		getContentPane().add(nord);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1024, 1024);
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
	}
	
	public void inizializzaTabellaPersonale() {
		
		intestazioneColonne = new Vector<String>();
		
		intestazioneColonne.addElement("Matricola");
		intestazioneColonne.addElement("Cognome");
		intestazioneColonne.addElement("Nome");
		
		inizializzaListaPersonale();
		
	}
	
	public void inizializzaListaPersonale() {
		
		listaPersonale = new Vector<Vector<String>>();
		
		Iterator<Dipendente> i = ListaDipendenti.getListaDipendenti().iterator();
		while (i.hasNext()) {
			Dipendente d = i.next();
			if (d.getImpiantoDiAppartenenza().equals(impianto.getNomeImpianto())) {
				
				personale = new Vector<String>();
				
				personale.addElement(d.getMatricola());
				personale.addElement(d.getCognome());
				personale.addElement(d.getNome());
				
				listaPersonale.add(personale);
				
				personale = null;
			}
			
		}
		
		
	}
	
}
