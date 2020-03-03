package MuzickiSimbol;

import java.util.*;

public class Akord extends Simbol{

	List<Simbol> akord=new ArrayList<Simbol>();
	
	public Akord(Razlomak tr) {
		super(tr);
	}
	
	public char Vrsta() {
		return 'A';
	}
	
	public void dodajSimbol(Simbol sim) {
		akord.add(sim);
	}
	
	public int getDuz() {return akord.size();}
	public Razlomak getTrajanje() {return trajanje;}
	public String getMidi() {return "100000";}
	public Simbol getSimbol(int j) {
		return akord.get(j);
	}
	public String toString() {
		
		int duz=getDuz();
		
		StringBuilder sb=new StringBuilder("[");
		
		for(int br=0;br<duz;br++)
			sb.append(akord.get(br));
		sb.append("]");
		return sb.toString();
	}
	
}
