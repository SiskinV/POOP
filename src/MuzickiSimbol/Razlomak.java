package MuzickiSimbol;

public class Razlomak {

	private int br,im;
	
	
	public Razlomak(int br,int im) {
		this.br=br;
		this.im=im;
	}
	public Razlomak(int br) {
		this.br=br;
		this.im=8;
	}
	public Razlomak() {
		br=1; im=8;
	}
	
	public int dohvIm() {return im;}
	public int dohvBr() {return br;}
	
	public String toString() {
		return br+"/"+im+" ";
	}
	
	public boolean jednaki(Razlomak a1,Razlomak a2) {
		int br1=a1.dohvBr(),br2=a2.dohvBr(),im1=a1.dohvIm(),im2=a2.dohvIm();
		if(br1==br2 && im1==im2) return true;
		if(br1*im2-br2*im1 == 0) return true;
		return false;
	}
	
}




