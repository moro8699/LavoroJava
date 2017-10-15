package packageDipendenti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Principale;
import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class SetDipendenteSW extends JFrame {
	
	private static final long serialVersionUID = -8722179695766788480L;
	protected  final int R_NOME =0, R_COGNOME =1, R_IMPIANTO =2, R_ASSUNZ =3, R_RECAPITI =4;
	private int rowAttuale = 0;
	protected JLabel matricola, recapiti;
	protected static JLabel impianto;
	protected JButton ok, cancel, assegnaImpianto, modRecapiti;
	protected Dipendente d;
	private JTable datiDipendente;
	private Vector<String> nomeRiga, dipendente, header;
	private Vector<Vector<String>> dati;
	private DefaultTableModel modelloDatiDipendente;
	
	public SetDipendenteSW (Dipendente d){
		
		this.d = d;
		
		inizializzaDatiDipendente();
		
		modelloDatiDipendente = new DefaultTableModel() {

			private static final long serialVersionUID = 1798174918491824698L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        if (row == R_RECAPITI && column == 1) return false;		       
				if (column != 0) return true;
		        return false;
		    }		
		};
		
		modelloDatiDipendente.setDataVector(dati, header);
		datiDipendente = new JTable(modelloDatiDipendente);
		datiDipendente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(datiDipendente);
		
		rowAttuale = Principale.posizioneAttualeTable();
		
		JPanel centro = new JPanel();
		JPanel nord = new JPanel();
		JPanel sud = new JPanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(nord, BorderLayout.NORTH);
		getContentPane().add(centro, BorderLayout.CENTER);
		getContentPane().add(sud, BorderLayout.SOUTH);
		
		nord.add(new JLabel("DATI DIPENDENTE " + d.getMatricola()));
		centro.setLayout(new BorderLayout());
		centro.add(scroll, BorderLayout.CENTER);
		
		ok = new JButton("OK");
		ok.addActionListener(new Ok(d));
		cancel = new JButton("Cancel");
		cancel.addActionListener(new Cancel());
		modRecapiti = new JButton("Gestione Recapiti");
		modRecapiti.addActionListener(new Recapiti());
		
		sud.add(ok);
		sud.add(cancel);
		sud.add(modRecapiti);
		
		setSize(500,350);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
	}
	
	public void inizializzaDatiDipendente() {
		
		header = new Vector<String>();
		nomeRiga = new Vector<String>();
		dipendente = new Vector<String>();
		dati = new Vector<>();
		
		header.addElement("Attributo");
		header.addElement("Valore");
		
		nomeRiga.add(R_NOME, "Nome");
		dipendente.add(R_NOME, d.getNome());
		nomeRiga.add(R_COGNOME, "Cognome");
		dipendente.add(R_COGNOME, d.getCognome());
		nomeRiga.add(R_IMPIANTO, "Impianto");
		dipendente.add(R_IMPIANTO, d.labelImpianto());
		nomeRiga.add(R_ASSUNZ, "Assunzione");
		dipendente.add(R_ASSUNZ, d.getDataAssunzione().toString());
		nomeRiga.add(R_RECAPITI, "Recapiti");
		dipendente.add(R_RECAPITI, ElencoTelefonicoDipendenti.cercaTelefoni(d));
		
		for(int i =0; i<nomeRiga.size(); i++) {
			Vector<String> temp = new Vector<>();
			temp.addElement(nomeRiga.elementAt(i));
			temp.addElement(dipendente.elementAt(i));
			dati.addElement(temp);
		}
		
	}
	
	class ApriAssegnaImpianto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AssegnaImpianto(d);
		}
		
	}
	
	class AssegnaImpianto extends JFrame {
		
		protected JComboBox<String> listaImpianti;
		protected JButton ok;
		protected Dipendente d;
		private static final long serialVersionUID = -2149893864315749372L;
		
		public AssegnaImpianto(Dipendente d){
			
			listaImpianti = new JComboBox<String>(Impianti.modelLista());
			this.d = d;
			JPanel nord = new JPanel();
			nord.add(new JLabel("SELEZIONE IMPIANTO"));
			
			JPanel centro = new JPanel();
			centro.add(listaImpianti);
			
			JPanel sud = new JPanel();
			ok = new JButton("OK");
			ok.addActionListener(new Assegna());
			sud.add(ok);
			
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(nord, BorderLayout.NORTH);
			getContentPane().add(centro, BorderLayout.CENTER);
			getContentPane().add(sud, BorderLayout.SOUTH);
			
			setSize(350,200);		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
			setVisible(true);
		}
		
		class Assegna implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				Impianto impianto = new Impianto((String) listaImpianti.getSelectedItem());
				Iterator<Impianto> iterator = Impianti.getListaImpianti().iterator();
				while (iterator.hasNext()){
					Impianto temp = iterator.next();
					if (impianto.equals(temp)) {
						d.dissocia();
						temp.assegnaDipendente(d);
						SetDipendenteSW.impianto.setText(d.getImpiantoDiAppartenenza());
						ListaDipendenti.salvaLista(ListaDipendenti.getFileLista());
						Impianti.salvaLista(Impianti.getFileLista());
					}
				}
					setVisible(false);
			}
			
		}
	}
	
	
	
	class Ok implements ActionListener{
		
		Dipendente dipendente;
		public Ok(Dipendente dip) {
			dipendente = dip;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String nome = (String) datiDipendente.getValueAt(R_NOME, 1),
					cognome = (String) datiDipendente.getValueAt(R_COGNOME, 1);
			
			Dipendente d = new Dipendente(nome, cognome, dipendente.getMatricola());
			Iterator<Dipendente> it = ListaDipendenti.getListaDipendenti().iterator();
			while (it.hasNext()){
				Dipendente dip = it.next();
				if (dip.equals(d)){
					dip.setNome(nome);
					dip.setCognome(cognome);
					Principale.getModelloTable().setValueAt
						(cognome + " " + nome , rowAttuale, Principale.COLONNA_COGNOME_NOME);
					Principale.getModelloTable().setValueAt
						(dip.getImpiantoDiAppartenenza() , rowAttuale, Principale.COLONNA_IMPIANTO);
				}
			}
			ListaDipendenti.salvaLista(ListaDipendenti.getFileLista());
			setVisible(false);
		}
		
	}
	
	class Cancel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
		
	}

	class Recapiti implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new GestioneRecapiti(d);
			
		}
		
	}
	
}

