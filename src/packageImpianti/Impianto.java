package packageImpianti;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import packageDipendenti.Dipendente;

public class Impianto implements Serializable {
	
	private static final long serialVersionUID = -8722710677459377737L;
	private String nomeImpianto="";
	private ArrayList<Dipendente> impianto;
	private boolean inizializzato;
	private int risorseNecessarie;
	
	public Impianto(String nomeImpianto, int risorseNecessarie){
		
		setNomeImpianto(nomeImpianto);
		setRisorseNecessarie(risorseNecessarie);
		impianto = new ArrayList<Dipendente>();
		setInizializzato(false);
	}
	
	public Impianto(String nomeImpianto) {
		this(nomeImpianto, 0);
	}
	
	//Metodi Setter & Getter
	public String getNomeImpianto() {
		return nomeImpianto;
	}	
	public void setImpianto (String nomeImpianto){
		this.nomeImpianto = nomeImpianto;
	}
	public void setNomeImpianto(String nomeImpianto) {
		this.nomeImpianto = nomeImpianto;
	}
	public boolean isInizializzato() {
		return inizializzato;
	}
	public void setInizializzato(boolean inizializzato) {
		this.inizializzato = inizializzato;
	}
	public int getRisorseNecessarie() {
		return risorseNecessarie;
	}
	public void setRisorseNecessarie(int risorseNecessarie) {
		this.risorseNecessarie = risorseNecessarie;
	}
	
	public boolean rimuoviDipendente(Dipendente d){
		for (int i=0; i<impianto.size(); i++){
			Dipendente temp = impianto.get(i);
			if (temp.equals(d)){
				impianto.remove(i);
				System.out.println("Il dipendente " + d.getCognome() +" "+ d.getNome() + 
					" (" + d.getMatricola() + ") rimosso dall'impianto di " + d.getImpiantoDiAppartenenza());
				d.setImpiantoDiAppartenenza("");
				return true;
			}
		}
		System.out.println("Dipendente non trovato");
		return false;
	}
	
	public boolean assegnaDipendente(Dipendente d){
		if (verificaDipendente(d)) return false;
		Iterator<Dipendente> iterator = impianto.iterator();
		while (iterator.hasNext()){
			Dipendente temp = (Dipendente) iterator.next();
			if (temp.equals(d)){
				System.out.println("Dipendente già presente in questo Impianto");
				return false;
			}
		}
		impianto.add(d);
		d.setImpiantoDiAppartenenza(nomeImpianto);
		return true;
	}
	//Verifica se un dipendente è già assegnato ad un Impianto
	public boolean verificaDipendente(Dipendente d){
		if (!(d.getImpiantoDiAppartenenza().isEmpty())) {
			System.out.println("Dipendente assegnato all'impianto di: " +  d.getImpiantoDiAppartenenza());
			return true;
		}
		return false;
		
	}
	
	public void inizializzaImpianto(int risorseNecessarie) {
		if (risorseNecessarie > 0) {
			setRisorseNecessarie(risorseNecessarie);
			setInizializzato(true);
		}
	}
	
	@Override
	public boolean equals (Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Impianto)) return false;  
		Impianto i = (Impianto) obj;		
		return i.getNomeImpianto().equals(this.getNomeImpianto());		
	}
	
	@Override
	public String toString(){
		return getNomeImpianto();
	}

	
	
	
}
