package Eccezioni;

public class ElementoGiaEsistente extends Exception {

	private static final long serialVersionUID = 3893024061766213107L;

	public ElementoGiaEsistente() {

		super ("Elemento gi� esistente nell'elenco");
		
	}
	
	public String toString(){
		return getMessage();
	}

}
