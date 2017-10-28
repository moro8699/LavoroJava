package packageDipendenti;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import packageImpianti.Impianti;
import packageImpianti.Impianto;

public class ListaDipendenti implements Serializable{
	
	private static final long serialVersionUID = -2879347912650820933L;
	private static final String FILE_SALVATAGGIO_LISTA_DIP = "./SaveFiles/listaDipendenti.man";
	private ListaDipendenti(){}
	private static ArrayList<Dipendente> listaDipendenti;
	
	public static String getFileLista() {
		return FILE_SALVATAGGIO_LISTA_DIP;
	}
	
	public static int listaDipendentiSize (){
		return listaDipendenti.size();
	}
	
	public static Dipendente getDipendente(int posizione){
		return listaDipendenti.get(posizione);
	}
	
	public static  ArrayList<Dipendente> getListaDipendenti(){
		return listaDipendenti;
	}
	
	public static DefaultListModel<String> arrListaD(){
		DefaultListModel<String> model = new DefaultListModel<String>();
		Iterator<Dipendente> iterator = listaDipendenti.iterator();
		while (iterator.hasNext()){
			Dipendente d = (Dipendente) iterator.next();
			model.addElement(d.toString()); 
		}
		return model; 
	}

	//Salva i dati della nel file specificato
	public static void salvaLista(String nomeFile){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(listaDipendenti);
			oss.close();
		}
		catch(Exception e){}
	} 
	
	//Carica i dati della rubrica dal file specificato
	@SuppressWarnings("unchecked")
	public static void apriRub(String nomeFile){
		listaDipendenti = new ArrayList<Dipendente>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			listaDipendenti = (ArrayList<Dipendente>) ois.readObject();
		}
		catch(Exception e){}
	}
	
	public static Dipendente confronta (Dipendente d){
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while(i.hasNext()){
			Dipendente temp = i.next();
			if (temp.equals(d)) return temp;
		}
		return null;
	}
	
	public static Dipendente cercaDipendente(String matricola) {
		Dipendente daCercare = new Dipendente("", "", matricola);
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while(i.hasNext()) {
			Dipendente d = i.next();
			if (d.equals(daCercare)) return d;
		}
		return null;
	}
	
	//Dissocia un dipendente da un Impianto
	public static void dissociaImpianto (Dipendente d){
		d.setImpiantoDiAppartenenza("");
	}
	
	public static boolean addDipendente (Dipendente dipendente){
		if(!(listaDipendenti.isEmpty())){
			Iterator<Dipendente> i = listaDipendenti.iterator();
			if (!(inserimentoCorretto(dipendente))) return false;
			while (i.hasNext()){
				if (((Dipendente) i.next()).equals(dipendente)){
					System.out.println("Dipendente già inserito");
					return false;
				}
			}
		}
		listaDipendenti.add(dipendente); 
		ListaDipendenti.salvaLista(getFileLista());
		return true;
	}
	
	public static boolean inserimentoCorretto(Dipendente d){
		if (Pattern.matches("[0-9]{7}",d.getMatricola()) 
				&& d.getNome()!= ""
				&& d.getCognome()!= "") return true;
		return false;
	}
	
	public static boolean rimuoviDipendente (Dipendente d){
		Iterator<Dipendente> i = listaDipendenti.iterator();
		while (i.hasNext()) {
			Dipendente di = (Dipendente) i.next();
			if (di.equals(d)){
				Impianto impiantoDiAppartenenza = Impianti.getImpiantoSelezionato(d.getImpiantoDiAppartenenza());
				if(impiantoDiAppartenenza != null) impiantoDiAppartenenza.rimuoviDipendente(di);
				ElencoTelefonicoDipendenti.rimuoviNumeriDipendente(di);
				i.remove();
				ListaDipendenti.salvaLista(getFileLista());
				return true;
			}
		}
		return false;
	}
	
	
}


