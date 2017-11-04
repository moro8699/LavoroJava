package Turni;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import packageImpianti.Impianto;

public class GestioneTurni extends JFrame {

	private static final long serialVersionUID = -9004497323450995745L;
	private JToolBar toolbar;
	private JButton gestionePresenze;

	public GestioneTurni(Impianto i){
		
		super ("Gestione Turni Impianto di " + i.toString());
		
		getContentPane().setLayout(new BorderLayout());
		
		toolbar = new JToolBar();
		gestionePresenze = new JButton("Gest. Presenze");
		gestionePresenze.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GestionePresenze(i);
			}
		});
		toolbar.add(gestionePresenze);
		
		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1024, 768);
		setLocation ((int)(dim.getWidth()-this.getWidth())/2, (int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
	}
}