package Turni;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ElencoPresenze implements Serializable{

	private static final long serialVersionUID = 7274666461959091620L;
	private static final String FILE_ELENCO_PRESENZE = "./SaveFiles/elencoPresenze.man";
	private static ArrayList<Presenza> elencoPresenze;

	public static String getFileElencoPresenze() {
		return FILE_ELENCO_PRESENZE;
	}

	public static ArrayList<Presenza> getElencoPresenze() {
		return elencoPresenze;
	}
	
	//Salva i dati della nel file specificato
	public static void salvaLista(String nomeFile){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(elencoPresenze);
			oss.close();
		}
		catch(Exception e){}
	} 	
			
	//Carica i dati della rubrica dal file specificato
	@SuppressWarnings("unchecked")
	public static void caricaLista(String nomeFile){
		elencoPresenze = new ArrayList<Presenza>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			elencoPresenze = (ArrayList<Presenza>) ois.readObject();
		}
		catch(Exception e){}
	}		
			
	public static boolean addElemento(Presenza p){
		Iterator<Presenza> iterator = elencoPresenze.iterator();
		while(iterator.hasNext()){
			Presenza temp = iterator.next();
			if(temp.equals(p)){
				System.out.println("Elemento già esistente");
				return false;
			}			
		}
		elencoPresenze.add(p);
		return true;
	}
			
	public static boolean rimuoviElemento(Presenza p) {
		Iterator<Presenza> iterator = elencoPresenze.iterator();
		while (iterator.hasNext()){
			if (iterator.next().equals(p)){
				System.out.println("Elemento rimosso con successo");
				iterator.remove();
				return true;
			}
		}
		System.out.println("Elemento non trovato");
		return false;
	}
}
