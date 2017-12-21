package Eccezioni;

public class InserimentoNonCorretto extends Exception {

	private static final long serialVersionUID = -5204545226357607786L;
	
	public InserimentoNonCorretto() {
		super("Inserimento non Corretto");
	}
	
	public String toString() {
		return getMessage();
	}
}
