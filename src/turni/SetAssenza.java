package turni;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import eccezioni.ElementoGiaEsistente;
import generici.Controllo;

public class SetAssenza extends JFrame {

	private static final long serialVersionUID = -6930394002674393457L;
	private final static int R_IDENT = 0, R_DESCR = 1;
	private Vector<Vector<String>> dati;
	private Vector<String> header, nomeRiga, valore;
	private JTable assenza;
	private JScrollPane scrollpane;
	private DefaultTableModel model;
	private JButton ok;

	public SetAssenza() {

		InizializzaModel();
		inizializzaForm();

	}

	public void InizializzaModel() {

		model = new DefaultTableModel() {

			private static final long serialVersionUID = -8087792651113476340L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column != 0)
					return true;
				return false;
			}
		};

		inizializzaVector();

	}

	public void inizializzaForm() {

		getContentPane().setLayout(new BorderLayout());

		model.setDataVector(dati, header);
		assenza = new JTable(model);
		scrollpane = new JScrollPane(assenza);

		getContentPane().add(scrollpane, BorderLayout.CENTER);

		JPanel sud = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(new InserisciRiposo());
		sud.add(ok);
		getContentPane().add(sud, BorderLayout.SOUTH);

		setSize(500, 250);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (dim.getWidth() - this.getWidth()) / 2, (int) (dim.getHeight() - this.getHeight()) / 2);
		setVisible(true);

	}

	private void inizializzaVector() {

		// Header Orizzontale
		header = new Vector<String>();
		header.addElement("Attributo");
		header.addElement("Valore");

		// Header Verticale
		nomeRiga = new Vector<String>();

		nomeRiga.add(R_IDENT, "Identificativo");
		nomeRiga.add(R_DESCR, "Descizione");

		nuovaAssenza();

	}

	private void nuovaAssenza() {

		valore = new Vector<String>();

		valore.add(R_IDENT, "");
		valore.add(R_DESCR, "");

		caricaDati();

	}

	private void caricaDati() {

		dati = new Vector<Vector<String>>();

		for (int i = 0; i < nomeRiga.size(); i++) {

			Vector<String> temp = new Vector<String>();

			temp.addElement(nomeRiga.elementAt(i));
			temp.addElement(valore.elementAt(i));

			dati.add(temp);

		}

	}

	public boolean verificaCampi() {

		if (Controllo.verificaRigaVuota((String) assenza.getValueAt(R_IDENT, 1))) {
			JOptionPane.showMessageDialog(null, "Identificativo Obbligatorio");
			return false;
		}

		if (Controllo.verificaRigaVuota((String) assenza.getValueAt(R_DESCR, 1))) {
			JOptionPane.showMessageDialog(null, "Inserire una descrizione della Presenza inserita");
			return false;
		}

		return true;
	}

	class InserisciRiposo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Assenza a;
			if (verificaCampi()) {
				a = new Assenza((String) assenza.getValueAt(R_IDENT, 1), (String) assenza.getValueAt(R_DESCR, 1));
				try {
					ElencoAssenze.aggiungiAssenza(a);
					GestioneAssenze.aggiungiAssenzaAModel(a);
					ElencoAssenze.salvaElencoAssenze();
					setVisible(false);
				} catch (ElementoGiaEsistente exc) {
					JOptionPane.showMessageDialog(null,
							"Esiste già un elemento con l'identificativo " + a.toString());
				}

			}

		}

	}

}
