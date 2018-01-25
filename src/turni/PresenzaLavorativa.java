package turni;

import java.time.LocalTime;
import packageImpianti.Impianto;

public class PresenzaLavorativa extends Presenza {

	private static final long serialVersionUID = -886272928095582125L;
	private Impianto impianto;

	public PresenzaLavorativa(Impianto i, String identificativo, String annotazione, LocalTime inizio, LocalTime fine,
			LocalTime pausa) {

		super(identificativo, annotazione, inizio, fine, pausa);
		setImpianto(i);

	}

	public Impianto getImpianto() {
		return impianto;
	}

	public void setImpianto(Impianto impianto) {
		this.impianto = impianto;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof PresenzaLavorativa))
			return false;
		PresenzaLavorativa p = (PresenzaLavorativa) obj;
		return (p.getIdentificativo().equals(this.getIdentificativo()) && p.getImpianto().equals(this.getImpianto()));
	}

}
