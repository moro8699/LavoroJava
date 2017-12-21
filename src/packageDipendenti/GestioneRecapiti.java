package packageDipendenti;

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

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;
import Eccezioni.InserimentoNonCorretto;

public class GestioneRecapiti extends JFrame {

	private static final long serialVersionUID = 7513352362957475301L;
	
	protected DefaultListModel<String> model;
	protected JList<String> listaRecapiti;
	protected JPanel nord, centro, sud;
	protected JButton addTel, remTel;
	protected Dipendente d;
	
	public GestioneRecapiti(Dipendente d){
		
		this.d = d;
		
		nord = new JPanel();
		nord.add(new JLabel("GESTIONIE RECAPITI TELEFONICI"));
		
		centro = new JPanel();
		centro.setLayout(new GridLayout(0, 2));
		model = new DefaultListModel<String>();
		listaRecapiti = new JList<>(model);
		JPanel centroEst = new JPanel();
		centroEst.add(listaRecapiti);
		centro.add(centroEst);
		
		sud = new JPanel();
		addTel = new JButton(" + ");
		addTel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new addTelefono();
				setVisible(false);
			}
		});
		sud.add(addTel);
		remTel = new JButton(" - ");
		remTel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (listaRecapiti.getSelectedValue()!= null){
					Telefono t = modelToTelefono(listaRecapiti.getSelectedValue(), d);
					try {
						ElencoTelefonicoDipendenti.rimuoviNumeroTelefonico(t);
						SetDipendenteSW.aggiornaRecapiti();
						model.remove(listaRecapiti.getSelectedIndex());
					} catch (ElementoNonTrovato e) {
						JOptionPane.showMessageDialog(null, e.toString());
					}
				}
			}
		});
		sud.add(remTel);
		
		getContentPane().add(nord, BorderLayout.NORTH);
		getContentPane().add(centro, BorderLayout.CENTER);
		getContentPane().add(sud, BorderLayout.SOUTH);
		
		setSize(350,200);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		caricaModel();
		setVisible(true);
	}
	
	public void caricaModel(){
		
		Iterator<Telefono> iterator = ElencoTelefonicoDipendenti.getElencoTelefonico().iterator();
		while (iterator.hasNext()){
			Telefono t = iterator.next();
			if (t.getDipendente().equals(d)) model.addElement(t.toString());
		}
	}
	
	public Telefono modelToTelefono(String modelSelezionato, Dipendente d){
		String[] splitModel = modelSelezionato.split(" ");
		Telefono telefono = new Telefono(d, splitModel[0]);
		return telefono;
	}
	
	class addTelefono extends JFrame {

		private static final long serialVersionUID = -2776781255910024456L;
		private JButton ok;
		protected JTextField numero, identificativo;
		
		public addTelefono() {
			
			numero = new JTextField(20);
			identificativo = new JTextField(20);
			
			JPanel nord = new JPanel();
			nord.add(new JLabel("NUOVO RECAPITO PER : " + d.getMatricola()));
			
			JPanel centro = new JPanel();
			centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
			
			JPanel numeroTelefonico = new JPanel();
			numeroTelefonico.add(new JLabel("RECAPITO :"));
			numeroTelefonico.add(numero);
			centro.add(numeroTelefonico);
			
			JPanel tipoDiRecapito = new JPanel();
			tipoDiRecapito.add(new JLabel("IDENTIFICATIVO :"));
			tipoDiRecapito.add(identificativo);
			centro.add(tipoDiRecapito);
			
			JPanel sud = new JPanel();
			ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						ElencoTelefonicoDipendenti.aggiungiNumeroTelefonico(new Telefono(d, numero.getText(), identificativo.getText()));
						ElencoTelefonicoDipendenti.salvaElenco();
						SetDipendenteSW.aggiornaRecapiti();
						setVisible(false);					
					} catch (ElementoGiaEsistente exc) {						
						JOptionPane.showMessageDialog(null, exc.toString());
					} catch (InserimentoNonCorretto exc) {						
						JOptionPane.showMessageDialog(null, exc.toString());
					}
				}
				
			});			
			sud.add(ok);
			
			getContentPane().add(nord, BorderLayout.NORTH);
			getContentPane().add(centro, BorderLayout.CENTER);
			getContentPane().add(sud, BorderLayout.SOUTH);
			setSize(350,200);		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
			setVisible(true);
			
		}
		
	}

}
