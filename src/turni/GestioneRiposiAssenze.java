package turni;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GestioneRiposiAssenze extends JFrame {

	private static final long serialVersionUID = -1137143183189022518L;
	private JList<String> riposi, assenze;
	private JScrollPane scrollRiposi, scrollAssenze;

	public GestioneRiposiAssenze() {

		super("Gestione Riposi/Assenze");

		riposi = new JList<String>(new RiposiListModel());
		assenze = new JList<String>(new AssenzeListModel());

		getContentPane().setLayout(new BorderLayout());

		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(2, 2));

		getContentPane().add(centro, BorderLayout.CENTER);

	}

	class RiposiListModel extends AbstractListModel<String> {

		private static final long serialVersionUID = 1L;
		Vector<String> listaRiposi = caricaListaRiposi();

		@Override
		public int getSize() {
			return listaRiposi.size();
		}

		@Override
		public String getElementAt(int index) {
			return listaRiposi.elementAt(index);
		}

		private Vector<String> caricaListaRiposi() {
			Vector<String> riposi = new Vector<String>();
			for (int i = 0; i < ElencoPresenze.getSize(); i++) {
				Presenza p = ElencoPresenze.getElencoPresenze().get(i);
				if (p instanceof Riposo)
					riposi.addElement(p.toString());
			}

			return riposi;
		}
	}

	class AssenzeListModel extends AbstractListModel<String> {

		private static final long serialVersionUID = 1L;
		Vector<String> listaAssenze = caricaListaAssenze();

		@Override
		public int getSize() {
			return listaAssenze.size();
		}

		@Override
		public String getElementAt(int index) {
			return listaAssenze.elementAt(index);
		}

		private Vector<String> caricaListaAssenze() {
			Vector<String> assenze = new Vector<>();
			for (int i = 0; i < ElencoAssenze.getSize(); i++) {
				Assenza a = ElencoAssenze.getElencoAssenze().get(i);
				assenze.addElement(a.toString());
			}
			return assenze;
		}

	}
}
