package generici;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import eccezioni.ErroreTrasferimento;
import packageDipendenti.Dipendente;
import packageDipendenti.ElencoTrasferimenti;
import packageDipendenti.ListaDipendenti;
import packageDipendenti.Trasferimento;

public class Controllo {
	
	private Controllo(){}
	
	//Controllo inserimento matricola Dipendente
	public static boolean verificaMatricolaDipendente(String matricola){
		if (Pattern.matches("[0-9]{7}", matricola)) return true;
		return false;
	}
	
	public static void verificaImpiantiDiAppartenenza(){
		for(int i =0; i< ListaDipendenti.listaDipendentiSize(); i++){
			Dipendente d = ListaDipendenti.getDipendente(i);
			d.setImpiantoDiAppartenenza(cercaImpiantoDiAppartenenza(d));
		}
	}
	
	public static String cercaImpiantoDiAppartenenza(Dipendente d){
		for(int k =0; k< ElencoTrasferimenti.getSizeElenco(); k++){
				Trasferimento t = ElencoTrasferimenti.getElemento(k);
				if (t.getDipendente().equals(d)){
					if (t.getDal().isAfter(LocalDate.now())) continue;
					if ((t.getDal().isBefore(LocalDate.now()) || t.getDal().isEqual(LocalDate.now())) &&
							(t.getAl().isEqual(LocalDate.now()) || t.getAl().isAfter(LocalDate.now()))) 
						return t.getImpianto().toString();
				}
			}
		return "";
	}
	
	//Controllo inserimento data nel tipo aaaa-mm-gg
	public static boolean verificaDataInserita (String data){
		
		//if (!(Pattern.matches("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[-/.](19|20)\\d\\d", data))) {

		if (!(Pattern.matches("(19|20)\\d\\d[\\-.](0[1-9]|1[012])[\\-.](0[1-9]|[12][0-9]|3[01])", data))) {
			JOptionPane.showMessageDialog(null, "La data Inserita deve essere di tipo aaaa-mm-gg");
			return false;
		}
		return true;
	}
	
	//Controllo inserimento orario nel tipo oo:mm
	public static boolean verificaOraInserita (String ora){
		
		if(Pattern.matches("(0[0-9]|1[0-9]|2[0123])[\\:]([0-5][0-9]|60)", ora)) return true;
		return false;
		
	}
	
	//Controllo Inserimento Numeri di Telefono
	public static boolean verificaRecapitoTelefonico (String recapito){
		
		if (Pattern.matches("[0-9]{5,}", recapito )) return true;
		
		System.out.println("Telefono non valido"); 
		return false;
		
	}
	
	//Verifica che il Campo sia compilato
	public static boolean verificaRigaVuota (String stringa){
		if(Pattern.matches("[0-9a-zA-Z]", stringa)) return true;
		return false;
	}
	
	//Converte un Dato di tipo Date in LocalDate
	public static LocalDate DateToLocalDate (Date data){
		if (data == null) return null;
		
		String[] splitter = data.toString().split(" ");
		
		return LocalDate.of(
				Integer.parseInt(splitter[5]), 
				monthStringToInteger(splitter[1]), 
				Integer.parseInt(splitter[2])
				);
	}
	
	public static LocalDate DateToLocalDate(int year, int month, int dayOfMonth) {
		return LocalDate.of(year, month, dayOfMonth);
	}
	
	public static boolean verificaTrasferimento(Trasferimento t) throws ErroreTrasferimento {
		Dipendente d = t.getDipendente();
		verificaDataAssunzione(d);
		if (d.getDataAssunzione().isAfter(t.getDal())) throw new ErroreTrasferimento() {
			private static final long serialVersionUID = -8873351620679996912L;
			public String toString() {
				return "Data di Inizio Trasferimento Antecedente la Data di Assunzione";
			}
		};
		
		if (t.getAl() == LocalDate.MAX) return true; 
		if (t.getAl().isAfter(t.getDal())) return true;
		return false;
	}
	
	public static void verificaDataAssunzione(Dipendente d) throws ErroreTrasferimento {
		if (d.getDataAssunzione() == null) throw new ErroreTrasferimento() {
			private static final long serialVersionUID = -8375245448289519649L;
			public String toString() {
				return("Inserire la Data di Assunzione");
			}
		};
	}	
	
	//Converte un Dato di tipo LocalDate in Date
	public static Date localDateToDate(LocalDate data) {
		if(data == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, data.getYear());
		cal.set(Calendar.MONTH, data.getMonthValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, data.getDayOfMonth());
		return cal.getTime();
	}
	
	//Convert i Mesi Date in dato Intero 
	private static int monthStringToInteger(String month){

		switch (month){
			case "Jan": return 1;
			case "Feb": return 2;
			case "Mar": return 3;
			case "Apr": return 4;
			case "May": return 5;
			case "Jun": return 6;
			case "Jul": return 7;
			case "Aug": return 8;
			case "Sep": return 9;
			case "Oct": return 10;
			case "Nov": return 11;
			case "Dec": return 12; 
		}
		return 0;
	}
}
