package packageDipendenti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.michaelbaranov.microba.calendar.DatePickerCellEditor;

import Generici.Controllo;
import main.Principale;
import packageImpianti.Impianti;
import packageImpianti.Impianto;


public class SetDipendenteSW extends JFrame {
	
	private static final long serialVersionUID = -8722179695766788480L;
	protected final static int NOME =0, COGNOME =1, IMPIANTO =2, NASCITA =3, ASSUNZ =4, RECAPITI =5;
	private int rowAttuale = 0;
	protected static JLabel impianto;
	protected JButton ok, cancel, assegnaImpianto, modRecapiti;
	private static Dipendente d;
	protected static JTable datiDipendente;
	private DatePicker picker;
	private SetDipendenteTableModel model;
	Date dataNascitaDate, dataAssunzioneDate;
	
	public SetDipendenteSW (Dipendente d){
		
		SetDipendenteSW.d = d;
		dataAssunzioneDate = Controllo.localDateToDate(d.getDataAssunzione());
		dataNascitaDate = Controllo.localDateToDate(d.getDataDiNascita()); 
		model = new SetDipendenteTableModel();
		datiDipendente = new JTable(model);
		setDataPickerColumn(datiDipendente, datiDipendente.getColumnModel().getColumn(NASCITA), dataNascitaDate);
		setDataPickerColumn(datiDipendente, datiDipendente.getColumnModel().getColumn(ASSUNZ), dataAssunzioneDate);		
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
		assegnaImpianto = new JButton("Assegnazione Impianto");
		assegnaImpianto.addActionListener(new ApriAssegnaImpianto());
				
		sud.add(ok);
		sud.add(cancel);
		sud.add(modRecapiti);
		sud.add(assegnaImpianto);
		
		setSize(500,350);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
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
	
	public String dataDiNascitaToString(Dipendente d) {
		if (d.getDataDiNascita() == null) return "";
		return d.getDataDiNascita().toString();
	}
	
	public String dataAssunzioneToString(Dipendente d){
		if (d.getDataAssunzione() == null) return "";
		return d.getDataAssunzione().toString();
	}
	
	public static void aggiornaRecapiti(){
		datiDipendente.setValueAt(ElencoTelefonicoDipendenti.cercaTelefoni(d), 0, RECAPITI);
	}
	
	public void inserisciDataAssunzione (Dipendente d, Date data){
		
		try {
			inserisciDataAssunzione(d, Controllo.DateToLocalDate(data).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}	
	}
	
	public void inserisciDataAssunzione (Dipendente d, String data){
		if (Controllo.verificaDataInserita(data)){
			String[] dataSplit = data.split("-");
			try {
				d.setDataAssunzione(LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2])));
			} catch (DateTimeException e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
	}
	
	public void inserisciDataDiNascita (Dipendente d, Date data){
		
		try {
			inserisciDataDiNascita(d, Controllo.DateToLocalDate(data).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}	
	}
	
	public void inserisciDataDiNascita (Dipendente d, String data){
		if (Controllo.verificaDataInserita(data)){
			String[] dataSplit = data.split("-");
			try {
				d.setDataDiNascita(LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2])));			
			} catch (DateTimeException e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
			ListaDipendenti.salvaLista(ListaDipendenti.getFileLista());
		}
		
	}
	
	class SetDipendenteTableModel extends AbstractTableModel {
       
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
					d.getImpiantoDiAppartenenza(), 
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
				Impianto nuovoImpianto = Impianti.getImpiantoSelezionato(listaImpianti.getItemAt(listaImpianti.getSelectedIndex()));		
				Impianto impiantoAttuale = d.getImpiantoDiAppartenenza()!= "" ? Impianti.getImpiantoSelezionato(d.getImpiantoDiAppartenenza()) : null;
				if (impiantoAttuale != null) impiantoAttuale.rimuoviDipendente(d);
				nuovoImpianto.assegnaDipendente(d);
				model.setValueAt(nuovoImpianto.getNomeImpianto(), 0, IMPIANTO);
				ListaDipendenti.salvaLista(ListaDipendenti.getFileLista());
				Impianti.salvaLista(Impianti.getFileLista());
				
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
					if (dataAssunzione.toString() !="") inserisciDataAssunzione(dip, dataAssunzione);
					if (dataNascita.toString() !="") inserisciDataDiNascita(dip, dataNascita);
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
	
	/*Testing
		class InserisciData implements MouseListener{
			@Override
			public void mouseClicked(MouseEvent e) {				
				if ((datiDipendente.getSelectedRow() == 0) &&
						(datiDipendente.getSelectedColumn() == NASCITA)){										
					//datiDipendente.setValueAt(picker.getDate(), R_NASCITA, 1);	
					framePicker.setSize(200,200);
					framePicker.setVisible(true);
				}				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}*/
	
}

