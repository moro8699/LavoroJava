import generici.Controllo;
import main.Principale;
import packageDipendenti.ElencoTelefonicoDipendenti;
import packageDipendenti.ElencoTrasferimenti;
import packageDipendenti.ListaDipendenti;
import packageImpianti.*;
import turni.ElencoAssenze;
import turni.ElencoPresenze;

public class mainLavoro {

	public static void main(String[] args) {
		
		Impianti.caricaElencoImpianti();
 		ListaDipendenti.caricaElencoDipendenti();
 		ElencoTelefonicoDipendenti.caricaElencoTelefonico();
 		ElencoPresenze.caricaElencoPresenze();
 		ElencoAssenze.caricaElencoAssenze();
 		ElencoTrasferimenti.caricaElencoTrasferimenti();
 		Controllo.verificaImpiantiDiAppartenenza();
 		
 		new Principale();
	}
}