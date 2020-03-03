package MuzickiSimbol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TxtFajl {

	private String kk;
	
	public TxtFajl(Kompozicija k) {		
		
		kk=k.dohvKomp();
		
	}
	
	public void ispisiuIzlaz(String putanja) throws IOException {
		BufferedWriter writer=new BufferedWriter(new FileWriter(putanja+".txt"));
		writer.append(kk);
		writer.close();
	}
}
