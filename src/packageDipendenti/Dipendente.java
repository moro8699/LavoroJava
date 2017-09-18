package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;

import packageImpianti.Impianti;
import packageImpianti.Impianto;
/**
 *  Astrae il concetto di Dipendente 
 *  
 */
public class Dipendente implements Serializable {

	private static final long serialVersionUID = -7500270499673162065L;

	LocalDate dataCreazioneIstanza = null;
	private String nome ="", cognome ="", matricola="", impiantoDiAppartenenza="";
	
	public Dipendente(String nome, String cognome, String matricola){
		setNome(nome);
		setCognome(cognome);
		this.matricola = matricola;
		dataCreazioneIstanza = LocalDate.now();	
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getMatricola() {
		return matricola;
	}

	public String getImpiantoDiAppartenenza() {
		return impiantoDiAppartenenza;
	}
	
	public String labelImpianto(){
		if (impiantoDiAppartenenza.isEmpty()) return "** NON ASSEGNATO **";
		return impiantoDiAppartenenza;
	}
	
	public void setImpiantoDiAppartenenza(String impiantoDiAppartenenza) {
		this.impiantoDiAppartenenza = impiantoDiAppartenenza;
		
	}
	
	public boolean dissocia () {
		if (!(this.impiantoDiAppartenenza.isEmpty())){
			Iterator<Impianto> iterator = Impianti.getListaImpianti().iterator();
			while (iterator.hasNext()){
				Impianto i = iterator.next();
				if (i.equals(new Impianto(this.impiantoDiAppartenenza))) {
					i.rimuoviDipendente(this);
					return true;
				}
			}
		} return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Dipendente)) return false;  
		Dipendente d = (Dipendente) obj;		
		return d.getMatricola().equals(this.matricola);
	}
	
	@Override
	public String toString(){
		return getMatricola() + " " + getNome() + " " + getCognome();
	}


	
	
	
}
