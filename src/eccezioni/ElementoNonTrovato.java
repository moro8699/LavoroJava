package eccezioni;

public class ElementoNonTrovato extends Exception {

	private static final long serialVersionUID = -5759978354086671101L;

	public ElementoNonTrovato() {
		super("Elemento non Presente nella Lista");
	}

	public String toString(){
		return getMessage();
	}

}
