package packageDipendenti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import eccezioni.ElementoGiaEsistente;
import eccezioni.ElementoNonTrovato;
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
				public String toString() {return"Rilevato Conflitto con il seguente Trasferimento: \n" + tras.toString();}
			};
			if(conflittoDateTrasferimento(tras, t))	throw exc;
		}
	}
	//restituisce true se c'è conflitto tra le date di inizo e fine dei due Trasferimenti
	public static boolean conflittoDateTrasferimento (Trasferimento trasferimento, Trasferimento altroTrasferimento){
		if (!(trasferimento.getDipendente().equals(altroTrasferimento.getDipendente()))) return false;
		if( trasferimento.getDal().isBefore(altroTrasferimento.getDal()) && trasferimento.getAl() == LocalDate.MAX) return true; 
		if(!( 
				(trasferimento.getDal().isAfter(altroTrasferimento.getDal()) && trasferimento.getDal().isAfter(altroTrasferimento.getAl())) ||
				(trasferimento.getAl().isBefore(altroTrasferimento.getDal())) && trasferimento.getAl().isBefore(altroTrasferimento.getAl())))
			return true;
		
		return false;
	}
	
	public static void eliminaTrasferimento(Trasferimento t) 
			throws ElementoNonTrovato{
		elencoTrasferimenti = rimuoviElemento(t, elencoTrasferimenti);
	}
	
	public static void modificaTrasferimento(Trasferimento daModificare, Trasferimento modificato) 
			throws ErroreTrasferimento {		
		
		int indice = -1;
		for(int i =0; i< elencoTrasferimenti.size(); i++){
			Trasferimento t = elencoTrasferimenti.get(i);
			if(t.equals(daModificare)){indice = i; continue;}
			if (conflittoDateTrasferimento(modificato, t)) throw new ErroreTrasferimento() {
				private static final long serialVersionUID = -7313944217323401375L;
				public String toString() {return"Rilevato Conflitto con il seguente Trasferimento: \n" + t.toString();}
			};
		}
		if(indice>=0){
			elencoTrasferimenti.get(indice).setDal(modificato.getDal());
			elencoTrasferimenti.get(indice).setAl(modificato.getAl());
		}
	}
	
	public static void AggiungiTrasferimento(Trasferimento t) 
			throws ElementoGiaEsistente, InserimentoNonCorretto, ErroreTrasferimento {
		
		cercaConflitti(t);
		if(Controllo.verificaTrasferimento(t)) {
			aggiungiElemento(t, elencoTrasferimenti);
			salvaLista(FILE_ELENCO_TRASFERIMENTI, elencoTrasferimenti);
		}
		else throw new InserimentoNonCorretto();
	}	
	
	public static void eliminaTrasferimentiDipendente (Dipendente d) throws ElementoNonTrovato{
		for(int i =0; i< elencoTrasferimenti.size(); i++){
			Trasferimento t = elencoTrasferimenti.get(i);
			if (t.getDipendente().equals(d)){
				eliminaTrasferimento(t);
			}
		}
	}
}
