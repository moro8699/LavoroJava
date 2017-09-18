package packageDipendenti;
import java.io.Serializable;

public class Telefono implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6686860484892532608L;
	private String telefono = "", indicativoTelefono ="";
	private Dipendente dip = null;
	
	public Telefono(Dipendente dipendente, String telefono, String indicativoTelefono){
		this.setTelefono(telefono);
		this.setIndicativoTelefono(indicativoTelefono);
		dip = dipendente;
	}
	public Telefono(Dipendente dipendente, String telefono){
		this (dipendente, telefono, "");
	}

	public String getTelefono() {
		return telefono;
	}
	public Dipendente getDipendente(){
		return dip;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIndicativoTelefono() {
		return indicativoTelefono;
	}

	public void setIndicativoTelefono(String indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}
	
	@Override
	public String toString(){
		return getTelefono() + " " + getIndicativoTelefono();
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Telefono)) return false;  
		Telefono t = (Telefono) obj;		
		return t.getTelefono().equals(this.telefono);
	}	
}
