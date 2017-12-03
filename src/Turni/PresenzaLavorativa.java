package Turni;

import java.time.LocalTime;
import packageImpianti.Impianto;

public class PresenzaLavorativa extends Presenza {


	private static final long serialVersionUID = -886272928095582125L;

	public PresenzaLavorativa(Impianto i, String identificativo, String annotazione, LocalTime inizio, LocalTime fine, LocalTime pausa) {
		
		super(i, identificativo, annotazione, inizio, fine, pausa);
		
	}	

}
