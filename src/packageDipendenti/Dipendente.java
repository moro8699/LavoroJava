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
	public static final short IDONEO = 0, IDONEO_PARZIALE = 1, INIDONEO = 2;
	
	private LocalDate dataCreazioneIstanza = null;
	private LocalDate dataAssunzione = null;
	private String nome ="", cognome ="", matricola="", impiantoDiAppartenenza="";
	private int statoDisalute = IDONEO;
	
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
	public LocalDate getDataAssunzione() {
		if (dataAssunzione != null) return dataAssunzione;
		return LocalDate.MIN;
	}
	public void setDataAssunzione(LocalDate dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}
	public LocalDate getDataCreazioneIstanza() {
		return dataCreazioneIstanza;
	}
	public int getStatoDiSalute() {
		return statoDisalute;
	}
	public void setDipendenteIdoneo() {
		statoDisalute = IDONEO;
	}
	public void setDipendenteIdoneoParziale() {
		statoDisalute = IDONEO_PARZIALE;
	}
	public void setDipendenteInidoneo() {
		statoDisalute = INIDONEO;
	}
	
	public String labelImpianto(){
		if (impiantoDiAppartenenza.isEmpty()) return "** NON ASSEGNATO **";
		return impiantoDiAppartenenza;
	}
	
	public String labelAssunzione(){
		if (dataAssunzione == null) return "** NON INSERITA **";
		return dataAssunzione.toString();
	}
	
	public String labelStatoDiSalute () {
		if (statoDisalute == IDONEO) return "Idoneo";
		if (statoDisalute == IDONEO_PARZIALE) return "Idoneo Parziale";
		if (statoDisalute == INIDONEO) return "Inidoneo";
		return "";
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
		return getCognome() + " " + getNome() + " (" + getMatricola() + ")";
	}
	
}
