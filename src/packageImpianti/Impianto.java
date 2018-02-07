package packageImpianti;

import java.io.Serializable;

public class Impianto implements Serializable {
	
	private static final long serialVersionUID = -8722710677459377737L;
	private String nomeImpianto="";
	private boolean inizializzato;
	private int risorseNecessarie;
	
	public Impianto(String nomeImpianto, int risorseNecessarie){
		
		setNomeImpianto(nomeImpianto);
		setRisorseNecessarie(risorseNecessarie);
		setInizializzato(false);
	}
	
	public Impianto(String nomeImpianto) {
		this(nomeImpianto, 0);
	}
	
	//Metodi Setter & Getter
	public String getNomeImpianto() {
		return nomeImpianto;
	}	
	public void setImpianto (String nomeImpianto){
		this.nomeImpianto = nomeImpianto;
	}
	public void setNomeImpianto(String nomeImpianto) {
		this.nomeImpianto = nomeImpianto;
	}
	public boolean isInizializzato() {
		return inizializzato;
	}
	public void setInizializzato(boolean inizializzato) {
		this.inizializzato = inizializzato;
	}
	public int getRisorseNecessarie() {
		return risorseNecessarie;
	}
	public void setRisorseNecessarie(int risorseNecessarie) {
		this.risorseNecessarie = risorseNecessarie;
	}
	
	public void inizializzaImpianto(int risorseNecessarie) {
		if (risorseNecessarie > 0) {
			setRisorseNecessarie(risorseNecessarie);
			setInizializzato(true);
		}
	}
	
	@Override
	public boolean equals (Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Impianto)) return false;  
		Impianto i = (Impianto) obj;		
		return i.getNomeImpianto().equals(this.getNomeImpianto());		
	}
	
	@Override
	public String toString(){
		return getNomeImpianto();
	}

	
	
	
}
