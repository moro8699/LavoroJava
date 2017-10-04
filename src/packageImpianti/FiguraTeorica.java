package packageImpianti;

import javax.swing.table.DefaultTableModel;

import packageDipendenti.Dipendente;

public class FiguraTeorica {
	
	private Impianto impianto = null;
	private String mansione = "", identifiactivo = "";
	private Dipendente dipendente = null;

	public FiguraTeorica(Impianto impianto, String mansione, String identificativo) {
		setImpianto(impianto);
		setMansione(mansione);
		setIdentifiactivo(identificativo);
	}
	
	public String getIdentifiactivo() {
		return identifiactivo;
	}
	public void setIdentifiactivo(String identifiactivo) {
		this.identifiactivo = identifiactivo;
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
