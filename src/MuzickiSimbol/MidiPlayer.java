package MuzickiSimbol;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class MidiPlayer extends Thread{
	
	private boolean radi=false;
	private static final int DEFAULT_INSTRUMENT = 1;
 	private MidiChannel channel;
 	private static int pomoziMi=0;
 	
 	public static int dohvPomoc() {
 		return pomoziMi;
 	}
 	public static void povPomoc(){
 		pomoziMi++;
 	}
 	public MidiPlayer() throws MidiUnavailableException {
 			this(DEFAULT_INSTRUMENT);
 	}
 	public MidiPlayer(int instrument) throws MidiUnavailableException {
 		channel = getChannel(instrument);
 	}
 	public void play(final int note) {
 		channel.noteOn(note, 50);
 	}
 
 	public void release(final int note) {
 		channel.noteOff(note, 50);
 	}
 
 	public void play(final int note, final long length) throws InterruptedException {
	 play(note);
	 Thread.sleep(length);
	 release(note);
 	}
 
 	public synchronized void playSimbol(Simbol sim,long length)throws InterruptedException{
 		if(sim.Vrsta()=='P') {
 			
 			Thread.sleep(length);
 			
 		}else if(sim.Vrsta()=='N') {
 			String pom=sim.getMidi();
 			//System.out.println(pom);
 			//System.out.println(pom+" "+ Kompozicija.notaUmidi.get(pom));
 			int mid =Integer.parseInt(pom);
 			play(mid);
 			Thread.sleep(length);
 			release(mid);
 		}else {
 			for(int i=0;i<sim.getDuz();i++)
 			{
 				String pom=sim.getSimbol(i).getMidi();
 	 			int mid =Integer.parseInt(pom);
 	 			play(mid);
 			}
 			Thread.sleep(length);
 			for(int i=0;i<sim.getDuz();i++)
 			{
 				String pom=sim.getSimbol(i).getMidi();
 				int mid=Integer.parseInt(pom);
 				release(mid);
 			}
 		}
 		
 	}
 	
 	public synchronized void svirajKompoziciju() throws InterruptedException {
 		Vector<Simbol> sim = Kompozicija.dohvSim();
 		
 		for(int i=pomoziMi;i<sim.size();pomoziMi++,i++)
	{		synchronized (this) {while(!radi) wait();}
 			Razlomak duzina=sim.get(pomoziMi).trajanje();
 			
 			if(Simbol.jednaki(duzina,new Razlomak(1,4))) {
 				playSimbol(sim.get(pomoziMi),220);
 				Klavir.platno.repaint();
			}else if(Simbol.jednaki(duzina,new Razlomak(1,8))){
 				playSimbol(sim.get(pomoziMi),110);
 				Klavir.platno.repaint();
 			}
 			
 			
 			sleep(1);
 		}
 		Klavir.platno.repaint();
 		//pomoziMi++;
 		Thread.currentThread().interrupt();
 	}
 	
 	public void run() {
 		try {
 		while(!Thread.interrupted()) {
 			synchronized(this) {while(!radi) wait();}
 			svirajKompoziciju();
 			sleep(100);
 		}}catch(InterruptedException e) {}catch(Exception e) {e.printStackTrace();}
 	}
 	
 	private static MidiChannel getChannel(int instrument) throws MidiUnavailableException {
 
	Synthesizer synthesizer = MidiSystem.getSynthesizer();
	synthesizer.open();
	return synthesizer.getChannels()[instrument];
 	}
 	
 	public synchronized void kreni() {radi=true;}
 	public synchronized void stani() throws IOException {radi=false;Kompozicija.ocistiListuSimbola();}
 	public synchronized void zaustavi() {interrupted(); Kompozicija.ocistiListuSimbola();pomoziMi=0;}

 	public static void main(String[] args) throws Exception {
 		MidiPlayer player = new MidiPlayer();
 		Scanner scanner = new Scanner(System.in);
 		int note;
 		while (!Thread.currentThread().isInterrupted()) {
 			System.out.print("Note (1..127) : ");
		 note = scanner.nextInt();
		 player.play(note, 200);
 		}
 		scanner.close();
 }
}