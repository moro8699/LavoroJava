package packageDipendenti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Principale;
import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class SetDipendenteSW extends JFrame {
	
	private static final long serialVersionUID = -8722179695766788480L;
	
	protected JTextField nome, cognome;
	protected JLabel matricola, recapiti;
	protected static JLabel impianto;
	protected JButton ok, cancel, assegnaImpianto, modRecapiti;
	protected Dipendente d;
	
	public SetDipendenteSW (Dipendente d){
		
		this.d = d;
		nome = new JTextField(20);
		nome.setText(d.getNome());
		cognome = new JTextField(20);
		cognome.setText(d.getCognome());
		matricola = new JLabel(d.getMatricola());
		impianto = new JLabel(d.labelImpianto());
		recapiti = new JLabel(ElencoTelefonicoDipendenti.cercaTelefoni(d));
		
		JPanel centro = new JPanel();
		JPanel nord = new JPanel();
		JPanel sud = new JPanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(nord, BorderLayout.NORTH);
		getContentPane().add(centro, BorderLayout.CENTER);
		getContentPane().add(sud, BorderLayout.SOUTH);
		
		nord.add(new JLabel("DATI DIPENDENTE " + d.getMatricola()));
		
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		JPanel pnlCognome = new JPanel();
		pnlCognome.add(new JLabel("COGNOME : "));	
		pnlCognome.add(cognome);	
		centro.add(pnlCognome);
		JPanel pnlNome = new JPanel();
		pnlNome.add(new JLabel("NOME : "));	
		pnlNome.add(nome);
		centro.add(pnlNome);
		JPanel pnlImpianto = new JPanel();
		assegnaImpianto = new JButton("Assegna");
		assegnaImpianto.addActionListener(new ApriAssegnaImpianto() );
		pnlImpianto.add(new JLabel("IMPIANTO : "));
		pnlImpianto.add(impianto);
		pnlImpianto.add(assegnaImpianto);
		centro.add(pnlImpianto);
		JPanel pnlRecapiti = new JPanel();
		pnlRecapiti.add(new JLabel("RECAPITI TELEFONICI : "));
		pnlRecapiti.add(recapiti);
		modRecapiti = new JButton("Gestisci Recapiti");
		modRecapiti.addActionListener(new Recapiti());
		pnlRecapiti.add(modRecapiti);
		centro.add(pnlRecapiti);
		
		ok = new JButton("OK");
		ok.addActionListener(new Ok());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new Cancel());
		sud.add(ok);
		sud.add(cancel);
		
		setSize(500,350);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
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

		@Override
		public void actionPerformed(ActionEvent e) {
			Dipendente d = new Dipendente(nome.getText(), cognome.getText(), matricola.getText());
			Iterator<Dipendente> it = ListaDipendenti.getListaDipendenti().iterator();
			while (it.hasNext()){
				Dipendente dip = it.next();
				if (dip.equals(d)){
					dip.setNome(nome.getText());
					dip.setCognome(cognome.getText());

					for (int i =0; i< Principale.getModelDip().size(); i++){
						Dipendente temp = Principale.modelToDipendente(Principale.getModelDip().getElementAt(i));
						if (temp.equals(d)){
							Principale.getModelDip().remove(i);
							Principale.getModelDip().addElement(d.toString());
							break;
						}
						
					}
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

