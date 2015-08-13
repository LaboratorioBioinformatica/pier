package lbi.iq.usp.br.mgbl.mgbl.config;

import java.util.HashMap;
import java.util.List;

import org.apache.bcel.generic.LSTORE;

public class FunctionalCofigFile {
	
	private String provider;
	private String gff;
	private HashMap<String, String> files;
	
	public FunctionalCofigFile(String provider, String gff, HashMap<String, String> files) {
		this.provider = provider;
		this.gff = gff;
		this.files = files;
	}

}
