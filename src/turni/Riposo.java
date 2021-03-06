package turni;

import java.time.LocalTime;

public class Riposo extends Presenza {

	private static final long serialVersionUID = 722076034313498377L;

	public Riposo(String identificativo, String annotazione) {

		super(identificativo, annotazione, LocalTime.MIN, LocalTime.MAX, LocalTime.MIN);

	}

	@Override
	public LocalTime impegno() {
		return LocalTime.MIN;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Riposo))
			return false;
		Riposo r = (Riposo) obj;
		return r.getIdentificativo().equals(this.getIdentificativo());
	}

}
