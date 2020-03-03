package MuzickiSimbol;

import javax.sound.midi.*;
import java.io.*;
import java.util.Vector;

public class Midi{

	private static final int time=25;
	private Razlomak r1=new Razlomak(1,8);
	
	public boolean napraviFajl(String putanja,Vector<Simbol> a) {
			long actionTime = 1;
			try {
				
				Sequence sekvenca = new Sequence(javax.sound.midi.Sequence.PPQ, 24);


				Track t = sekvenca.createTrack();

				byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
				SysexMessage sm = new SysexMessage();
				sm.setMessage(b, 6);
				MidiEvent me = new MidiEvent(sm, (long) 0);
				t.add(me);

				
				MetaMessage mt = new MetaMessage();
				byte[] bt = { 0x02, (byte) 0x00, 0x00 };
				mt.setMessage(0x51, bt, 3);
				me = new MidiEvent(mt, (long) 0);
				t.add(me);

				
				mt = new MetaMessage();
				String TrackName = new String("midifile track");
				mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
				me = new MidiEvent(mt, (long) 0);
				t.add(me);

				
				ShortMessage mm = new ShortMessage();
				mm.setMessage(0xB0, 0x7D, 0x00);
				me = new MidiEvent(mm, (long) 0);
				t.add(me);

				
				mm = new ShortMessage();
				mm.setMessage(0xB0, 0x7F, 0x00);
				me = new MidiEvent(mm, (long) 0);
				t.add(me);

				
				mm = new ShortMessage();
				mm.setMessage(0xC0, 0x00, 0x00);
				me = new MidiEvent(mm, (long) 0);
				t.add(me);
				//Upisivanje u fajl
				for (int i = 0; i <a.size(); i++) {
					//Ako je pauza
					if (a.get(i).Vrsta()=='P') {
						if (jednaki(a.get(i).trajanje(),r1))
							actionTime+=time;
						else actionTime +=2*time;
					}
					else if (a.get(i).Vrsta()=='N') {
					
						String midi1=a.get(i).getMidi();
						int midi=Integer.parseInt(midi1);
						mm = new ShortMessage();
						mm.setMessage(0x90, midi, 0x60); 
						me = new MidiEvent(mm, actionTime);
						t.add(me);

						if (jednaki(a.get(i).trajanje(),r1)) actionTime+=time;
						else actionTime+=2*time;
						
						mm = new ShortMessage();
						mm.setMessage(0x80, midi, 0x40);
						me = new MidiEvent(mm, actionTime);
						t.add(me);
					}
					else {//Akord
						for (int j = 0; j < a.get(i).getDuz(); j++) {
							
							Simbol sim=a.get(i).getSimbol(j);
							
							String midi1 = sim.getMidi();
							int midi=Integer.parseInt(midi1);
							mm = new ShortMessage();
							mm.setMessage(0x90, midi, 0x60); 
							me = new MidiEvent(mm, actionTime);
							t.add(me);
							mm = new ShortMessage();
							mm.setMessage(0x80, midi, 0x40);
							me = new MidiEvent(mm, actionTime+2*time);
							t.add(me);
						}
						actionTime += 2*time;
					} 
				}
				
				mt = new MetaMessage();
				byte[] bet = {}; 
				mt.setMessage(0x2F, bet, 0);
				me = new MidiEvent(mt, (long) 140);
				t.add(me);

				File file = new File(putanja+".midi");
				MidiSystem.write(sekvenca, 1, file);
				return true;
			} 
			catch (Exception e) {
				return false;
			} 
		}
	
	public boolean jednaki(Razlomak a1,Razlomak a2) {
		int br1=a1.dohvBr(),br2=a2.dohvBr(),im1=a1.dohvIm(),im2=a2.dohvIm();
		if(br1==br2 && im1==im2) return true;
		if(br1*im2-br2*im1 == 0) return true;
		return false;
	}
}
