import Turni.ElencoPresenze;
import main.Principale;
import packageDipendenti.ElencoTelefonicoDipendenti;
import packageDipendenti.ListaDipendenti;
import packageImpianti.*;

public class mainLavoro {

	public static void main(String[] args) {
		
		Impianti.apriRub(Impianti.getFileLista());
 		ListaDipendenti.caricaElencoDipendenti();
 		ElencoTelefonicoDipendenti.caricaElenco();
 		ElencoPresenze.caricaElenco();
 		
 		new Principale();
	}
}