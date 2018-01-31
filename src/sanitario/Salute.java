package sanitario;

import java.io.Serializable;

import packageDipendenti.Dipendente;

public class Salute implements Serializable{

	private static final long serialVersionUID = -3435363010760330514L;
	public final short IDONEO = 0, IDONEO_PARZIALE = 1, INIDONEO = 2;
	private int statoDiSalute;
	Dipendente d;
	
	public Salute(Dipendente d) {
		this.d = d;
		statoDiSalute = IDONEO;	
	}
	
	public int getStatoDiSalute() {
		return statoDiSalute;
	}
	public void setDipendenteIdoneo() {
		statoDiSalute = IDONEO;
	}
	public void setDipendenteIdoneoParziale() {
		statoDiSalute = IDONEO_PARZIALE;
	}
	public void setDipendenteInidoneo() {
		statoDiSalute = INIDONEO;
	}
	
	public String toString () {
		if (statoDiSalute == IDONEO) return "Idoneo";
		if (statoDiSalute == IDONEO_PARZIALE) return "Idoneo Parziale";
		if (statoDiSalute == INIDONEO) return "Inidoneo";
		return "";
	}

}
