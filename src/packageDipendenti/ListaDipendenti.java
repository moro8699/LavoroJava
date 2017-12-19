package packageDipendenti;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Eccezioni.ElementoGiaEsistente;
import Generici.Lista;
import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class ListaDipendenti extends Lista implements Serializable{
	
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
		caricaLista(FILE_LISTA_DIP);
	}
	
	public static Dipendente confronta (Dipendente d){
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while(i.hasNext()){
			Dipendente temp = i.next();
			if (temp.equals(d)) return temp;
		}
		return null;
	}
	
	public static Dipendente cercaDipendente(String matricola) {
		Dipendente daCercare = new Dipendente("", "", matricola);
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while(i.hasNext()) {
			Dipendente d = i.next();
			if (d.equals(daCercare)) return d;
		}
		return null;
	}
	
	//Dissocia un dipendente da un Impianto
	public static void dissociaImpianto (Dipendente d){
		d.setImpiantoDiAppartenenza("");
	}
	
	public static void aggiungiDipendente (Dipendente dipendente) 
			throws ElementoGiaEsistente {
		
		aggiungiElemento(dipendente, listaDipendenti);
		
	}
	
	public static boolean rimuoviDipendente (Dipendente d){
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while (i.hasNext()) {
			Dipendente di = (Dipendente) i.next();
			if (di.equals(d)){
				Impianto impiantoDiAppartenenza = Impianti.getImpiantoSelezionato(d.getImpiantoDiAppartenenza());
				if(impiantoDiAppartenenza != null) impiantoDiAppartenenza.rimuoviDipendente(di);
				ElencoTelefonicoDipendenti.rimuoviNumeriDipendente(di);
				i.remove();
				salvaElencoDipendenti();
				return true;
			}
		}
		return false;
	}
	
	
}


