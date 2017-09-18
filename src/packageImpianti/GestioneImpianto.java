package packageImpianti;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GestioneImpianto extends JFrame {

	private static final long serialVersionUID = 1573062133006083688L;
	
	public GestioneImpianto(Impianto i) {
		
		super("Gestione Impianto Di "+i.getNomeImpianto());
		
		setSize(500,500);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		
	}

}
