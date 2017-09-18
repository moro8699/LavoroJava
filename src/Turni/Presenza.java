package Turni;

import java.time.LocalTime;

import packageImpianti.Impianto;

public abstract class Presenza {
	
	protected Impianto i = null;
	protected String identificativo = "";
	protected LocalTime inizio = null, fine = null, pausa = null;
	
	public Presenza (Impianto i, String identificativo, LocalTime inizio, LocalTime fine, LocalTime pausa) {	
		this.i = i;
		this.identificativo = identificativo;
		this.inizio = inizio;
		this.fine = fine;
		this.pausa = pausa;	
	}
	
	public Presenza (Impianto i, String identificativo, LocalTime inizio, LocalTime fine){
		this(i, identificativo, inizio, fine, LocalTime.MIN);
	}
	
	public Impianto getImpianto(){
		return i;
	}
	
	public String getIdentificativo(){
		return identificativo;
	}
	
	public LocalTime impegno(){
		int oreImpegno =0, minImpegno =0;
		oreImpegno = differenzaOre(inizio, fine) - pausa.getHour();
		minImpegno = differenzaMinuti(inizio.getMinute(), fine.getMinute());
		
		return togliPausaPranzo(LocalTime.of(oreImpegno, minImpegno), pausa);
				
	}
	
	private int differenzaMinuti(int minInizio, int minFine){
		
		if (minFine<minInizio){
			return (60-minInizio)+ minFine;
		} else {
			return minFine-minInizio;
		}
	}
	
	private int differenzaOre(LocalTime inizio, LocalTime fine){
		byte riporto =0;
		if (fine.getMinute()<inizio.getMinute())riporto++;
		if (fine.getHour()<inizio.getHour()) return ((24 - inizio.getHour()) + fine.getHour()) + riporto;
		else return fine.getHour() - inizio.getHour() + riporto;
	}
	
	public LocalTime togliPausaPranzo(LocalTime impegno, LocalTime pausaPranzo){
		byte riporto=0;
		int oreImpegno =0, minutiImpegno=0;
		
		if (impegno.getMinute()< pausaPranzo.getMinute()) {
			riporto++;
			minutiImpegno = (60-pausaPranzo.getMinute())+impegno.getMinute();
		}else{minutiImpegno = impegno.getMinute()-pausaPranzo.getMinute();}
		oreImpegno = impegno.getHour()-riporto;
		
		return LocalTime.of(oreImpegno, minutiImpegno);
		
	}
}
