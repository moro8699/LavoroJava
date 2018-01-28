package turni;

import java.io.Serializable;
import java.time.LocalTime;

public class Assenza extends Inserimento implements Serializable {

	private static final long serialVersionUID = -200309008393607238L;
	private String identificativo = "", descrizione = "";
	private final LocalTime INIZIO = LocalTime.MIDNIGHT, FINE = LocalTime.MAX;

	public Assenza(String identificativo, String descrizione) {
		this.identificativo = identificativo;
		this.setDescrizione(descrizione); 
	}

	public void setIdentificativo(String i) {
		identificativo = i;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalTime getINIZIO() {
		return INIZIO;
	}

	public LocalTime getFINE() {
		return FINE;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Assenza))
			return false;
		Assenza a = (Assenza) obj;
		return (a.getIdentificativo().equals(this.identificativo));
	}

	@Override
	public String toString() {
		return getIdentificativo();
	}

}
