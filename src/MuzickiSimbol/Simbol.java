package MuzickiSimbol;

public abstract class Simbol {
	
	protected Razlomak trajanje;
	
	
	public Simbol(Razlomak trajanje) {
		this.trajanje=trajanje;
	} 
	
	public Razlomak trajanje() {return trajanje;}
	public abstract char Vrsta();
	public abstract String toString();
	public abstract String getMidi();
	//public abstract String getNota();
	public abstract int getDuz();
	public abstract Simbol getSimbol(int i);
	
	public static boolean jednaki(Razlomak a1,Razlomak a2) {
		int br1=a1.dohvBr(),br2=a2.dohvBr(),im1=a1.dohvIm(),im2=a2.dohvIm();
		if(br1==br2 && im1==im2) return true;
		if(br1*im2-br2*im1 == 0) return true;
		return false;
	}
}
