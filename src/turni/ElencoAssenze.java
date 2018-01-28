package turni;

import java.io.Serializable;
import java.util.ArrayList;

import eccezioni.ElementoGiaEsistente;
import generici.Elenco;

public class ElencoAssenze extends Elenco implements Serializable {

	private static final long serialVersionUID = 301228204835231957L;
	private static final String FILE_ELENCO_ASSENZE = "./SaveFiles/listaAssenze.man";

	private static ArrayList<Assenza> elencoAssenze;

	private ElencoAssenze() {
	}

	public static ArrayList<Assenza> getElencoAssenze() {
		return elencoAssenze;
	}

	public static void caricaElencoAssenze() {
		elencoAssenze = caricaLista(FILE_ELENCO_ASSENZE);
	}
	
	public static Assenza getElementoA(int posizione){
		return elencoAssenze.get(posizione);
	}
	
	public static Assenza ricercaAssenzaDaIndentificativo (String identificativo){
		for (int i =0; i< elencoAssenze.size(); i++){
			if (getElementoA(i).equals(new Assenza(identificativo, ""))) return getElementoA(i);
		}
		return null;
	}

	public static void salvaElencoAssenze() {
		salvaLista(FILE_ELENCO_ASSENZE, elencoAssenze);
	}
	
	public static void aggiungiAssenza(Assenza a) throws ElementoGiaEsistente{
		elencoAssenze = aggiungiElemento(a, elencoAssenze);
	}

	public static int getSize() {
		return elencoAssenze.size();
	}

}
