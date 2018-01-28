package turni;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestioneAssenze extends JFrame {

	private static final long serialVersionUID = -1137143183189022518L;
	private JList<String> assenze;
	private static DefaultListModel<String> model;
	private JLabel assenzaListener;
	private JButton aggiungi, rimuovi;
	private JScrollPane scrollAssenze;

	public GestioneAssenze() {

		super("Gestione Assenze");
		
		model = inizializzaListModel();
		
		assenze = new JList<String>(model);
		assenze.addListSelectionListener(new ListaAssenzeListener());
		scrollAssenze = new JScrollPane(assenze);
		
		getContentPane().setLayout(new BorderLayout());

		JPanel centro = new JPanel();
		centro.setLayout(new BorderLayout());
		centro.add(scrollAssenze, BorderLayout.CENTER);
		
		aggiungi = new JButton(" + ");
		aggiungi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SetAssenza();
			}
		});
		rimuovi = new JButton(" - ");
		assenzaListener = new JLabel("");
		
		JPanel sud = new JPanel();
		sud.setLayout(new GridLayout(2, 0));
		JPanel sudNord = new JPanel();
		JPanel sudSud = new JPanel();
		sudNord.add(assenzaListener);
		sudSud.add(aggiungi);
		sudSud.add(rimuovi);
		sud.add(sudNord);
		sud.add(sudSud);
		
		getContentPane().add(centro, BorderLayout.CENTER);
		getContentPane().add(sud, BorderLayout.SOUTH);

		setSize(300,450);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);	
		
	}
	
	private DefaultListModel<String> inizializzaListModel(){
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(int i =0; i< ElencoAssenze.getSize(); i++){
			model.addElement(ElencoAssenze.getElementoA(i).toString());
		}
		return model;
	}
	
	public static void aggiungiAssenzaAModel(Assenza a){
		model.addElement(a.toString());
	}
	
	class ListaAssenzeListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!(assenze.isSelectionEmpty())){
				assenzaListener.setText(ElencoAssenze.ricercaAssenzaDaIndentificativo(assenze.getSelectedValue()).getDescrizione());
			}
		}

		
	}

}
