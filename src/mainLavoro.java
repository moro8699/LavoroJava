import Turni.ElencoPresenze;
import main.Principale;
import packageDipendenti.ElencoTelefonicoDipendenti;
import packageDipendenti.ListaDipendenti;
import packageImpianti.*;

public class mainLavoro {

	public static void main(String[] args) {
		
		Impianti.apriRub(Impianti.getFileLista());
 		ListaDipendenti.apriRub(ListaDipendenti.getFileLista());
 		ElencoTelefonicoDipendenti.apriRub(ElencoTelefonicoDipendenti.getFileElencoTel());
 		ElencoPresenze.caricaLista(ElencoPresenze.getFileElencoPresenze());
		
 		new Principale();
 		
		ElencoTelefonicoDipendenti.salvaLista(ElencoTelefonicoDipendenti.getFileElencoTel());
		ListaDipendenti.salvaLista(ListaDipendenti.getFileLista());
		ElencoPresenze.salvaLista(ElencoPresenze.getFileElencoPresenze());
	}

}

		//ISTRUZIONI DI PROVA
		//Dipendente a = new Dipendente("Fabio", "Morandi", "2940970");
		//ListaDipendenti.addDipendente(a);
		//ElencoTelefonicoDipendenti.aggiungiNumeroTelefonico(new Telefono(b, "0558314564", "Ufficio"));
		//ElencoTelefonicoDipendenti.aggiungiNumeroTelefonico(new Telefono(b, "3374444581", "casa"));
		//ElencoTelefonicoDipendenti.rimuoviNumero(b, "3374444521");
		//ElencoTelefonicoDipendenti.cercaTelefoni(b);
		//Impianto firenze = new Impianto("Firenze SMN");
		//firenze.assegnaDipendente(a);
		//firenze.rimuoviDipendente(a);
 		//FrameDipendenti a = new FrameDipendenti();