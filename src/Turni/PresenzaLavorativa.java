package Turni;

import java.time.LocalTime;
import packageImpianti.Impianto;

public class PresenzaLavorativa extends Presenza {


	private static final long serialVersionUID = -886272928095582125L;

	public PresenzaLavorativa(Impianto i, String identificativo, String annotazione, LocalTime inizio, LocalTime fine, LocalTime pausa) {
		
		super(i, identificativo, annotazione, inizio, fine, pausa);
		
	}
	
	public LocalTime getInizio(){
		return inizio;
	}
	
	public LocalTime getFine(){
		return fine;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (!(obj instanceof PresenzaLavorativa)) return false;  
		PresenzaLavorativa presenza = (PresenzaLavorativa) obj;		
		return (presenza.getImpianto().equals(this.getImpianto()) && 
				(presenza.getIdentificativo().equals(this.getIdentificativo())) &&
				presenza.getInizio().equals(this.getInizio()) &&
				presenza.getFine().equals(this.getFine()));		
	}

}
