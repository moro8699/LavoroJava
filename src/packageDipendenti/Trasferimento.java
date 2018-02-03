package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;

import packageImpianti.Impianto;

public class Trasferimento implements Serializable{
	
	private static final long serialVersionUID = -4136133844889882316L;
	private Dipendente dipendente;
	private Impianto destinazione;
	private LocalDate dal, al;

	public Trasferimento (Dipendente dipendente, Impianto destinazione, LocalDate dal, LocalDate al) {
		setDipendente(dipendente);
		setImpianto(destinazione);
		setDal(dal);
		setAl(al);
	}
	
	public Trasferimento(Dipendente dipendente, Impianto destinazione, LocalDate dal){
		this(dipendente, destinazione, dal, null);
	}
	
	public Dipendente getDipendente() {
		return dipendente;
	}
	public void setDipendente(Dipendente d) {
		this.dipendente = d;
	}
	public Impianto getImpianto() {
		return destinazione;
	}
	public void setImpianto(Impianto impianto) {
		this.destinazione = impianto;
	}
	public LocalDate getDal() {
		return dal;
	}
	public void setDal(LocalDate dal) {
		this.dal = dal;
	}
	public LocalDate getAl() {
		return al;
	}
	public void setAl(LocalDate al) {
		this.al = al;
	}
	
	public boolean equals(Object obj){
		
		if (obj == null) return false;
		if (!(obj instanceof Trasferimento)) return false;  
		Trasferimento t = (Trasferimento) obj;		
		return 
				t.getDipendente().equals(dipendente) &&
				t.getImpianto().equals(destinazione) &&
				t.getDal().equals(dal) &&
				t.getAl().equals(al);
		
	}

}
