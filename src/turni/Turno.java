package turni;

import java.io.Serializable;
import java.util.ArrayList;

import packageImpianti.Impianto;

public class Turno implements Serializable {

	private static final long serialVersionUID = 1306739086969388747L;
	private Impianto impiantoDiAppartenenza = null;
	private String identificativo = "";
	private int giornate = 0;
	private ArrayList<Presenza> turno = null;

	public Turno (String identificativo, Impianto impiantoDiAppartenenza, int giornate) {

		this.identificativo = identificativo;
		this.impiantoDiAppartenenza = impiantoDiAppartenenza;
		setGiornate(giornate);
		turno = new ArrayList<Presenza>();

	}

	// Setter & Getter
	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public Impianto getImpiantoDiAppartenenza() {
		return impiantoDiAppartenenza;
	}

	public ArrayList<Presenza> getTurno() {
		return turno;
	}

	public int getGiornate() {
		return giornate;
	}

	public void setGiornate(int giornate) {
		this.giornate = giornate;
	}

	public boolean aggiungiGiornata(Presenza p) {
		if (!(verificaDisponibilitaGiornate()))
			return false;
		turno.add(p);
		return true;
	}

	public boolean verificaDisponibilitaGiornate() {
		if (turno.size() >= giornate) {
			System.out.println("Impossibile Aggiungere giornate, Turno Completato");
			return false;
		}
		return true;
	}
}
