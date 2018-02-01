package packageImpianti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import packageDipendenti.ElencoTrasferimenti;
import packageDipendenti.Trasferimento;
import turni.GestioneTurni;

public class GestioneImpianto extends JFrame {

	private static final long serialVersionUID = 1573062133006083688L;
	
	private JToolBar strumenti;
	private JButton turni;
	private JPanel nord, centro, centroNord, centroNordOvest;
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
		getContentPane().setLayout(new BorderLayout());
		
		inizializzaTabellaPersonale();
		
		model.setDataVector(listaPersonale, intestazioneColonne);
		turno = new JTable(model);
		
		nord = new JPanel();
		nord.setLayout(new BorderLayout());
		strumenti = new JToolBar();
		strumenti.setFloatable(false);
		turni = new JButton("Turni");
		turni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GestioneTurni(i);
				
			}
		});
		strumenti.add(turni);
		nord.add(strumenti);
		
		centro = new JPanel();
		centro.setLayout(new GridLayout(2, 0));
		centroNord = new JPanel();
		centroNord.setLayout(new GridLayout(0,2));

		centroNordOvest = new JPanel();
		JScrollPane scrollPane = new JScrollPane(turno);
		centroNordOvest.add(scrollPane);
		
		centroNord.add(centroNordOvest);
		
		centro.add(centroNord);
		
		getContentPane().add(nord, BorderLayout.NORTH);
		getContentPane().add(centro, BorderLayout.CENTER);
		
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
		
		Iterator<Trasferimento>i = ElencoTrasferimenti.getElencoTrasferimenti().iterator();
		
		listaPersonale = new Vector<Vector<String>>();
		
		while(i.hasNext()) {
			Trasferimento t = i.next();
			if(t.getImpianto().equals(impianto)) {
				if(t.getAl() == null || 
						t.getAl().isAfter(LocalDate.now()) || 
						t.getAl().isEqual(LocalDate.now())) {
					
					personale = new Vector<String>();
					
					personale.addElement(t.getDipendente().getMatricola());
					personale.addElement(t.getDipendente().getCognome());
					personale.addElement(t.getDipendente().getNome());
					
					listaPersonale.add(personale);
					
					personale = null;
				}
			}
		}
		/*listaPersonale = new Vector<Vector<String>>();
		
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
			
		}*/
		
		
	}
	
}
