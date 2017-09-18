package packageImpianti;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;

import packageDipendenti.Dipendente;
import packageDipendenti.ListaDipendenti;
public class Impianti implements Serializable {
	
	private static final long serialVersionUID = 5944774531916148934L;
	private static final String FILE_LISTA_IMP = "./SaveFiles/listaImpianti.man";
	private static ArrayList<Impianto> listaImpianti;	
	private Impianti(){}
	
	public static ArrayList<Impianto> getListaImpianti() {
		return listaImpianti;
	}	
	
	//Salva i dati della nel file specificato
	public static void salvaLista(String nomeFile){
		ObjectOutputStream oss;
		try{
			oss = new ObjectOutputStream(new FileOutputStream(nomeFile));
			oss.writeObject(listaImpianti);
			oss.close();
		}
		catch(Exception e){}
	} 	
	
	//Carica i dati della rubrica dal file specificato
	@SuppressWarnings("unchecked")
	public static void apriRub(String nomeFile){
		listaImpianti = new ArrayList<Impianto>();
		ObjectInputStream ois;
		try{
			ois = new ObjectInputStream(new FileInputStream(nomeFile));
			listaImpianti = (ArrayList<Impianto>) ois.readObject();
		}
		catch(Exception e){}
	}
	
	public static DefaultListModel<String> arrListaImp(){
		DefaultListModel<String> model = new DefaultListModel<>();
		Iterator<Impianto> i = listaImpianti.iterator();
		while (i.hasNext()){
			Impianto temp = i.next();
			model.addElement(temp.getNomeImpianto());
		}
		return model;
		
	}
	
	public static String[] modelLista() {
		String[] lista = new String[listaImpianti.size()];
		Iterator<Impianto> i = listaImpianti.iterator();
		int c = 0;
		while(i.hasNext()){
			Impianto temp = i.next();
			lista[c++] = temp.toString(); 
		}
		return lista;
	}
	
	public static boolean aggiungiImpianto(Impianto i){
		
		Iterator<Impianto> iterator = listaImpianti.iterator();
		while(iterator.hasNext()){
			Impianto temp = (Impianto) iterator.next();
			if(temp.equals(i)){
				System.out.println("Impianto già presente");
				return false;
			}
		}
		listaImpianti.add(i);
		salvaLista(FILE_LISTA_IMP);
		return true;
		
	}
	
	public static void cancellaModel(String nomeImpianto){
		for (int i=0; i<ImpiantiSW.getModelImpianti().size(); i++){
			if (ImpiantiSW.getModelImpianti().getElementAt(i) == nomeImpianto) 
				ImpiantiSW.getModelImpianti().remove(i);
		}
	}
	
	public static boolean rimuoviImpianto(Impianto i){
		
		Iterator<Impianto> iter = listaImpianti.iterator();
		while (iter.hasNext()){
			Impianto temp = iter.next();
			if (i.equals(temp)) {
				svuotaImpianto(temp);
				cancellaModel(i.toString());
				listaImpianti.remove(temp);
				return true;
			}	
		}
		System.out.println("Impianto non presente");
		return false;
	}
	
	public static void svuotaImpianto (Impianto i){
		Iterator<Dipendente> iteratorD = ListaDipendenti.getListaDipendenti().iterator();
		while (iteratorD.hasNext()){
			Dipendente temp = iteratorD.next();
			if (i.equals(new Impianto (temp.getImpiantoDiAppartenenza())))
				i.rimuoviDipendente(temp);
		}
	}
	
	public static Impianto getImpiantoDaModel (String impianto) {
		Iterator<Impianto> iterator = listaImpianti.iterator();
		while(iterator.hasNext()) {
			Impianto i = iterator.next();
			if (i.getNomeImpianto().equals(impianto)) return i;
		}
		return null;		
	}

	public static String getFileLista() {
		return FILE_LISTA_IMP;
	}

	
}
