package MuzickiSimbol;


import javax.swing.JFrame;

import MuzickiSimbol.Kompozicija.MyArray;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class Klavir extends JFrame implements KeyListener,ActionListener,ItemListener{

	public static int expMidi=0,expTxt=0;
	public static int brojDirki=7;
	public static int brojOktava=5;
	public static int brojPDirki=5;
	private String[] note= {"C","D","E","F","G","A","B"};
	private String[] Pnote= {"C#","D#","F#","G#","A#"};
	private String[] okt = {"2","3","4","5","6"};
 	private static Vector<JButton> Dugmici = new Vector<JButton>();
	public Iterator iter = Dugmici.iterator();
 	
	//private MidiPlayer midPl;
	private static Kompozicija kompozicija;
	private JPanel prviP,drugiP,treciP;
	private JLayeredPane tastatura;
	private JButton load,play,stop,end,record,begining,export;
	private JTextField enter;
	private JCheckBox cbmidi,cbtxt;
	private JComboBox option;
	private String[] Soption= {"Taster","Nota"};
	private JLabel unos = new JLabel("Composition:");
	private static boolean svira=false,ucitanaKomp=false;
	public static int k=2;
	public static Platno platno=new Platno();
	public int brojac=0;
	Vector<Simbol> novi= new Vector<Simbol>();
	public String s="";
	
	public Klavir() {
		
		super("Piano");
		
		this.setBounds(100, 100, 1035, 800);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		
		add();
	}
	
	
	
	public static boolean dohvSvira() {
		return svira;
	}
	
	private void add() {
			
		prviP=new JPanel();
		prviP.setLayout(null);
		prviP.setBackground(Color.white);
		
		load = dugme("Load",300,15);
		play = dugme("Play",470,15);
		stop = dugme("Stop",640,15);
		end = dugme("End",810,15);
		record = dugme("Record",810,80);
		begining = dugme("Begining",300,80);
		export = dugme("Export",105,140);
		
		enter = polje("",100,15);
		unos.setBounds(15, 20,75,15);
		
		cbmidi = izbor("Midi",125,70,false);
		cbmidi.setName("Midi");
		cbtxt = izbor("Txt",190,70,false);
		cbtxt.setName("Txt");
		
		option = izbor1(Soption,550,80);
		
		prviP.add(load);
		prviP.add(play);
		prviP.add(stop);
		prviP.add(end);
		prviP.add(enter);
		prviP.add(unos);
		prviP.add(record);
		prviP.add(cbmidi);
		prviP.add(cbtxt);
		prviP.add(begining);
		prviP.add(option);
		prviP.add(export);
		
		this.add(prviP);
		

		this.add(platno);
		tastatura=keys(k);
		this.add(tastatura);

		
	}
	
	private JComboBox izbor1(String[] arr,int x,int y) {
		JComboBox novi = new JComboBox(arr);
		novi.setBounds(x, y, 170, 28);
		novi.setVisible(true);
		novi.addActionListener(this);
		return novi;
	}
	
	private JCheckBox izbor(String name,int x,int y,boolean on) {
		JCheckBox novi = new JCheckBox(name,on);
		novi.setBounds(x,y,50,50);
		novi.setBackground(Color.white);
		novi.setVisible(true);
		novi.addItemListener(this);
		return novi;
	}
	
	private JTextField polje(String name,int x,int y) {
		JTextField novo=new JTextField(name);
		novo.setBounds(x,y,170,28);
		novo.setVisible(true);	
		return novo;
	}
	
	private JButton dugme(String name,int x,int y) {
		JButton novi = new JButton();
		novi.setText(name);
		novi.setName(name);
		novi.setBounds(x, y,150,28);
		novi.setFont(new Font("Arial",Font.PLAIN,25));
		novi.setBackground(Color.BLACK);
		novi.setForeground(Color.RED);
		novi.setVisible(true);
		novi.addActionListener(this);
		novi.addKeyListener(this);
		return novi;
	}
	
	private JButton dirka(int i,int j,int x,int y,int k) {
		
		
		String ime= note[j].concat(okt[i]);
		JButton novi = new JButton(ime);
		novi.setVerticalAlignment(JLabel.BOTTOM);
		novi.setHorizontalAlignment(JLabel.CENTER);
		novi.setName(ime);
		//novi.setVisible(true);
		novi.setBounds(x, y, 28, 230);
		novi.setMargin(new Insets(0,0,0,0));
		//novi.setFont(new Font("Arial",Font.PLAIN,3));
		novi.addActionListener(this);
		novi.addKeyListener(this);
		novi.setActionCommand(ime);
		if(k==1) {
			//ako je 1 neka bude nota
			novi.setText(ime);
			//novi.setName(ime);
		}else if(k==2){
			//u suprotnom neka bude sta je na tastaturi			
			novi.setText(String.valueOf(Kompozicija.notaUtaster.get(ime)));
			//novi.setName(String.valueOf(Kompozicija.notaUtaster.get(ime)));
		}
	
		//System.out.println(novi.getText());
		Dugmici.add(novi);
		return novi;
	}
	
	private JButton povisenaDirka(int i,int j,int x,int y,int k) {
		
		String ime= Pnote[j]+okt[i];
		JButton novi = new JButton(ime);
		//System.out.println(ime);
		novi.setBackground(Color.BLACK);
		novi.setForeground(Color.WHITE);
		novi.setVerticalAlignment(JLabel.BOTTOM);
		novi.setHorizontalAlignment(JLabel.CENTER);
		novi.setName(ime);
		novi.setVisible(true);
		novi.setBounds(x, y, 20, 130);
		novi.setMargin(new Insets(0,0,0,0));
		novi.setFont(new Font("Arial",Font.PLAIN,7));
		novi.addActionListener(this);
		novi.addKeyListener(this);
		novi.setActionCommand(ime);
		if(k==1) {
			//ako je 1 neka bude nota
			novi.setText(ime);
		}else if(k==2){
			//u suprotnom neka bude sta je na tastaturi
			
			novi.setText(String.valueOf(Kompozicija.notaUtaster.get(ime)));
			
		}
	
		//System.out.println(novi.getText());
		Dugmici.add(novi);
		return novi;
	}
	
	private JLayeredPane keys(int k) {
		
		JLayeredPane board = new JLayeredPane();
		String name="";
		int x=20;
		int y=17;
		
//		JButton dugme = dirka(0,0,35,17,1);
//		board.add(dugme);
//		
		for(int i=0;i<brojOktava;i++)
			for(int j=0;j<brojDirki;j++) {
				
				int pom1=i;
				int pom2=j;
				int iz=(x+28*(pom1+1)*(pom2+1));
				//System.out.println(" "+ i + " "+j+" "+ iz);
				
				JButton dugme = dirka(i,j,x,y,k);
				x+=28;
				board.add(dugme,new Integer(1));
			}
		
		x=20;
		for(int i=0;i<brojOktava;i++)
			for(int j=0;j<brojPDirki;j++) {
				
				if(j==0) {
					x+=18;
					JButton dugme = povisenaDirka(i,j,x,y,k);
					x+=20;
					board.add(dugme,new Integer(2));
				}else if(j==1 || j==3) {
					x+=8;
					JButton dugme = povisenaDirka(i,j,x,y,k);
					x+=20;
					board.add(dugme,new Integer(2));
				}else if(j==2)
				{
					x+=36;
					JButton dugme = povisenaDirka(i,j,x,y,k);
					x+=20;
					board.add(dugme,new Integer(2));
				}else if(j==4) {
					x+=8;
					JButton dugme = povisenaDirka(i,j,x,y,k);
					x+=20;
					x+=18;
					board.add(dugme,new Integer(2));
				}
				
				
			}
		
		return board;
		
	}
	
	public int expMidi() {
		return expMidi;
	}
	public int expTxt() {
		return expTxt;
	}
	public void setExp(int sta,int vr) {
		sta=vr;
	}
	public static void main(String[] varg)
	{
		try {
			 kompozicija=new Kompozicija();//treba da stavis od koje kompozicije
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Klavir klavir= new Klavir();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char key=e.getKeyChar();
		System.out.println(key);
		
		if(brojac%2==1) {
			
			MyArray n=Kompozicija.mapa.get(key);
			//System.out.println("Nota"+n.getNiz1());
			
			if(n.getNiz1().charAt(1)=='#')
			{
				int ok=n.getNiz1().charAt(2);
				Simbol simbol = new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),true);
				novi.add(simbol);
			}else {
				int ok=n.getNiz1().charAt(1);
				Simbol simbol=new Nota(new Razlomak(1,4),ok,n.getNiz1(),n.getNiz2(),false);
				novi.add(simbol);
			}
			s+=key;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Character key = e.getKeyChar();
		for(int i=0;i<Kompozicija.tasterUmidi.size();i++)
			if(Dugmici.elementAt(i).getName().equals(Kompozicija.tasterUnotu.get(key)))
				Dugmici.elementAt(i).doClick();
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		JCheckBox b;
		String name="";
		Object obj = e.getSource();
		b=(JCheckBox)obj;
		name=b.getName();
		
		//System.out.println(expMidi+ " "+ expTxt);
		//System.out.println(name);
		if(name!="null") {
			
		if(name.equals("Midi"))
		{
			if(e.getStateChange()==1)
				expMidi=1;
			else
				expMidi=0;
			
		}else if(name.equals("Txt")) {
			if(e.getStateChange()==1)
				expTxt=1;
			else
				expTxt=0;
		}
		//System.out.println(expMidi+ " "+ expTxt);
		}else {
			System.out.println("Nesto ne valja ne prepoznaje kad kliknem");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command="";
		String name="";
		JButton b;
		
		
		try {
			
			Object obj = e.getSource();
			
			//AKO JE JCOMBOBOX
			if(obj instanceof JComboBox) {	
				JComboBox combo = (JComboBox) obj;
				
				if(((String)combo.getSelectedItem()).equals("Nota")) {
					k=1;
					
					for(int i=0;i<Dugmici.size();i++)
					{
						
						JButton pomocni = Dugmici.get(i);
						Character c =pomocni.getText().charAt(0);
						Dugmici.get(i).setText(String.valueOf(Kompozicija.tasterUnotu.get(c)));
						//Dugmici.get(i).setName(String.valueOf(Kompozicija.tasterUnotu.get(c)));
					}
				}else if(((String)combo.getSelectedItem()).equals("Taster")){
					k=2;
					
					for(int i=0;i<Dugmici.size();i++) {
						
						JButton pomocni=Dugmici.get(i);
						String s=pomocni.getText();
						//Dugmici.get(i).setName(String.valueOf(Kompozicija.notaUtaster.get(s)));
						Dugmici.get(i).setText(String.valueOf(Kompozicija.notaUtaster.get(s)));
					}
				}
			//KRAJ JCOMBOBOX
			//ONDA JE NEKI JBUTTON	
					
			}else {
				MidiPlayer midiPl = new MidiPlayer();
				b=(JButton) obj;
				name=b.getName();
				
			if(name.equals("Load")){
				
				//skinem sve ako je vec bila neka ranije ucitana i ucitam drugu
				ucitanaKomp=true;
				Kompozicija.postaviImeKomp(enter.getText());
				String putanja = "C:\\JavaCodes\\POOP projekat\\"+enter.getText()+".txt";
				try {
					Kompozicija.ocistiListuSimbola();
					kompozicija.ucitajKompoziciju(putanja);
					platno.pocetnoCrtanje();
					System.out.println("Uspesno je ucitana komp  "+Kompozicija.dohvBrSimbola());
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(name.equals("Play")) {
				if(ucitanaKomp==true && svira==false) {
					//napravi i novi kanvas
					svira=true;
					midiPl.kreni();
					midiPl.start();
					
				}else {
					System.out.println("nije ucitana komp ili vec svira");
				}
				 
			}else if(name.equals("Stop")) {
				if(ucitanaKomp==true && svira==true) {
					svira=false;
					ucitanaKomp=false;
					midiPl.stani();
				}else {
					System.out.println("Nije ukljucena kompozicija ili ucitana");
				}
			}else if(name.equals("End")) {
				ucitanaKomp=false;
				svira=false;
				midiPl.zaustavi();
				platno.repaint();
				System.out.println("Ucitaj novu kompoziciju");
				
			}else if(name.equals("Export")) {
				if(expTxt==1) {
					TxtFajl t=new TxtFajl(kompozicija);
					t.ispisiuIzlaz("C:\\JavaCodes\\POOP projekat\\testTxt3");
				}
				if(expMidi==1) {
					Midi m =new Midi();
					m.napraviFajl("C:\\JavaCodes\\POOP projekat\\test2",Kompozicija.dohvSim());
				}
			}else if(name.equals("Record")){
					brojac++;
					
					if(brojac%2==0) {
						Midi m=new Midi();
						m.napraviFajl("C:\\JavaCodes\\POOP projekat\\test2", novi);
						novi.clear();
						BufferedWriter writer=new BufferedWriter(new FileWriter("C:\\JavaCodes\\POOP projekat\\test3.txt"));
						writer.append(s);
						writer.close();
					}
					
				
				}else{
				//Ako nije nista od ovih gore dugmica znaci da je neka dirka
				
				MidiPlayer midPl=new MidiPlayer();
				command = b.getActionCommand();
				//System.out.println(command +" pusices ga");
				if(k==1) {
					System.out.println(command);
					midPl.play(Integer.parseInt(Kompozicija.notaUmidi.get(command)), 100);
					
					String pom = Kompozicija.notaUmidi.get(command);
					platno.tastatura(pom);
					
				}else if(k==2) {
					
					//Character pom = command.charAt(0);
					//System.out.println(command+ " "+ pom + " "+ Kompozicija.tasterUmidi.get(pom));
					midPl.play(Integer.parseInt(Kompozicija.notaUmidi.get(command)), 100);

					String pom = Kompozicija.notaUmidi.get(command);
					platno.tastatura(pom);
				}
				
				
			}
		}
		} catch (MidiUnavailableException e1) {
			// TODO Auto-generated catch block
			System.out.println("Midi greska kada ne moze da ga nadje");
			e1.printStackTrace();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	

}


















