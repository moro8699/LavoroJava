package Turni;

import java.time.LocalTime;

public class Assenza {
	
	private String identificativo = "";
	private final LocalTime INIZIO = LocalTime.MIDNIGHT, FINE = LocalTime.MAX;
	
	public Assenza(String identificativo) {
		this.identificativo = identificativo;	
	}
	
	public void setIdentifiicativo(String i){
		identificativo = i;
	}
	
	public String getIdentificativo(){
		return identificativo;
	}
	
	public LocalTime getINIZIO() {
		return INIZIO;
	}

	public LocalTime getFINE() {
		return FINE;
	}	
	@Override
	public boolean equals (Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Assenza)) return false;  
		Assenza a = (Assenza) obj;		
		return (a.getIdentificativo().equals(this.identificativo));		
	}



}
