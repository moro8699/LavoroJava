package Turni;

import java.time.LocalTime;

import packageImpianti.Impianto;

public class Riposo extends Presenza {

	private static final long serialVersionUID = 722076034313498377L;

	public Riposo(Impianto i, String identificativo, String annotazione) {
		
		super(i, identificativo, annotazione, LocalTime.MIN, LocalTime.MAX, LocalTime.MIN);
		
	}
	
	@Override
	public LocalTime impegno() {
		return LocalTime.MIN;
		
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
