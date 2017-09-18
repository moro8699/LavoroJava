package packageDipendenti;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ElencoTelefonicoDipendenti implements Serializable {

	private static final long serialVersionUID = -8134543161001092202L;
	private static final String FILE_ELENCO_TEL = "./SaveFiles/elencoTelefonico.man";
	private static ArrayList<Telefono> elencoTelefonico;
	
	public static ArrayList<Telefono> getElencoTelefonico() {
		return elencoTelefonico;
	}

	public static String getFileElencoTel() {
		return FILE_ELENCO_TEL;
	}

	//Costruttore privato
	private ElencoTelefonicoDipendenti(){}
	
	//Salva i dati della nel file specificato
	public static void salvaLista(String nomeFile){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(elencoTelefonico);
			oss.close();
		}
		catch(Exception e){}
	} 	
	
	//Carica i dati della rubrica dal file specificato
	@SuppressWarnings("unchecked")
	public static void apriRub(String nomeFile){
		elencoTelefonico = new ArrayList<Telefono>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			elencoTelefonico = (ArrayList<Telefono>) ois.readObject();
		}
		catch(Exception e){}
	} 
	
	public static boolean aggiungiNumeroTelefonico (Telefono tel) {
		
		if (!(Pattern.matches("[0-9]{5,}",tel.getTelefono()))){
			System.out.println("Telefono non valido"); 
			return false;
		}
		Iterator<Telefono> i = elencoTelefonico.iterator();
		while (i.hasNext()){
			Telefono temp = (Telefono) i.next();
			if(temp.equals(tel)){
				System.out.println("Telefono già esistente");
				return false;
			}	
		}
		elencoTelefonico.add(tel);	
		return true;
	}
	
	public static boolean rimuoviNumero(Telefono t) {
		Iterator<Telefono> iterator = elencoTelefonico.iterator();
		while (iterator.hasNext()){
			if (t.equals(iterator.next())) {
				System.out.println(t.getTelefono() + " rimosso con successo");
				iterator.remove();
				ElencoTelefonicoDipendenti.salvaLista(FILE_ELENCO_TEL);
				return true;
			}
		}
		System.out.println("Numero non trovato");
		return false;
	}
	
	public static void rimuoviNumeriDipendente(Dipendente d){
		Iterator<Telefono> iterator = elencoTelefonico.iterator();
		while (iterator.hasNext()){
			if (iterator.next().getDipendente().equals(d)) iterator.remove();
		}
	}
	
	public static String cercaTelefoni(Dipendente d){
		String tel = "";
		Iterator<Telefono> i = elencoTelefonico.iterator();
		while(i.hasNext()){
			Telefono temp = (Telefono) i.next();
			if (temp.getDipendente().equals(d))
				tel += (temp.getTelefono() + " ");
		}
		return tel;
	}
	
}
