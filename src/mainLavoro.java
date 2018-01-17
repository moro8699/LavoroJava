import Turni.ElencoAssenze;
import Turni.ElencoPresenze;
import main.Principale;
import packageDipendenti.ElencoTelefonicoDipendenti;
import packageDipendenti.ListaDipendenti;
import packageImpianti.*;

public class mainLavoro {

	public static void main(String[] args) {
		
		Impianti.caricaElencoImpianti();
 		ListaDipendenti.caricaElencoDipendenti();
 		ElencoTelefonicoDipendenti.caricaElencoTelefonico();
 		ElencoPresenze.caricaElencoPresenze();
 		ElencoAssenze.caricaElencoAssenze();
 		
 		new Principale();
	}
}