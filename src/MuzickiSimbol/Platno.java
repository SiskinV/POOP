package MuzickiSimbol;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Platno extends Canvas{
	
	private Vector<Simbol> sim=Kompozicija.dohvSim();
	public static Set<String> a=new HashSet<String>();
	
	public Platno() {}
	
	private int x=30,y=113,y1=133;
	private int h=40;
	private int n;
	
//	public void paint(Graphics g) {
//	
//		g.setColor(Color.BLACK);
//		g.drawRect(500, 100, 60, 60);
//	}
	

	
	public void pocetnoCrtanje() {
		repaint();
	}
	
	
	public void paint(Graphics g) {
		//if(Klavir.dohvSvira()) {
			int pomoc=MidiPlayer.dohvPomoc();
			n=20;
			for(int i=0;i<15 && pomoc+i<sim.size();i++) {
				
				
				Simbol s = sim.get(i+pomoc);
				
				if(s.Vrsta()=='N') {
				if(Simbol.jednaki(s.trajanje(), new Razlomak(1,8))) {
					g.setColor(Color.GREEN);
					g.fillRect(n, y, x, h);
					g.setColor(Color.BLACK);
					g.drawRect(n, y, x, h);
					FontMetrics fm = g.getFontMetrics();
					int   w = fm.stringWidth(Kompozicija.midiUnotu.get(s.getMidi()));
					int   h1 = fm.getAscent();
					
					if(Klavir.k == 1) {
						String upis = String.valueOf(Kompozicija.midiUnotu.get(s.getMidi()));
						g.drawString(upis, n+w, y+3*h1/2);
						
					}else if(Klavir.k==2) {
						String upis=String.valueOf(Kompozicija.midiUtaster.get(s.getMidi()));
						g.drawString(upis, n+w, y+3*h1/2);
					}
					n+=x;
				}//KRAJ ZA NOTU 1/8
				if(Simbol.jednaki(s.trajanje(), new Razlomak(1,4))) {
					g.setColor(Color.RED);
					g.fillRect(n, y, 2*x, h);
					g.setColor(Color.BLACK);
					g.drawRect(n, y,2* x, h);
					FontMetrics fm = g.getFontMetrics();
					int   w = fm.stringWidth(Kompozicija.midiUnotu.get(s.getMidi()));
					int   h1 = fm.getAscent();
					
					if(Klavir.k == 1) {
						String upis = String.valueOf(Kompozicija.midiUnotu.get(s.getMidi()));
						g.drawString(upis, n+2*w, y+3*h1/2);
						
					}else if(Klavir.k==2) {
						String upis=String.valueOf(Kompozicija.midiUtaster.get(s.getMidi()));
						g.drawString(upis, n+2*w, y+3*h1/2);
					}
					n+=2*x;
				}
				//KRAJ ZA NOTU
				}else if(s.Vrsta()=='P'){
					
					if(Simbol.jednaki(s.trajanje(), new Razlomak(1,8))) {
						g.setColor(Color.GREEN);
						g.fillRect(n, y, x, h);
						g.setColor(Color.BLACK);
						g.drawRect(n, y, x, h);
						n+=x;
				
					}//KRAJ ZA PAUZU 1/4
					if(Simbol.jednaki(s.trajanje(), new Razlomak(1,4))) {
						g.setColor(Color.RED);
						g.fillRect(n, y, 2*x, h);
						g.setColor(Color.BLACK);
						g.drawRect(n, y,2* x, h);
						n+=2*x;
					}
					//KRAJ ZA PAUZU	
					}else {
						//Ako nije nista od ova dva onda je akord
						int broj = s.getDuz();
						if(broj%2==0) {
							int pocetakPrvog=y1-(broj/2)*h;
							for(int j=0;j<broj;j++) {
								g.setColor(Color.RED);
								g.fillRect(n,pocetakPrvog+ h*j,2*x, h);
								g.setColor(Color.BLACK);
								g.drawRect(n, pocetakPrvog+h*j, 2*x, h);
								FontMetrics fm = g.getFontMetrics();
								Simbol poslednjaPomoc = s.getSimbol(j);
								int   w = fm.stringWidth(Kompozicija.midiUnotu.get(poslednjaPomoc.getMidi()));
								int   h1 = fm.getAscent();
								
								if(Klavir.k == 1) {
									String upis = String.valueOf(Kompozicija.midiUnotu.get(poslednjaPomoc.getMidi()));
									g.drawString(upis, n+2*w, pocetakPrvog+ h*j+3*h1/2);
									
								}else if(Klavir.k==2) {
									String upis=String.valueOf(Kompozicija.midiUtaster.get(poslednjaPomoc.getMidi()));
									g.drawString(upis, n+2*w, pocetakPrvog+ h*j+3*h1/2);
								}
							}
							n+=2*x;
						}else { //AKO JE NEPARAN BROJ NOTA U AKORDU
							int pocetakPrvog=y-(broj/2)*h;
							for(int j=0;j<broj;j++) {
								
								g.setColor(Color.RED);
								g.fillRect(n,pocetakPrvog+ h*j,2*x, h);
								g.setColor(Color.BLACK);
								g.drawRect(n, pocetakPrvog+h*j, 2*x, h);
								FontMetrics fm = g.getFontMetrics();
								Simbol poslednjaPomoc=s.getSimbol(j);
								int   w = fm.stringWidth(Kompozicija.midiUnotu.get(poslednjaPomoc.getMidi()));
								int   h1 = fm.getAscent();
								
								if(Klavir.k == 1) {
									String upis = String.valueOf(Kompozicija.midiUnotu.get(poslednjaPomoc.getMidi()));
									g.drawString(upis, n+2*w, pocetakPrvog+ h*j+3*h1/2);
									
								}else if(Klavir.k==2) {
									String upis=String.valueOf(Kompozicija.midiUtaster.get(poslednjaPomoc.getMidi()));
									g.drawString(upis, n+2*w, pocetakPrvog+ h*j+3*h1/2);
								}
							}
							n+=2*x;
						}
					}
				}
			}
		//}
	
	
	public void tastatura(String c) {
		//int pomoc=MidiPlayer.dohvPomoc();
		if(sim.get(MidiPlayer.dohvPomoc()).Vrsta()=='A') {
			if(a.isEmpty()) {
				Simbol akord=sim.get(MidiPlayer.dohvPomoc());
				for(int i=0;i<akord.getDuz();i++)
					a.add(akord.getSimbol(i).getMidi());
			}
			
			if(a.contains(c)) {
				a.remove(c);
			}
			if(a.isEmpty())
			{
				MidiPlayer.povPomoc();
				repaint();
			}
			
			
		}
		if(c.equals(sim.get(MidiPlayer.dohvPomoc()).getMidi())) {
			MidiPlayer.povPomoc();
			repaint();
		}while(sim.get(MidiPlayer.dohvPomoc()).Vrsta()=='P')
		{
			MidiPlayer.povPomoc();
			repaint();
		}
	}
}

