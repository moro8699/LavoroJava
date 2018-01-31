package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import generici.Elenco;

public class ElencoTrasferimenti extends Elenco implements Serializable {

	private static final long serialVersionUID = 2410464647399783168L;
	private final String FILE_ELENCO_TRASFERIMENTI = "./SaveFiles/elencoTrasferimenti.man";
	private ArrayList<Trasferimento> elencoTrasferimenti;

	private ElencoTrasferimenti() {}

	public ArrayList<Trasferimento> getElencoTrasferimenti() {
		return elencoTrasferimenti;
	}
	
	public void caricaElencoTrasferimenti(){
		elencoTrasferimenti = caricaLista(FILE_ELENCO_TRASFERIMENTI);
	}
	
	public void salvaElencoTrasferimenti(){
		salvaLista(FILE_ELENCO_TRASFERIMENTI, elencoTrasferimenti);
	}
	
	public void terminaTrasferimentoAttuale(Dipendente d, LocalDate termine){
		LocalDate oggi = LocalDate.now();
		for(int i=0; i< elencoTrasferimenti.size(); i++){
			Trasferimento t = elencoTrasferimenti.get(i);
			if(t.getDipendente().equals(d))
				if(t.getAl() == null || t.getAl().isEqual(oggi) || t.getAl().isAfter(oggi)) 
					t.setAl(termine);
		}
	}
}
