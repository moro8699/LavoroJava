package Generici;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Controllo {
	
	private Controllo(){}

	public static boolean verificaDataInserita (String data){
		
		//if (!(Pattern.matches("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[-/.](19|20)\\d\\d", data))) {

		if (!(Pattern.matches("(19|20)\\d\\d[\\-.](0[1-9]|1[012])[\\-.](0[1-9]|[12][0-9]|3[01])", data))) {
			JOptionPane.showMessageDialog(null, "La data Inserita deve essere di tipo aaaa-mm-gg");
			return false;
		}
		return true;
	}
}
