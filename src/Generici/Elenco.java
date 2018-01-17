package Generici;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Eccezioni.ElementoGiaEsistente;
import Eccezioni.ElementoNonTrovato;

/**
 * Astrae il Concetto di Lista Generica dove vengono aggiunti e rimossi elementi
 * La lista può essere salvata e caricata da un file.
 * L
 * 
 * @author Fabio Morandi
 *
 */
public abstract class Elenco {
	
	/**Salva i dati di una Lista in un file specificato
	 * 
	 * @param nomeFile Il Percorso del file dove verranno salvati i dati dell'ArrayList
	 * @param lista L'Array da salvare
	 * 
	 */
	protected static <E> void salvaLista(String nomeFile, ArrayList<E> lista){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(lista);
			oss.close();
		}
		catch(Exception e){}
	} 	
			
	/** Carica i dati della rubrica dal file specificato in un ArrayList
	 * 
	 * @param nomeFile Il Percorso dove si trova il file da caricare nell'array
	 * @return Un ArrayList caricato con i dati presenti nel file in input
	 */
	
	@SuppressWarnings("unchecked")
	protected static <E> ArrayList<E> caricaLista(String nomeFile){
		ArrayList<E> lista = new ArrayList<E>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));		
			lista = (ArrayList<E>) ois.readObject();
			ois.close();
			
		}
		catch(Exception e){
			lista = new ArrayList<E>();
		}
		return lista;

	}			
	/**Aggiunge un elemento alla Lista
	 * 
	 * @param el Elemento che verrà aggiunto alla lista
	 * @param lista la lista dove verrà aggiunto l'elemento
	 * @return L'ArrayList con aggiunto l'elemento se non già Presente
	 * @throws ElementoGiaEsistente Se l'elemento è gia presente nella Lista, questo non verrà aggiunto
	 */
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
