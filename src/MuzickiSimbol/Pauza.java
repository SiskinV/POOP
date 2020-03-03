package MuzickiSimbol;


public class Pauza extends Simbol{

	private Razlomak r1=new Razlomak(1,4);
	
	public Pauza(Razlomak tr) {
		super(tr);
	}
	
	public String getMidi() {return "100000";}
	public int getDuz() {return 1;}
	public char Vrsta() {return 'P';}
	public Simbol getSimbol(int j) {return this;}
	public String toString() {
	
		if(jednaki(trajanje,r1)) return "-";
		else return "|";
	}

}
