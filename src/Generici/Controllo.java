package Generici;

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
}
