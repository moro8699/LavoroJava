package turni;

import java.io.Serializable;
import java.util.ArrayList;

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

	public static void salvaElencoAssenze() {
		salvaLista(FILE_ELENCO_ASSENZE, elencoAssenze);
	}

	public static int getSize() {
		return elencoAssenze.size();
	}

}
