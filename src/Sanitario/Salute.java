package Sanitario;

import java.io.Serializable;

import packageDipendenti.Dipendente;

public class Salute implements Serializable{

	private static final long serialVersionUID = -3435363010760330514L;
	public final short IDONEO = 0, IDONEO_PARZIALE = 1, INIDONEO = 2;
	private int statoDisalute;
	Dipendente d;
	
	public Salute(Dipendente d) {
		this.d = d;
		statoDisalute = IDONEO;	
	}
	
	public int getStatoDiSalute() {
		return statoDisalute;
	}
	public void setDipendenteIdoneo() {
		statoDisalute = IDONEO;
	}
	public void setDipendenteIdoneoParziale() {
		statoDisalute = IDONEO_PARZIALE;
	}
	public void setDipendenteInidoneo() {
		statoDisalute = INIDONEO;
	}
	
	public String toString () {
		if (statoDisalute == IDONEO) return "Idoneo";
		if (statoDisalute == IDONEO_PARZIALE) return "Idoneo Parziale";
		if (statoDisalute == INIDONEO) return "Inidoneo";
		return "";
	}

}
