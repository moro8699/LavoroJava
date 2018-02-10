package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import eccezioni.ElementoGiaEsistente;
import eccezioni.ErroreTrasferimento;
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
	
	private static void cercaConflitti(Trasferimento t) 
			throws ErroreTrasferimento{
		
		for(int i=0; i< elencoTrasferimenti.size(); i++){
			Trasferimento tras = elencoTrasferimenti.get(i);
			ErroreTrasferimento exc = new ErroreTrasferimento() {
				private static final long serialVersionUID = -7313944217323401375L;
				public String toString() {
					return"Rilevato Conflitto con altro Trasferimento";
				}
			};
			if(tras.getDipendente().equals(t.getDipendente())){
				if(tras.getAl() == null) throw exc; 
				if(!( 
						(tras.getDal().isAfter(t.getDal()) && tras.getDal().isAfter(t.getAl())) ||
						(tras.getAl().isBefore(t.getDal())) && tras.getAl().isBefore(t.getAl())))
					
					throw exc;
			}
		}
	}
	
	public static void AggiungiTrasferimento(Trasferimento t) 
			throws ElementoGiaEsistente, InserimentoNonCorretto, ErroreTrasferimento {
		
		//terminaTrasferimentoAttuale(t.getDipendente(), t.getDal().minusDays(1));
		cercaConflitti(t);
		if(Controllo.verificaTrasferimento(t)) {
			aggiungiElemento(t, elencoTrasferimenti);
			salvaLista(FILE_ELENCO_TRASFERIMENTI, elencoTrasferimenti);
		}
		else throw new InserimentoNonCorretto();
	}
	
	public static void eliminaTrasferimentiDipendente (Dipendente d){
		for(int i =0; i< elencoTrasferimenti.size(); i++){
			Trasferimento t = elencoTrasferimenti.get(i);
			if (t.getDipendente().equals(d)){
				elencoTrasferimenti.remove(i);
			}
		}
	}
}
