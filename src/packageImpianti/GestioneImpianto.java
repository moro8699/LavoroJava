package packageImpianti;

import javax.swing.JFrame;

public class GestioneImpianto extends JFrame {

	private static final long serialVersionUID = 1573062133006083688L;
	
	public GestioneImpianto(Impianto i) {
		
		super("Gestione Impianto Di "+i.getNomeImpianto());
		
		pack();
		setVisible(true);
	}

}
