package lbi.usp.br.caravela.config;

import java.util.HashMap;
import java.util.List;

import org.apache.bcel.generic.LSTORE;

public class FunctionalCofigFile {
	
	private static final String PHILO_DIST_FILE = "PhiloDistFile";
	private static final String GENE_PRODUCT_FILE = "GeneProductFile";
	
	private String provider;
	private String gff;
	private HashMap<String, String> files;
	
	public FunctionalCofigFile(String provider, String gff, HashMap<String, String> files) {
		this.provider = provider;
		this.gff = gff;
		this.files = files;
	}
	
	public String providerName(){
		return this.provider;
	}
	
	public String getGFFFilePath(){
		return this.gff;
	}
	
	public String getGeneProductFilePath(){
		return this.files.get(GENE_PRODUCT_FILE);
	}
	
	public String getPhiloDistFilePath(){
		return this.files.get(PHILO_DIST_FILE);
	}

}
