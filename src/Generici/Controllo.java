package Generici;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Controllo {
	
	private Controllo(){}
	
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
		
		if(Pattern.matches("(0[0-9]|1[0-9]|2[123])[\\:]([0-5][0-9]|60)", ora)) return true;
		return false;
		
	}
	
	//Controllo riga vuota
	public static boolean verificaRigaVuota (String stringa){
		if(Pattern.matches("[0-9a-zA-Z]", stringa)) return true;
		return false;
	}
	
	//Converte un Dato di tipo Date in LocalDate
	public static LocalDate DateToLocalDate (Date data){
		if (data == null) return null;
		
		String[] splitter = data.toString().split(" ");
		
		return LocalDate.of(Integer.parseInt(splitter[5]), 
				monthStringToInteger(splitter[1]), 
				Integer.parseInt(splitter[2]));
	}
	
	public static Date localDateToDate(LocalDate data) {
		if(data == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, data.getYear());
		cal.set(Calendar.MONTH, data.getMonthValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, data.getDayOfMonth());
		return cal.getTime();
	}
	//Convert i Mesi Date in dato Intero 
	//Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
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
