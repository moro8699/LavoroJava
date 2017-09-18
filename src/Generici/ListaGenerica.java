package Generici;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ListaGenerica<E> {

	protected  ArrayList<E> lista = null;
	
	public ListaGenerica (ArrayList<E> lista) {
		setLista(lista);
	}
	
	public void setLista(ArrayList<E> l) {
			lista = l;
	}
	
	//Salva i dati della nel file specificato
	public  void salvaLista(String nomeFile){
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
	public void caricaLista(String nomeFile){
		lista = new ArrayList<E>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			lista = (ArrayList<E>) ois.readObject();
		}
		catch(Exception e){}
	}		
			
	public boolean addElemento(E el){
		Iterator<E> iterator = lista.iterator();
		while(iterator.hasNext()){
			E elemento = iterator.next();
			if(elemento.equals(el)){
				System.out.println("Elemento già esistente");
				return false;
			}			
		}
		lista.add(el);
		return true;
	}
			
	@SuppressWarnings("unchecked")
	public boolean rimuoviElemento(Object obj) {
		Iterator<E> iterator = lista.iterator();
		while (iterator.hasNext()){
			if (iterator.next().equals((E) obj)){
				System.out.println("Elemento rimosso con successo");
				iterator.remove();
				return true;
			}
		}
		System.out.println("Elemento non trovato");
		return false;
	}
	
			
}
