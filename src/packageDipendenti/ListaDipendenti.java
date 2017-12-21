package packageDipendenti;

import java.io.Serializable;
import java.util.ArrayList;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;
import Generici.Elenco;
import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class ListaDipendenti extends Elenco implements Serializable{
	
	private static final long serialVersionUID = -2879347912650820933L;
	private static final String FILE_LISTA_DIP = "./SaveFiles/listaDipendenti.man";
	private ListaDipendenti(){}
	private static ArrayList<Dipendente> listaDipendenti;
	
	public static String getFileLista() {
		return FILE_LISTA_DIP;
	}
	
	public static int listaDipendentiSize (){
		return listaDipendenti.size();
	}
	
	public static Dipendente getDipendente(int posizione){
		return listaDipendenti.get(posizione);
	}
	
	public static  ArrayList<Dipendente> getListaDipendenti(){
		return listaDipendenti;
	}

	//Salva i dati della nel file specificato
	public static void salvaElencoDipendenti(){
		salvaLista(FILE_LISTA_DIP, listaDipendenti);
	} 
	
	//Carica i dati della rubrica dal file specificato
	public static void caricaElencoDipendenti() {
		listaDipendenti = caricaLista(FILE_LISTA_DIP);
	}
	
	//Restituisce un istanza Dipendente con in input la matricola
	public static Dipendente cercaDipendente(String matricola) {
		
		Dipendente daCercare = new Dipendente("", "", matricola);
		if (listaDipendenti.contains(daCercare)) 
			return listaDipendenti.get(listaDipendenti.indexOf(daCercare));  
		
		return null;
		
	}
	
	
	public static void aggiungiDipendente (Dipendente dipendente) 
			throws ElementoGiaEsistente {
		
		aggiungiElemento(dipendente, listaDipendenti);
		
	}
	
	public static void rimuoviDipendente (Dipendente d) 
			throws ElementoNonTrovato{
		
		if (listaDipendenti.contains(d)){
			Impianto impiantoDiAppartenenza = Impianti.getImpiantoSelezionato(d.getImpiantoDiAppartenenza());
			if(impiantoDiAppartenenza != null) impiantoDiAppartenenza.rimuoviDipendente(d);
			ElencoTelefonicoDipendenti.rimuoviNumeriDipendente(d);
			rimuoviElemento(d, listaDipendenti);
			salvaElencoDipendenti();
		}
	}
	
	
}


