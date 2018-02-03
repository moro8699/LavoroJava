package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import eccezioni.ElementoGiaEsistente;
import eccezioni.InserimentoNonCorretto;
import generici.Controllo;
import generici.Elenco;

public class ElencoTrasferimenti extends Elenco implements Serializable {

	private static final long serialVersionUID = 2410464647399783168L;
	private final static String FILE_ELENCO_TRASFERIMENTI = "./SaveFiles/elencoTrasferimenti.man";
	private static ArrayList<Trasferimento> elencoTrasferimenti;

	private ElencoTrasferimenti() {}

	public static ArrayList<Trasferimento> getElencoTrasferimenti() {
		return elencoTrasferimenti;
	}
	
	public static void caricaElencoTrasferimenti(){
		elencoTrasferimenti = caricaLista(FILE_ELENCO_TRASFERIMENTI);
	}
	
	public static void salvaElencoTrasferimenti(){
		salvaLista(FILE_ELENCO_TRASFERIMENTI, elencoTrasferimenti);
	}
	
	public static int getSizeElenco(){
		return elencoTrasferimenti.size();
	}
	
	public static Trasferimento getElemento(int indice){
		return elencoTrasferimenti.get(indice);
	}
	
	private static void terminaTrasferimentoAttuale(Dipendente d, LocalDate termine) 
			throws InserimentoNonCorretto{
		
		LocalDate oggi = LocalDate.now();
		for(int i=0; i< elencoTrasferimenti.size(); i++){
			Trasferimento t = elencoTrasferimenti.get(i);
			if(t.getDipendente().equals(d))
				if(t.getAl() == null || t.getAl().isEqual(oggi) || t.getAl().isAfter(oggi)) 
					if(t.getDal().isAfter(termine)) throw new InserimentoNonCorretto();
					t.setAl(termine);
					
		}
	}
	
	public static void AggiungiTrasferimento(Trasferimento t) 
			throws ElementoGiaEsistente, InserimentoNonCorretto {
		
		terminaTrasferimentoAttuale(t.getDipendente(), t.getDal().minusDays(1));
		if(Controllo.verificaTrasferimento(t)) {
			aggiungiElemento(t, elencoTrasferimenti);
			salvaLista(FILE_ELENCO_TRASFERIMENTI, elencoTrasferimenti);
		}
		else throw new InserimentoNonCorretto();
	}
}
