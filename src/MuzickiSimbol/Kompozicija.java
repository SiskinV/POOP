package MuzickiSimbol;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;


public class Kompozicija {

	public static int duzMyArray=2;
	private static Vector<Simbol> simboli=new Vector<Simbol>();
	private String komp="";
	
	//za mapu 
	
	class MyArray{
		
		String[] niz=new String[2];
		public MyArray() {};
		
		public void setNiz1(String a) {niz[0]=a;}
		public void setNiz2(String b) {niz[1]=b;}
		public String getNiz1() {return niz[0];}
		public String getNiz2() {return niz[1];}
		
		public String toString() {
			return niz[0]+" "+niz[1];
		}
	};
	
	//TREBA TI JOS JEDNA MAPA ZA OBRNUTO ZA KASNIJE!!!
	
	
	
	//KRAJ TE MAPE
	
	public static String imeKomp="";
	public static Map<Character,MyArray> mapa=new HashMap<Character,MyArray>();
	public static Map<Character,String> tasterUmidi= new HashMap<Character,String>();
	public static Map<String,Character> notaUtaster = new HashMap<String,Character>();
	public static Map<Character,String> tasterUnotu =  new HashMap<Character,String>();
	public static Map<String,String> notaUtaster1 = new HashMap<String,String>();
	public static Map<String,String> notaUmidi = new HashMap<String,String>();
	public static Map<String,String> midiUnotu = new HashMap<String,String>();
	public static Map<String,Character> midiUtaster= new HashMap<String,Character>();
	
	public Kompozicija()throws IOException {
		
		mapa("C:\\JavaCodes\\POOP projekat\\map.csv");
		
		//ispis za mapu provera

//		mapa.entrySet().forEach(entry->{
//			System.out.println(entry.getKey()+" "+entry.getValue());
//		});
	}
	
	
	public static void ocistiListuSimbola() {
		simboli.clear();
	}
	
	public static Vector<Simbol> dohvSim(){
		return simboli;
	}
	
	//Ucitavanje mape
	
	public void mapa(String ucitaj) {
		
		try {
			
			File fajl = new File(ucitaj);
			BufferedReader br=new BufferedReader(new FileReader(fajl));
			String st;
			
			Pattern pattern = Pattern.compile(",",Pattern.CASE_INSENSITIVE); 
			
			while ((st = br.readLine()) != null) {
			
				String[] rez = pattern.split(st);
			
				Character o=rez[0].charAt(0);
				MyArray ar=new MyArray();
				ar.setNiz1(rez[1]);
				ar.setNiz2(rez[2]);
				
				notaUtaster1.put(rez[1],rez[0]);
				notaUtaster.put(rez[1],o);
				mapa.put(o,ar);
				tasterUmidi.put(o,rez[2]);
				tasterUnotu.put(o,rez[1]);
				notaUmidi.put(rez[1],rez[2]);
				midiUnotu.put(rez[2],rez[1]);
				midiUtaster.put(rez[2],o);
			}
		}catch(Exception e) {
			
		}
		
	}
	
	void ucitajKompoziciju(String kompozicija)throws IOException {
		
		//System.out.println(notaUtaster.get("C2"));
		komp="";
		File pesma=new File(kompozicija);
		String st;
		
		BufferedReader br = new BufferedReader(new FileReader(pesma)); //ucitava celu komp u br iz koga izvlacim red po red
	
		while((st=br.readLine())!=null) komp=komp+st; //stavlja u komp celu kompoziciju 
		
		System.out.println(komp);
		
		Pattern pattern = Pattern.compile("([^\\[])|(\\[[^\\]]*\\])");
		Matcher m=pattern.matcher(komp);
		
		while(m.find()) {
			
			if(m.group(1)!=null) {
				//Prvo gledam sve ono sto je van zagrade znaci pauze i notu ako nije to onda je ili akrord ili spojene note
				if(m.group().charAt(0)==' ') {  //Pauza 1/8
					//System.out.println("Pauza");
					Simbol simbol=new Pauza(new Razlomak(1,8));
					simboli.add(simbol);
				}else if(m.group().charAt(0)=='|') { //Pauza 1/4
					//System.out.println("PauzaD");
					Simbol simbol=new Pauza(new Razlomak(1,4));
					simboli.add(simbol);
				}else { //Nota 1/4
					
					MyArray n=mapa.get(m.group().charAt(0));
					//System.out.println("Nota"+n.getNiz1());
					
					if(n.getNiz1().charAt(1)=='#')
					{
						int ok=n.getNiz1().charAt(2);
						Simbol simbol = new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),true);
						simboli.add(simbol);
					}else {
						int ok=n.getNiz1().charAt(1);
						Simbol simbol=new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),false);
						simboli.add(simbol);
					}
					
				}// kraj za notu 1/4
				
			}// kraj za prvu grupu
			else {
				//Akord ili spojene note!!!
				if(m.group().charAt(2)==' ') {
					
					int i=1;
					//spojene note
					while(m.group().charAt(i)!=']' && i<m.group().length()) {
						if(m.group().charAt(i)==' ') {
						i++;
						continue;
					}
					MyArray n=mapa.get(m.group().charAt(i));
					//System.out.println("Nota"+n.getNiz1());
					
					if(n.getNiz1().charAt(1)=='#') {
						int ok=n.getNiz1().charAt(2);
						Simbol simbol=new Nota(new Razlomak(1,8),ok,n.getNiz1(),n.getNiz2(),true);
						simboli.add(simbol);
						i++;
					}else {
						int ok=n.getNiz1().charAt(1);
						Simbol simbol=new Nota(new Razlomak(1,8),ok,n.getNiz1(),n.getNiz2(),false);
						simboli.add(simbol);
						i++;
					}
					}
					//System.out.println();
					
				}//kraj nota za redom sad nam ostaje akord
				else {
					//System.out.println("Akord!!!!!!");
					int i=1;
					Akord a=new Akord(new Razlomak(1,4));
					while(i<m.group().length() && m.group().charAt(i)!=']') {
						MyArray n=mapa.get(m.group().charAt(i));
						
						if(n.getNiz1().charAt(1)=='#') {
							int ok=n.getNiz1().charAt(2);
							Simbol simbol=new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),true);
							a.dodajSimbol(simbol);
					//		System.out.println("Nota"+n.getNiz1());
							i++;
						}else {
							int ok=n.getNiz1().charAt(1);
							Simbol simbol=new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),false);
							a.dodajSimbol(simbol);
					//		System.out.println("Nota"+n.getNiz1());
							i++;
							
						}
					}
					//System.out.println("Kraj akorda!");
					simboli.add(a);
				}
			}
			
		}
		
		
	} 
	
	public static int dohvBrSimbola() {
		return simboli.size();
	}
	
	public static void postaviImeKomp(String ime) {
		imeKomp=ime;
	}
	public static String dohvImeKomp() {
		return imeKomp;
	}
	public String dohvKomp() {
		return komp;
	}
	
	public static void main(String[] varg)throws IOException {
	
		//Kompozicija k=new Kompozicija("C:\\JavaCodes\\POOP projekat\\jingle_bells.txt");
		
		//postaviImeKomp("jingle_bells.txt");
		
		Kompozicija k=new Kompozicija();
		k.ucitajKompoziciju(imeKomp);
//		
//		Midi m =new Midi();
//		m.napraviFajl("C:\\JavaCodes\\POOP projekat\\test1", k);
//		
//		TxtFajl t=new TxtFajl(k);
//		t.ispisiuIzlaz("C:\\JavaCodes\\POOP projekat\\testTxt");
	}
}
