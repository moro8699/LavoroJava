package Turni;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import packageImpianti.Impianto;

public class GestioneTurni extends JFrame {

	private static final long serialVersionUID = -9004497323450995745L;
	

	public GestioneTurni(Impianto i){
		
		super ("Gestione Turni Impianto di " + i.toString());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1024, 768);
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
	}
}