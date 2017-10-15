package Turni;

import java.time.LocalTime;

import packageImpianti.Impianto;

public class Riposo extends Presenza {

	public Riposo(Impianto i, String identificativo, String annotazione) {
		
		super(i, identificativo, annotazione, LocalTime.of(00, 00), LocalTime.of(23, 59), LocalTime.of(00, 00));
		
	}
	
	@Override
	public boolean equals (Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Riposo)) return false;  
		Riposo r = (Riposo) obj;		
		return (r.getImpianto().equals(this.getImpianto()) && 
				(r.getIdentificativo().equals(this.getIdentificativo())));
	}
	
}
