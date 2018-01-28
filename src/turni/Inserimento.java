package turni;

public abstract class Inserimento {
	
	private boolean utilizzato = false;
	
	public Inserimento() {}
	
	public boolean isUtilizzato() {
		return utilizzato;
	}
	public void setUtilizzato(boolean utilizzato) {
		this.utilizzato = utilizzato;
	}

}
