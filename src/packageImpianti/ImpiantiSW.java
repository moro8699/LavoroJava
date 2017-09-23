package packageImpianti;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;

public class ImpiantiSW extends JFrame {

	private static final long serialVersionUID = 1982155242792968974L;
	protected JButton aggiungiImpianto, rimuoviImpianto, apriImpianto;
	protected JList<String> impianti;
	private static DefaultListModel<String> modelImpianti;

	public ImpiantiSW(){
		
		modelImpianti = Impianti.arrListaImp();
		impianti = new JList<>(modelImpianti);
		
		JPanel nord = new JPanel();
		nord.add(new JLabel("GESTIONE IMPIANTI"));
		
		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(0, 2));
		JPanel centroOvest = new JPanel();
		centroOvest.setLayout(new BorderLayout());
		centroOvest.add(impianti);
		centro.add(centroOvest);		
		
		JPanel centroEst = new JPanel();
		centroEst.setLayout(new BoxLayout(centroEst, BoxLayout.Y_AXIS));
		JPanel aggiungiRimuovi = new JPanel();
		aggiungiImpianto = new JButton("+");
		aggiungiImpianto.addActionListener(new AggiungiImpianto());
		aggiungiRimuovi.add(aggiungiImpianto);
		rimuoviImpianto = new JButton(" - ");
		rimuoviImpianto.addActionListener(new RimuoviImpianto());		
		aggiungiRimuovi.add(rimuoviImpianto);			
		centroEst.add(aggiungiRimuovi);
		//aggiungiRimuovi.setAlignmentX(Component.CENTER_ALIGNMENT);
		apriImpianto = new JButton("Apri Gestione Impianto");
		apriImpianto.addActionListener(new ApriGestioneImpianto());
		JPanel jPApriImpianto = new JPanel();
		jPApriImpianto.add(apriImpianto);
		centroEst.add(jPApriImpianto);
		//jPApriImpianto.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		centro.add(centroEst);		
		
		getContentPane().setLayout(new BorderLayout());		
		getContentPane().add(nord, BorderLayout.NORTH);
		getContentPane().add(centro, BorderLayout.CENTER);
		
		setSize(350,200);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
	}
	
	public static DefaultListModel<String> getModelImpianti() {
		return modelImpianti;
	}
	
	class ApriGestioneImpianto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String impianto = impianti.getSelectedValue();
			if (impianto != null) new GestioneImpianto(Impianti.getImpiantoDaModel(impianto));
		}
		
	}
	class RimuoviImpianto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (impianti.getSelectedValue() != ""){
				Impianto imp = new Impianto(impianti.getSelectedValue());
				int conferma = JOptionPane.showConfirmDialog(null, 
						"Attenzione!! Tutti i Dipendenti Associati all'impianto selezionato saranno Dissociati.\nContinuare?",
						"CONFERMA CANCELLAZIONE", JOptionPane.YES_NO_OPTION);
				if (conferma == JOptionPane.YES_OPTION){
					if (Impianti.rimuoviImpianto(imp)) {
						Iterator<Dipendente> iter = ListaDipendenti.getListaDipendenti().iterator();
						while (iter.hasNext()){
							Dipendente d = iter.next();
							if (imp.getNomeImpianto().equals(d.getImpiantoDiAppartenenza())) ListaDipendenti.dissociaImpianto(d);
						}
						for (int i= 0 ; i< modelImpianti.size(); i++){
							if (imp.getNomeImpianto().equals(modelImpianti.getElementAt(i))){
								modelImpianti.remove(impianti.getSelectedIndex());
								break;
							}
						}
					}
				}
			}
			pack();
		}
		
	}
	
	class AggiungiImpianto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AddImpianto();
		}
		
	}
	
	public class AddImpianto extends JFrame {

		private static final long serialVersionUID = -7450113793026077011L;
		protected JTextField nuovoImpianto;
		protected JButton ok, cancel;
		
		public AddImpianto(){
		
			getContentPane().setLayout(new BorderLayout());
			
			JPanel nord = new JPanel();
			nord.add(new JLabel("INSERIMENTO NUOVO IMPIANTO"));
			getContentPane().add(nord, BorderLayout.NORTH);
			
			JPanel centro = new JPanel();
			nuovoImpianto = new JTextField(15);
			centro.add(nuovoImpianto);
			getContentPane().add(centro, BorderLayout.CENTER);
			
			JPanel sud = new JPanel();
			ok = new JButton("OK");
			ok.addActionListener(new NewImpianto());
			cancel = new JButton("Cancel");
			cancel.addActionListener(new Cancel());
			sud.add(ok);
			sud.add(cancel);
			getContentPane().add(sud, BorderLayout.SOUTH);	
			
			setSize(250,150);		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
			setVisible(true);
			
		}
		
		class NewImpianto implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				Impianto i = new Impianto(nuovoImpianto.getText());
				if ((i.getNomeImpianto().isEmpty())) { JOptionPane.showMessageDialog(null, "Il Campo non può essere Vuoto"); }
				else if (Impianti.aggiungiImpianto(i)) {	
						modelImpianti.addElement(nuovoImpianto.getText());
						Impianti.salvaLista(Impianti.getFileLista());
						setVisible(false);					
				} else {JOptionPane.showMessageDialog(null, "Impianto già presente");}
			}
			
		}
		
		class Cancel implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		}
	}
}
