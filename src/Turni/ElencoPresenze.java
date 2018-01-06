package Turni;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;
import Generici.Elenco;

public class ElencoPresenze extends Elenco implements Serializable{

	private static final long serialVersionUID = -7274666461959091620L;
	private static final String FILE_ELENCO_PRESENZE = "./SaveFiles/listaPresenze.man";
	private static ArrayList<Presenza> elencoPresenze;

	public static String getFileElencoPresenze() {
		return FILE_ELENCO_PRESENZE;
	}

	public static ArrayList<Presenza> getElencoPresenze() {
		return elencoPresenze;
	}
	
	public static void salvaElenco(){
		salvaLista(FILE_ELENCO_PRESENZE, elencoPresenze);
	}
	
	public static void caricaElencoPresenze(){
		elencoPresenze = caricaLista(FILE_ELENCO_PRESENZE);
	}
	
	//Aggiunge una Presenza alla Lista
	public static void aggiungiPresenza(Presenza p) 
			throws ElementoGiaEsistente{
		
		elencoPresenze = aggiungiElemento(p, elencoPresenze);

	}
	
	public static void rimuoviPresenza(Presenza p) 
			throws ElementoNonTrovato {

			elencoPresenze = rimuoviElemento(p, elencoPresenze);

	}	
	
	//Se presente in elenco restituisce la Presenza selezionata
	public static Presenza restituisciPresenzaInElenco (Presenza altraPresenza){
		Iterator<Presenza> iterator = elencoPresenze.iterator();
		while(iterator.hasNext()){
			Presenza temp = iterator.next();
			if (temp.equals(altraPresenza)) return temp;
		}
		return null;
	}

	public static boolean modificaPresenza(Presenza originale, Presenza modificata){
		Presenza presenzaOriginale = restituisciPresenzaInElenco(originale),
				presenzaModificata = restituisciPresenzaInElenco(modificata);
		
		if(presenzaModificata != null && modificata.getIdentificativo() != originale.getIdentificativo()){
			System.out.println("Elemento già esistente");
			return false;
		}
		presenzaOriginale.setIdentificativo(modificata.getIdentificativo());
		presenzaOriginale.setDescrizione(modificata.getDescrizione());
		presenzaOriginale.setInizio(modificata.getInizio());
		presenzaOriginale.setFine(modificata.getFine());
		presenzaOriginale.setPausa(modificata.getPausa());
		ElencoPresenze.salvaLista(FILE_ELENCO_PRESENZE, elencoPresenze);
		return true;
		
	}
			

}
