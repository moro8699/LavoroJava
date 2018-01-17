package packageImpianti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;
import Generici.Elenco;
import Turni.ElencoPresenze;
import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;
public class Impianti extends Elenco implements Serializable {
	
	private static final long serialVersionUID = 5944774531916148934L;
	private static final String FILE_SALVATAGGIO_LISTA_IMP = "./SaveFiles/listaImpianti.man";
	private static ArrayList<Impianto> listaImpianti;	
	
	private Impianti(){}
	
	public static ArrayList<Impianto> getListaImpianti() {
		return listaImpianti;
	}	
	
	//Salva i dati della nel file specificato
	public static void salvaListaImpianti(){
		salvaLista(FILE_SALVATAGGIO_LISTA_IMP, listaImpianti);
	} 	
	
	//Carica i dati della rubrica dal file specificato
	public static void caricaElencoImpianti(){
		listaImpianti = caricaLista(FILE_SALVATAGGIO_LISTA_IMP);
	}
	
	public static int getSize() {
		return listaImpianti.size();
	}
	
	public static void aggiungiImpianto(Impianto i) 
			throws ElementoGiaEsistente {
	
		listaImpianti = aggiungiElemento(i, listaImpianti);
		salvaListaImpianti();
		
	}
	
	public static void rimuoviImpianto(Impianto i) 
			throws ElementoNonTrovato{
		
		dissociaDipendenti(i);
		ElencoPresenze.rimuoviPresenzeImpianto(i);
		listaImpianti = rimuoviElemento(i, listaImpianti);
		
	}
	
	public static Impianto getImpiantoSelezionato(String nomeImpianto) {
		Iterator<Impianto> iterator = listaImpianti.iterator();
		while(iterator.hasNext()) {
			Impianto i = iterator.next();
			if(i.getNomeImpianto().equals(nomeImpianto)) return i;
		}
		return null;
	}
	
	public static Impianto getImpiantoSelezionato(int indice) {
		return listaImpianti.get(indice);
	}
	
	public static void dissociaDipendenti (Impianto i){
		Iterator<Dipendente> iterator = ListaDipendenti.getListaDipendenti().iterator();
		while (iterator.hasNext()){
			Dipendente temp = iterator.next();
			if (i.getNomeImpianto().equals(temp.getImpiantoDiAppartenenza())){
				temp.dissociaDaImpianto();
			}
		}
	}
	
	public static Impianto getImpiantoDaModel (String impianto) {
		Iterator<Impianto> iterator = listaImpianti.iterator();
		while(iterator.hasNext()) {
			Impianto i = iterator.next();
			if (i.getNomeImpianto().equals(impianto)) return i;
		}
		return null;		
	}
	
}
