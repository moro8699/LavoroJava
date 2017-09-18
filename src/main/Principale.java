package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import packageDipendenti.Dipendente;
import packageDipendenti.ElencoTelefonicoDipendenti;
import packageDipendenti.ListaDipendenti;
import packageDipendenti.SetDipendenteSW;
import packageImpianti.ImpiantiSW;

public class Principale extends JFrame {

	private static final long serialVersionUID = -7652191700375937203L;
	
	protected JMenuBar barradeiMenu;
	protected JToolBar toolbarDip;
	protected JMenu file, modifica, impianti;
	protected JMenuItem aggiungiImpianto;
	protected JButton addDipendente, remDipendente, setDipendente, Impianti;
	protected JList<String> listaD ;
	protected static DefaultListModel<String> modelDip;
	protected JLabel nome, cognome, matricola, impianto, telefoni;
	protected TitledBorder titleLista, titleDatiDip;
	
	public  Principale(){
		
		super("GEST 1.0 MENU PRINCIPALE");
		
		barradeiMenu = new JMenuBar();
		file = new JMenu("File");
		modifica = new JMenu("Modifica");
		impianti = new JMenu("Impianti");
		aggiungiImpianto = new JMenuItem("Aggiungi Impianto");
		barradeiMenu.add(file);
		barradeiMenu.add(modifica);
		barradeiMenu.add(impianti);
		impianti.add(aggiungiImpianto);
		setJMenuBar(barradeiMenu);

		getContentPane().setLayout(new BorderLayout());
		//Costruzione Frame Principale
		//ContentPane in BorderLayout

		toolbarDip = new JToolBar();
		toolbarDip.setFloatable(false);
		JPanel pnlNord = new JPanel();
		getContentPane().add(pnlNord, BorderLayout.NORTH);
		pnlNord.setLayout(new BorderLayout());
		pnlNord.add(toolbarDip, BorderLayout.CENTER);
		addDipendente = new JButton();
		addDipendente.setIcon(new ImageIcon("./Icons/newUser.png"));
		remDipendente = new JButton();
		remDipendente.setIcon(new ImageIcon("./Icons/remUser.png"));
		setDipendente = new JButton();
		setDipendente.setIcon(new ImageIcon("./Icons/setUser.png"));
		Impianti = new JButton();
		Impianti.setIcon(new ImageIcon("./Icons/setImpianto.png"));
		addDipendente.addActionListener(new AddDipendente());
		remDipendente.addActionListener(new RemDipendente());
		setDipendente.addActionListener(new SetDipendente());
		Impianti.addActionListener(new GestImpianti());
		toolbarDip.add(addDipendente);
		toolbarDip.add(remDipendente);
		toolbarDip.add(setDipendente);
		toolbarDip.add(Impianti);
		
		JPanel pnlCentro = new JPanel();
		getContentPane().add(pnlCentro, BorderLayout.CENTER);		
		pnlCentro.setLayout(new GridLayout(0, 2));
		
		
		JPanel pnlCentroOvest = new JPanel();	
		pnlCentro.add(pnlCentroOvest);
		pnlCentroOvest.setLayout(new GridLayout(2,1));
		
		JPanel pnlCentroOvestNord = new JPanel();
		titleLista = new TitledBorder("Lista Dipendenti");
		pnlCentroOvestNord.setBorder(titleLista);
		pnlCentroOvest.add(pnlCentroOvestNord);	
		pnlCentroOvestNord.setLayout(new BorderLayout());
		modelDip = ListaDipendenti.arrListaD();		
		listaD = new JList<String>(modelDip);
		listaD.setFixedCellHeight(20);	
		listaD.addListSelectionListener(new ViewDatiDipendente());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(listaD);
		pnlCentroOvestNord.add(scrollPane);
			
		JPanel pnlCentroOvestSud = new JPanel();
		titleDatiDip = new TitledBorder("Dati Dipendente");
		pnlCentroOvestSud.setBorder(titleDatiDip); 
		pnlCentroOvest.add(pnlCentroOvestSud);
		pnlCentroOvestSud.setLayout(new BoxLayout(pnlCentroOvestSud, BoxLayout.Y_AXIS));
		
		nome = new JLabel("");		
		cognome = new JLabel("");
		matricola = new JLabel("");		
		impianto = new JLabel("");
		telefoni = new JLabel("");
		
		JPanel pnlNome = new JPanel(),
				pnlCognome = new JPanel(),
				pnlMatricola = new JPanel(),
				pnlImpianto = new JPanel(),
				pnlTelefoni = new JPanel();
		
		JLabel lNome = new JLabel("Nome :"),
				lCognome = new JLabel("Cognome :"),
				lMatricola = new JLabel("CID :"),
				lImpianto = new JLabel("Impianto :"),
				lTelefoni = new JLabel("Telefono :");
		
		pnlMatricola.add(lMatricola);
		pnlMatricola.add(matricola);
		pnlCentroOvestSud.add(pnlMatricola);
		pnlNome.add(lNome);
		pnlNome.add(nome);
		pnlCentroOvestSud.add(pnlNome);
		pnlCognome.add(lCognome);
		pnlCognome.add(cognome);
		pnlCentroOvestSud.add(pnlCognome);
		pnlImpianto.add(lImpianto);
		pnlImpianto.add(impianto);
		pnlCentroOvestSud.add(pnlImpianto);
		pnlTelefoni.add(lTelefoni);
		pnlTelefoni.add(telefoni);
		pnlCentroOvestSud.add(pnlTelefoni);
		
		setSize(700,700);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	//Visualizza i Dati del Dipendente selezionato dalla Lista
	class ViewDatiDipendente implements ListSelectionListener {

	     @Override   
		 public void valueChanged(ListSelectionEvent e) { 
			 if ((modelDip.size() >0) && (listaD.getSelectedValue() != null)) {
				 Dipendente d = modelToDipendente(listaD.getSelectedValue());
				 Dipendente real = ListaDipendenti.confronta(d);
				 nome.setText(real.getNome());
				 cognome.setText(real.getCognome());
				 matricola.setText(real.getMatricola());
				 impianto.setText(real.labelImpianto());
				 telefoni.setText(ElencoTelefonicoDipendenti.cercaTelefoni(real));
			}
	    }
	}
	
	// rimuove un dipendente dalla Lista
	public void rimuoviDipendente (String dipendente){
				
		Dipendente dTemp = modelToDipendente(dipendente);
		if (ListaDipendenti.rimuoviDipendente(dTemp)){
			for (int i=0; i<modelDip.size(); i++){
				if (modelDip.getElementAt(i).equals(dipendente)) {	
					if (modelDip.size() >0) listaD.setSelectedIndex(listaD.getFirstVisibleIndex());
					modelDip.remove(i);
					break;
				}
			}
		} 
		
	}
	
	public static DefaultListModel<String> getModelDip(){
		return modelDip;
	}
	
	//Converte un dato model in Dipentente
	public static Dipendente modelToDipendente (String model){
		
		String[] modelSplit = model.split(" ");
		return new Dipendente (modelSplit[1], modelSplit[2], modelSplit[0]);

	}
	
	class RemDipendente implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int conferma = JOptionPane.showConfirmDialog(null , "Sei sicuro ?", "ATTENZIONE!!", JOptionPane.YES_NO_OPTION);		
			if (conferma == JOptionPane.YES_OPTION)	{
				try{
					rimuoviDipendente(listaD.getSelectedValue());
				}
				catch (NullPointerException d) { 
					JOptionPane.showMessageDialog(null, "Nessun Dipendente selezionato");
				}
			}
		}
		
	}
	class AddDipendente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new FrameDipendenti();
			}	
	}	
	class SetDipendente implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (listaD.getSelectedValue() != null) {
				Dipendente temp = modelToDipendente(listaD.getSelectedValue());
				new SetDipendenteSW(ListaDipendenti.confronta(temp));
			}
		}
		
	}
	class FrameDipendenti extends JFrame {
		
		private static final long serialVersionUID = -971152471889661132L;
		JPanel pnlNord = new JPanel();
		JPanel pnlCentro = new JPanel();
		JPanel pnlSud = new JPanel();
		JTextField txtMatricola = new JTextField(10);
		JTextField txtNome = new JTextField(10);
		JTextField txtCognome = new JTextField(10);
		JButton btnOK = new JButton("OK");
		JButton btnCancel = new JButton("Annulla");
		
		public FrameDipendenti(){
			
			super("Inserimento Dipendente");
			pnlNord.add(new JLabel("Dati Dipendente"));
			pnlCentro.setLayout(new GridLayout(3, 2));
			pnlCentro.add(new JLabel("Matricola"));
			pnlCentro.add(txtMatricola);
			pnlCentro.add(new JLabel("Nome"));
			pnlCentro.add(txtNome);
			pnlCentro.add(new JLabel("Cognome"));
			pnlCentro.add(txtCognome);
			pnlSud.add(btnOK);
			btnOK.addActionListener(new AddDipendente());
			pnlSud.add(btnCancel);
			btnCancel.addActionListener(new Cancel());
			getContentPane().add(pnlNord, BorderLayout.NORTH);
			getContentPane().add(pnlCentro, BorderLayout.CENTER);
			getContentPane().add(pnlSud, BorderLayout.SOUTH);
			setSize(350,150);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
			setVisible(true);
		}
		
		class AddDipendente implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {			
				Dipendente d = new Dipendente(txtNome.getText(), txtCognome.getText(), txtMatricola.getText());
				if (!(ListaDipendenti.inserimentoCorretto(d))) {
					JOptionPane.showMessageDialog(null, "Dati Non Corretti");
					}
				else if (ListaDipendenti.addDipendente(d)){
						modelDip.addElement(d.toString());
						listaD.setSelectedIndex(listaD.getFirstVisibleIndex());
					setVisible(false);	
				}else {
					JOptionPane.showMessageDialog(null, "Dipendente già Esistente nella Lista");
				}
			}			
		}
		
		class Cancel implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);	
			}
			
		}
		
	}
	
	class GestImpianti implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new ImpiantiSW();
		}
		
	}
}