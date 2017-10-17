package Generici;

import java.util.regex.Pattern;

public class Controllo {
	
	private Controllo(){}
	
	public static boolean verificaDataInserita (String data){
		//if (Pattern.matches("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[-/.](19|20)\\d\\d", data)) return true;
		if (Pattern.matches("(19|20)\\d\\d[\\-.](0[1-9]|1[012])[\\-.](0[1-9]|[12][0-9]|3[01])", data)) return true;
		return false;
	}
}
