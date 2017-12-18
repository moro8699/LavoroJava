package Generici;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ListaGenerica {
	
	//Salva i dati della nel file specificato
	public  static <E> void salvaLista(String nomeFile, ArrayList<E> lista){
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
	public static <E> ArrayList<E> caricaLista(String nomeFile){
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
	public static <E> ArrayList<E> addElemento(E el, ArrayList<E> lista){
		
		if (!(lista.contains(el))) {
			lista.add(el);
		}
		else System.out.println("Elemento già esistente");
		
		return lista;
	}
	
	//Test
	public static <E> boolean rimuoviElemento(E el, ArrayList<E> lista ) {
		Iterator<E> iterator = lista.iterator();
		while (iterator.hasNext()){
			if (iterator.next().equals(el)){
				System.out.println("Elemento rimosso con successo");
				iterator.remove();
				return true;
			}
		}
		System.out.println("Elemento non trovato");
		return false;
	}
	
	//Verifica se un elemento è già Presente
	private static <E> boolean verificaElemento(E el, ArrayList<E> lista){
		Iterator<E> iterator = lista.iterator();
		while(iterator.hasNext()){
			E elemento = iterator.next();
			if(elemento.equals(el)){

				return true;
			}			
		}
		return false;
	}
	
			
}
