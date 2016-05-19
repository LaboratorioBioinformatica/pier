package lbi.usp.br.caravela.config;

import java.util.HashMap;

public class FunctionalCofigFile {
	
	private String provider;
	private HashMap<String, String> files;
	
	public FunctionalCofigFile(String provider, HashMap<String, String> files) {
		this.provider = provider;
		this.files = files;
	}

	public String providerName(){
		return this.provider;
	}

	public HashMap<String, String> getFileList() {
		return files;
	}
	

}
