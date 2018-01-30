package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;

import sanitario.Salute;

/**
 *  Astrae il concetto di Dipendente 
 *  
 */
public class Dipendente implements Serializable {

	private static final long serialVersionUID = -7500270499673162065L;
	
	private LocalDate dataCreazioneIstanza = null, dataAssunzione = null, dataDiNascita = null;
	private String nome ="", cognome ="", matricola="", impiantoDiAppartenenza="";
	
	Salute statoDiSalute;
	
	public Dipendente(String nome, String cognome, String matricola){
		setNome(nome);
		setCognome(cognome);
		this.matricola = matricola;
		statoDiSalute = new Salute(this);
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
	public LocalDate getDataAssunzione() {
		return dataAssunzione;
	}
	public void setDataAssunzione(LocalDate dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}
	public LocalDate getDataCreazioneIstanza() {
		return dataCreazioneIstanza;
	}
	
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public String getStatoDiSalute(){
		return statoDiSalute.toString();
	}
	
	public void setImpiantoDiAppartenenza(String impiantoDiAppartenenza) {
		this.impiantoDiAppartenenza = impiantoDiAppartenenza;
		
	}
	
	//Dissocia un dipendente da un Impianto
	public void dissociaDaImpianto (){
		setImpiantoDiAppartenenza("");
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
		return getCognome() + " " + getNome() + " (" + getMatricola() + ")";
	}

	
}
