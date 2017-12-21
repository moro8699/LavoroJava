package packageDipendenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;
import Eccezioni.InserimentoNonCorretto;
import Generici.Controllo;
import Generici.Elenco;

public class ElencoTelefonicoDipendenti extends Elenco implements Serializable {

	private static final long serialVersionUID = -8134543161001092202L;
	private static final String FILE_ELENCO_TEL = "./SaveFiles/elencoTelefonico.man";
	private static ArrayList<Telefono> elencoTelefonico;
	
	public static ArrayList<Telefono> getElencoTelefonico() {
		return elencoTelefonico;
	}

	public static String getFileElencoTel() {
		return FILE_ELENCO_TEL;
	}

	//Costruttore privato
	private ElencoTelefonicoDipendenti(){}
	
	//Salva i dati della nel file specificato
	public static void salvaElenco(){
		salvaLista(FILE_ELENCO_TEL, elencoTelefonico);
	} 	
	
	//Carica i dati della rubrica dal file specificato
	public static void caricaElencoTelefonico(){
		elencoTelefonico = caricaLista(FILE_ELENCO_TEL);
	} 
	
	public static void aggiungiNumeroTelefonico (Telefono tel) 
			throws ElementoGiaEsistente, InserimentoNonCorretto {
		
		if(Controllo.verificaRecapitoTelefonico(tel.getTelefono())) {
			aggiungiElemento(tel, elencoTelefonico);
			salvaElenco();
		}
		throw new InserimentoNonCorretto();
		
	}
	
	public static void rimuoviNumeroTelefonico (Telefono t) 
			throws ElementoNonTrovato {
		
		rimuoviElemento(t, elencoTelefonico);
		
	}
	
	public static void rimuoviNumeriDipendente(Dipendente d){
		Iterator<Telefono> iterator = elencoTelefonico.iterator();
		while (iterator.hasNext()){
			if (iterator.next().getDipendente().equals(d)) iterator.remove();
		}
	}
	
	public static String cercaTelefoni(Dipendente d){
		String tel = "";
		Iterator<Telefono> i = elencoTelefonico.iterator();
		while(i.hasNext()){
			Telefono temp = (Telefono) i.next();
			if (temp.getDipendente().equals(d))
				tel += (temp.getTelefono() + " ");
		}
		return tel;
	}
	
}
