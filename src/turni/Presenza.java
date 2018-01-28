package turni;

import java.io.Serializable;
import java.time.LocalTime;

public abstract class Presenza extends Inserimento implements Serializable {

	private static final long serialVersionUID = -1765844892878514071L;
	private String identificativo = "", descrizione = "";
	private LocalTime inizio = null, fine = null, pausa = null;

	public Presenza(String identificativo, String descrizione, LocalTime inizio, LocalTime fine, LocalTime pausa) {

		setIdentificativo(identificativo);
		setDescrizione(descrizione);
		setInizio(inizio);
		setFine(fine);
		setPausa(pausa);

	}

	public Presenza(String identificativo, String annotazione, LocalTime inizio, LocalTime fine) {
		this(identificativo, annotazione, inizio, fine, LocalTime.MIN);
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalTime inizio) {
		this.inizio = inizio;
	}

	public LocalTime getFine() {
		return fine;
	}

	public void setFine(LocalTime fine) {
		this.fine = fine;
	}

	public LocalTime getPausa() {
		return pausa;
	}

	public void setPausa(LocalTime pausa) {
		this.pausa = pausa;
	}

	public LocalTime impegno() {
		int oreImpegno = 0, minImpegno = 0;
		oreImpegno = differenzaOre(inizio, fine) - pausa.getHour();
		minImpegno = differenzaMinuti(inizio.getMinute(), fine.getMinute());

		return togliPausaPranzo(LocalTime.of(oreImpegno, minImpegno), pausa);

	}

	private int differenzaMinuti(int minInizio, int minFine) {

		if (minFine < minInizio) {
			return (60 - minInizio) + minFine;
		} else {
			return minFine - minInizio;
		}
	}

	private int differenzaOre(LocalTime inizio, LocalTime fine) {
		byte riporto = 0;
		if (fine.getMinute() < inizio.getMinute())
			riporto++;
		if (fine.getHour() < inizio.getHour())
			return ((24 - inizio.getHour()) + fine.getHour()) + riporto;
		else
			return fine.getHour() - inizio.getHour() + riporto;
	}

	public LocalTime togliPausaPranzo(LocalTime impegno, LocalTime pausaPranzo) {
		byte riporto = 0;
		int oreImpegno = 0, minutiImpegno = 0;

		if (impegno.getMinute() < pausaPranzo.getMinute()) {
			riporto++;
			minutiImpegno = (60 - pausaPranzo.getMinute()) + impegno.getMinute();
		} else {
			minutiImpegno = impegno.getMinute() - pausaPranzo.getMinute();
		}
		oreImpegno = impegno.getHour() - riporto;

		return LocalTime.of(oreImpegno, minutiImpegno);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Presenza))
			return false;
		Presenza p = (Presenza) obj;
		return (p.getIdentificativo().equals(this.getIdentificativo()));
	}

	@Override
	public String toString() {
		return getIdentificativo();
	}
}
