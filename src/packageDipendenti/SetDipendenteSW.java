package packageDipendenti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.michaelbaranov.microba.calendar.DatePickerCellEditor;

import eccezioni.ElementoGiaEsistente;
import eccezioni.ElementoNonTrovato;
import eccezioni.ErroreTrasferimento;
import eccezioni.InserimentoNonCorretto;
import generici.Controllo;
import main.Principale;
import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class SetDipendenteSW extends JFrame {
	
	private static final long serialVersionUID = -8722179695766788480L;
	protected final static int NOME =0, COGNOME =1, IMPIANTO =2, NASCITA =3, ASSUNZ =4, RECAPITI =5;
	private final static int DESTINAZIONE =0, DAL =1, AL = 2;
	private int rowAttuale = 0;
	protected static JLabel impianto;
	protected JButton ok, cancel, applica, assegnaImpianto, rimuoviTrasferimento, modificaTrasferimento, modRecapiti;
	private static Dipendente d;
	protected static JTable datiDipendente, datiTrasferimenti;
	protected DatePicker picker;
	protected DatiDipendenteTableModel modelDatiDipendente;
	protected DatiTrasferimentiTableModel modelTrasferimenti;
	private JTabbedPane tabbedPane;
	protected Date dataNascitaDate, dataAssunzioneDate;
	
	public SetDipendenteSW (Dipendente d){
		
		super("DATI DIPENDENTE " + d.getMatricola());
		
		SetDipendenteSW.d = d;
		dataAssunzioneDate = Controllo.localDateToDate(d.getDataAssunzione());
		dataNascitaDate = Controllo.localDateToDate(d.getDataDiNascita()); 
		rowAttuale = Principale.posizioneAttualeTable();
		
		tabbedPane = new JTabbedPane();		
		tabbedPane.addTab("Dati Dipendente", new DatiDipendente());
		tabbedPane.addTab("Trasferimenti", new DatiTrasferimenti());
		add(tabbedPane);
		
		setSize(500,350);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
	}
	
	public static void aggiornaRecapiti(){
		datiDipendente.setValueAt(ElencoTelefonicoDipendenti.cercaTelefoni(d), 0, RECAPITI);
	}
		
	private void setDataPickerColumn(JTable tabella, TableColumn colonnaDaSettare, Date valoreIniziale){
		
		if (valoreIniziale == null) picker = new DatePicker();
		else picker = new DatePicker(valoreIniziale);
		colonnaDaSettare.setCellEditor(new DatePickerCellEditor(picker));
		
		picker.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(picker.getDate());//print the current date   
            }
        });
	}

	private void aggiornaImpiantoAppartenenza(Dipendente d){
		modelDatiDipendente.setValueAt(Controllo.cercaImpiantoDiAppartenenza(d) , 0, IMPIANTO);
		Principale.aggiornaImpiantiModel();
	}
	
	private void inserisciDataAssunzione (Dipendente d, Date data){
		
		try {
			inserisciDataAssunzione(d, Controllo.DateToLocalDate(data).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}	
	}
	
	private void inserisciDataAssunzione (Dipendente d, String data){
		if (Controllo.verificaDataInserita(data)){
			String[] dataSplit = data.split("-");
			try {
				d.setDataAssunzione(LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2])));
			} catch (DateTimeException e) {
				JOptionPane.showMessageDialog(null, e.toString());
			} catch (NullPointerException e) {
				d.setDataAssunzione(LocalDate.MIN);
			}
		}
	}
	
	private void inserisciDataDiNascita (Dipendente d, Date data){
		
		try {
			inserisciDataDiNascita(d, Controllo.DateToLocalDate(data).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}	
	}

	private void inserisciDataDiNascita (Dipendente d, String data){
		if (Controllo.verificaDataInserita(data)){
			String[] dataSplit = data.split("-");
			try {
				d.setDataDiNascita(LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2])));			
			} catch (DateTimeException e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
			ListaDipendenti.salvaElencoDipendenti();
		}
		
	}
	
	private Vector<String> TrasferimentoToVector (Trasferimento t){
		Vector<String> trasf = new Vector<String>();
		String al = t.getAl() == LocalDate.MAX ? "" : t.getAl().toString();
		trasf.add(t.getImpianto().toString());
		trasf.add(t.getDal().toString());
		trasf.add(al);
		
		return trasf;
		
	}
	
	private Trasferimento trasferimentodamodel(int riga){
		Trasferimento t;
		
		try {
			t = new Trasferimento(
					d, 
					Impianti.getImpiantoSelezionato((String) modelTrasferimenti.getValueAt(riga,DESTINAZIONE)), 
					LocalDate.parse((String) modelTrasferimenti.getValueAt(riga,DAL)),
					LocalDate.parse((String) modelTrasferimenti.getValueAt(riga,AL)));
			
		} catch(DateTimeParseException exc){
			t = new Trasferimento(
					d, 
					Impianti.getImpiantoSelezionato((String) modelTrasferimenti.getValueAt(riga,DESTINAZIONE)), 
					LocalDate.parse((String) modelTrasferimenti.getValueAt(riga,DAL)));	
		} 
		
		return t;
	}

	class DatiDipendente extends JPanel{

		private static final long serialVersionUID = 1L;

		public DatiDipendente() {
			
			modelDatiDipendente = new DatiDipendenteTableModel();
			datiDipendente = new JTable(modelDatiDipendente);
			setDataPickerColumn(datiDipendente, datiDipendente.getColumnModel().getColumn(NASCITA), dataNascitaDate);
			setDataPickerColumn(datiDipendente, datiDipendente.getColumnModel().getColumn(ASSUNZ), dataAssunzioneDate);		
			datiDipendente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			JPanel centro = new JPanel();
			JPanel sud = new JPanel();	
			
			setLayout(new BorderLayout());
			add(centro, BorderLayout.CENTER);
			add(sud, BorderLayout.SOUTH);
						
			JScrollPane scroll = new JScrollPane(datiDipendente);	
			centro.setLayout(new BorderLayout());
			centro.add(scroll, BorderLayout.CENTER);
			
			ok = new JButton("OK");
			ok.addActionListener(new Salva(d, true));
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			applica = new JButton("Applica");
			applica.addActionListener(new Salva(d, false));
			modRecapiti = new JButton("Gestione Recapiti");
			modRecapiti.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {new GestioneRecapiti(d);}
			});
					
			sud.add(ok);
			sud.add(cancel);
			sud.add(applica);
			sud.add(modRecapiti);
			
		}
	}
	
	class DatiTrasferimenti extends JPanel{
		
		private static final long serialVersionUID = 1L;

		public DatiTrasferimenti(){
			
			modelTrasferimenti = new DatiTrasferimentiTableModel();
			datiTrasferimenti = new JTable(modelTrasferimenti);
			JScrollPane scrollpane = new JScrollPane(datiTrasferimenti);
			
			setLayout(new BorderLayout());
			
			JPanel sud = new JPanel();
			assegnaImpianto = new JButton("Nuovo Trasferimento");
			assegnaImpianto.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Controllo.verificaDataAssunzione(d);
						new GestioneTrasferimento(d);
					} catch(ErroreTrasferimento exc) {JOptionPane.showMessageDialog(null, exc.toString());}					
				}
			});
			modificaTrasferimento = new JButton("Modifica Trasferimento");
			modificaTrasferimento.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(datiTrasferimenti.getSelectedRow()>=0)
						new GestioneTrasferimento(trasferimentodamodel(datiTrasferimenti.getSelectedRow()), datiTrasferimenti.getSelectedRow());
				}
			});
			
			rimuoviTrasferimento = new JButton("Rimuovi");
			rimuoviTrasferimento.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {				
					try{
						ElencoTrasferimenti.eliminaTrasferimento(trasferimentodamodel(datiTrasferimenti.getSelectedRow()));
						modelTrasferimenti.removeRow(datiTrasferimenti.getSelectedRow());
						ElencoTrasferimenti.salvaElencoTrasferimenti();
					}catch (ElementoNonTrovato exc) {JOptionPane.showMessageDialog(null, exc.toString());}
				}
			});
			sud.add(assegnaImpianto);
			sud.add(modificaTrasferimento);
			sud.add(rimuoviTrasferimento);
			add(scrollpane, BorderLayout.CENTER);
			add(sud, BorderLayout.SOUTH);

		}
		
	}	
	
	class DatiTrasferimentiTableModel extends DefaultTableModel{

		private static final long serialVersionUID = 1L;
		

		private Vector<String> header = caricaHeader();
		private Vector<Vector<String>> datiTrasferimenti = caricaDatiTrasferimenti();
		
		public DatiTrasferimentiTableModel() {
			super.setDataVector(datiTrasferimenti, header);
		}
		
		@Override
        public boolean isCellEditable(int row, int col) {
	        return false;
        }
		
		private Vector<String> caricaHeader (){
			Vector<String> header = new Vector<>();
			header.addElement("Impinato");
			header.addElement("Dal");
			header.addElement("Al");
			
			return header;
		}
		private Vector<Vector<String>> caricaDatiTrasferimenti(){
			Vector<Vector<String>> trasferimentiDipendente = new Vector<>();
			Vector<String> trasferimento;
			for(int i=0; i<ElencoTrasferimenti.getSizeElenco(); i++){
				Trasferimento t = ElencoTrasferimenti.getElemento(i);
				if(t.getDipendente().equals(d)){
					trasferimento = new Vector<>();
					trasferimento.addElement(t.getImpianto().toString());
					trasferimento.addElement(t.getDal().toString());
					if (t.getAl().isEqual(LocalDate.MAX)) 
						trasferimento.addElement("");
					else trasferimento.addElement(t.getAl().toString()); 
					trasferimentiDipendente.add(trasferimento);
				}

			}
			
			return trasferimentiDipendente;
		}
		
	}
	
	class DatiDipendenteTableModel extends AbstractTableModel {
       
		private static final long serialVersionUID = -6720720544764467716L;
		
			
		
		private String[] columnNames = {"Nome",
                                        "Cognome",
                                        "Impianto",
                                        "Data di Nascita",
                                        "Assunzione", 
                                        "recapiti"};
		
		private Object[][] data = {
				{d.getNome(), 
					d.getCognome(),
					Controllo.cercaImpiantoDiAppartenenza(d), 
					dataNascitaDate,
					dataAssunzioneDate,
					ElencoTelefonicoDipendenti.cercaTelefoni(d)}};
 
        public int getColumnCount() {
            return columnNames.length;
        }

		public int getRowCount() {
            return data.length;
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c){
        	try{
        		return getValueAt(0, c).getClass();
        	} catch (NullPointerException e){
        		return Object.class;
        	}
			
        }
 
        public boolean isCellEditable(int row, int col) {

			if (row == 0 && col == IMPIANTO) return false;
	        if (row == 0 && col == RECAPITI) return false;
	        return true;
        }
 
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
	}

	class GestioneTrasferimento extends JFrame {
		
		
		private JComboBox<String> listaImpianti;
		private JButton ok;
		private Dipendente d;
		private DatePicker dal, al;
		private JCheckBox chKAl;
		private static final long serialVersionUID = -2149893864315749372L;

		public GestioneTrasferimento(Dipendente d){
			listaImpianti = new JComboBox<String>(modelListaImpianti());
			this.d = d;
			chKAl = new JCheckBox(" Al ");

			JPanel nord = new JPanel();
			nord.add(new JLabel("SELEZIONE IMPIANTO"));
			
			JPanel centro = new JPanel(), 
					centroNord = new JPanel(), 
					centroCentro = new JPanel(),
					centroSud = new JPanel();
			
			centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
			
			centroNord.add(listaImpianti);
			
			dal = new DatePicker();
			centroCentro.add(new JLabel("Dal:   "));
			centroCentro.add(dal);
			
			chKAl.setSelected(false);
			al = new DatePicker();
			al.setEnabled(false);
			chKAl.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chKAl.isSelected()){
						al.setEnabled(true);
					} else {
						al.setEnabled(false);
					}
				}
			});
			
			centroSud.add(chKAl);
			centroSud.add(al);
			
			centro.add(centroNord);
			centro.add(centroCentro);
			centro.add(centroSud);
			JPanel sud = new JPanel();
			
			ok = new JButton("OK");			
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new Inserisci(
							Impianti.getImpiantoSelezionato(listaImpianti.getItemAt(listaImpianti.getSelectedIndex())),
							Controllo.DateToLocalDate(dal.getDate()),
							getAl());
				}
			});
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
		
		public GestioneTrasferimento(Trasferimento t, int indiceLista){
			
			chKAl = new JCheckBox(" Al ");
			JPanel nord = new JPanel();
			nord.add(new JLabel("MODIFICA TRASFERIMENTO"));
			
			JPanel centro = new JPanel(), 
					centroNord = new JPanel(), 
					centroCentro = new JPanel(),
					centroSud = new JPanel();
			
			centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
			
			centroNord.add(new JLabel("Impianto: "+ t.getImpianto().toString()));
			
			dal = new DatePicker(Controllo.localDateToDate(t.getDal()));
			centroCentro.add(new JLabel("Dal:   "));
			centroCentro.add(dal);
			
			if (t.getAl().isEqual(LocalDate.MAX)){
				chKAl.setSelected(false);
				al = new DatePicker();
				al.setEnabled(false);
			} else {
				chKAl.setSelected(true);
				al = new DatePicker(Controllo.localDateToDate(t.getAl()));
				al.setEnabled(true);
			}
			chKAl.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chKAl.isSelected()){
						al.setEnabled(true);
					} else {
						al.setEnabled(false);
					}
				}
			});
			
			centroSud.add(chKAl);
			centroSud.add(al);
			
			centro.add(centroNord);
			centro.add(centroCentro);
			centro.add(centroSud);
			JPanel sud = new JPanel();
			
			ok = new JButton("OK");			
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						
					new Modifica(t,
							new Trasferimento(t.getDipendente(), t.getImpianto(),
							Controllo.DateToLocalDate(dal.getDate()),
							getAl()), indiceLista);
				}
			});
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
		
		private LocalDate getAl(){
			if (chKAl.isSelected()) return Controllo.DateToLocalDate(al.getDate());
			return LocalDate.MAX;
		}
		
		private String[] modelListaImpianti() {
			
			String[] lista = new String[Impianti.getSize()];
			for (int c = 0; c <Impianti.getSize(); c++) {
				lista[c] = Impianti.getImpiantoSelezionato(c).toString();
			}

			return lista;
		}
		
		class Modifica {
			public Modifica(Trasferimento daModificare, Trasferimento modificato, int indice) {
				try {
					ElencoTrasferimenti.modificaTrasferimento(daModificare, modificato);
					String alModificato = (modificato.getAl().isEqual(LocalDate.MAX)) ? "" : modificato.getAl().toString();
					modelTrasferimenti.setValueAt((String) modificato.getDal().toString(), indice, DAL);
					modelTrasferimenti.setValueAt((String) alModificato, indice, AL);
					aggiornaImpiantoAppartenenza(modificato.getDipendente());
					ElencoTrasferimenti.salvaElencoTrasferimenti();
					setVisible(false);
				} catch (ErroreTrasferimento e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
			}
		}
		
		class Inserisci {
			
			public Inserisci(Impianto i, LocalDate dal, LocalDate al){
				Trasferimento t = new Trasferimento(d, i, dal, al);	
				if (al.isEqual(LocalDate.MAX)) t= new Trasferimento(d, i, dal);
							
				try {
					ElencoTrasferimenti.AggiungiTrasferimento(t);
					modelTrasferimenti.addRow(TrasferimentoToVector(t));
					aggiornaImpiantoAppartenenza(d);
					ListaDipendenti.salvaElencoDipendenti();
					setVisible(false);							
				} catch (ElementoGiaEsistente exc) {
					JOptionPane.showMessageDialog(null, exc.toString());
				} catch (InserimentoNonCorretto exc) {
					JOptionPane.showMessageDialog(null, "La data di Inizio deve essere precedente a quella di fine");
				} catch (ErroreTrasferimento exc) {
					JOptionPane.showMessageDialog(null, exc.toString());
				} 
				
			}
			
		}
	}
		
	class Salva implements ActionListener{
		
		Dipendente dipendente;
		boolean chiudiFinestra;
		
		public Salva (Dipendente dip, boolean chiudiFinestra) {
			dipendente = dip;
			this.chiudiFinestra = chiudiFinestra;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String nome = (String) datiDipendente.getValueAt(0, NOME),
					cognome = (String) datiDipendente.getValueAt(0, COGNOME);
					
			Date dataNascita = (Date) datiDipendente.getValueAt(0, NASCITA),
					dataAssunzione = (Date) datiDipendente.getValueAt(0, ASSUNZ);
			
			Dipendente d = new Dipendente(nome, cognome, dipendente.getMatricola());
			Iterator<Dipendente> it = ListaDipendenti.getListaDipendenti().iterator();
			while (it.hasNext()){
				Dipendente dip = it.next();
				if (dip.equals(d)){
					dip.setNome(nome);
					dip.setCognome(cognome);
					if (dataAssunzione != null) inserisciDataAssunzione(dip, dataAssunzione);
					if (dataNascita != null) inserisciDataDiNascita(dip, dataNascita);
					Principale.getModelloTable().setValueAt
						(cognome + " " + nome , rowAttuale, Principale.COLONNA_COGNOME_NOME);
				}
			}
			ListaDipendenti.salvaElencoDipendenti();
			if (chiudiFinestra) setVisible(false);
		}
		
	}
	
}

