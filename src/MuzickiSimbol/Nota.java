package MuzickiSimbol;

public class Nota extends Simbol{
	
	private int ok;
	private String v,mind;
	private boolean povisena=false;
	
	//ok je koja je oktava,mind je midi br a v je nota 
	
	public Nota(Razlomak tr,int ok,String v,String mind,boolean povisena) {
		super(tr);
		this.ok=ok;
		this.v=v;
		this.mind=mind;
		this.povisena=povisena;
	}
	
	public void povisi() {povisena=true;}
	public int getOk() {return ok;}
	public String getV() {return v;}
	public int getDuz() {return 1;}
	public boolean getPovisena() {return povisena;}
	public String getMidi() {return mind;}
	public char Vrsta() {return 'N';}
	public Simbol getSimbol(int j) {return this;}

	@Override
	public String toString() {
		
		if(povisena) {
			
			String o=Integer.toString(ok);
			return v+"#"+o;
			
		}else {

			String o=Integer.toString(ok);
			return v+o;
		}
	}

	
}

