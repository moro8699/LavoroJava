package Turni;

import java.time.LocalTime;

import packageImpianti.Impianto;

public class TestPresenza {
	
		public TestPresenza(){
			LocalTime inizio = LocalTime.of(21, 0);
			LocalTime fine = LocalTime.of(6, 0);
			LocalTime pausa = LocalTime.of(0, 0);
		
			Presenza p = new PresenzaLavorativa(new Impianto("Firenze SMN"),"3T" ,"Notte Traghettatore", inizio, fine, pausa);
		
			ElencoPresenze.addElemento(p);
			
			System.out.println(p.impegno());
		}

}
