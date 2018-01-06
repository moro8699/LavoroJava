package Generici;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;

public abstract class Elenco {
	
	//Salva i dati della nel file specificato
	protected static <E> void salvaLista(String nomeFile, ArrayList<E> lista){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(lista);
			oss.close();
		}
		catch(Exception e){}
	} 	
			
	//Carica i dati della rubrica dal file specificato
	@SuppressWarnings("unchecked")
	protected static <E> ArrayList<E> caricaLista(String nomeFile){
		ArrayList<E> lista = new ArrayList<E>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));		
			lista = (ArrayList<E>) ois.readObject();
			ois.close();
			return lista;
		}
		catch(Exception e){return null;}
	}		
	
	//Aggiunge un elemento alla Lista
	protected static <E> ArrayList<E> aggiungiElemento(E el, ArrayList<E> lista) 
			throws ElementoGiaEsistente{
		
		if (lista.contains(el)) {
			throw new ElementoGiaEsistente();
		}
		lista.add(el);
		System.out.println("Elemento Aggiunto con successo");
		return lista;

	}
		
	//Rimuove un elemento dalla lista
	protected static <E> ArrayList<E> rimuoviElemento(E el, ArrayList<E> lista ) 
			throws ElementoNonTrovato {
			
		if(lista.remove(el)){
			System.out.println("Elemento rimosso con successo");
			return lista;
		}
		System.out.println("Elemento non trovato");
		throw new ElementoNonTrovato();
	}	
			
}