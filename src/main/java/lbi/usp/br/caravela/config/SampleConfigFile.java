package lbi.usp.br.caravela.config;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SampleConfigFile {
	
	private String sample;
	private String contig;
	private String mapping;
	private TaxonomyFileConfig taxonomy;
	private FunctionalCofigFile functional;
	
	public SampleConfigFile(String sample, String contig, String mappingFilePath, TaxonomyFileConfig taxonomyFileConfig, FunctionalCofigFile functionalCofigFile) {
		this.sample = sample;
		this.contig = contig; 
		this.mapping = mappingFilePath;
		this.taxonomy = taxonomyFileConfig;
		this.functional = functionalCofigFile;
	}
	
	public String getSampleName(){
		return sample;
	}
	
	public String getContigFilePath(){
		return contig;
	}
	
	public String getMappingFilePath(){
		return mapping;
	}
	
	public String getTaxonomyFilePath(){
		return taxonomy.getTaxonomyFilePath();
	}
	
	public String getGFFFilePath(){
		return functional.getGFFFilePath();
	}
	
	public String getGeneProductFilePath(){
		return functional.getGeneProductFilePath();
	}
	
	public String getPhiloDistFilePath(){
		return functional.getPhiloDistFilePath();
	}
	
	
	
	public static void main(String[] args) {
		TaxonomyFileConfig taxonomyFileConfig = new TaxonomyFileConfig("mytaxa", "/data/mgb/mytaxa/ZC3DAY01IQMSIPER01R1-mytaxa-output.txt");
		HashMap<String, String> fileList = new HashMap<String, String>();
		fileList.put("GeneProductFile", "/data/mgb/annotation/ZC3b-day-01-3300002194-gene_product.txt");
		fileList.put("PhiloDistFile", "/data/mgb/annotation/ZC3b-day-01-3300002194-phylodist.txt");
		
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile("img_m", "/data/mgb/annotation/ZC3b-day-01-3300002194.gff", fileList);
		
		SampleConfigFile sampleConfigFile = new SampleConfigFile("zc3b-day-01", "/data/mgb/contig/ZC3b-day-01-3300002194.fasta", "/data/mgb/mapping/ZC3b-day-01-sorted.bam", taxonomyFileConfig, functionalCofigFile);
		 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 
		System.out.println(gson.toJson(sampleConfigFile));
		
		
	}
	
	
	

}
