package packageImpianti;

import packageDipendenti.Dipendente;

public class FiguraTeorica {
	
	private Impianto impianto;
	private String mansione;
	private Dipendente dipendente;
	public FiguraTeorica(Impianto impianto, String mansione) {
		setImpianto(impianto);
		setMansione(mansione);
	}
	public String getMansione() {
		return mansione;
	}
	public void setMansione(String mansione) {
		this.mansione = mansione;
	}
	public Impianto getImpianto() {
		return impianto;
	}
	public void setImpianto(Impianto impianto) {
		this.impianto = impianto;
	}
	
	public void assegnaDipendente(Dipendente d) {
		this.dipendente = d;
	}
	public Dipendente getDipendente() {
		return dipendente;
	}
	
}