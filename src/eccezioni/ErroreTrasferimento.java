package eccezioni;

public class ErroreTrasferimento extends Exception {

	private static final long serialVersionUID = -9203571389968245375L;
	
	public ErroreTrasferimento() {
		super("Problema con il Trasferimento");
	}

	public String toString() {
		return getMessage();
	}
}
