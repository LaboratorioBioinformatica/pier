package lbi.usp.br.caravela.config;

import java.io.File;

import lbi.usp.br.caravela.exeption.DomainValidateException;


public class SampleConfigFile {
	
	private static final int MIN_SAMPLE_LENGTH = 3;

	private String sample;
	private String contig;
	private String mapping;
	private TaxonomyFileConfig taxonomy;
	private FunctionalCofigFile functional;
	
	public SampleConfigFile(String sample, String contig, String mappingFilePath, TaxonomyFileConfig taxonomyFileConfig, FunctionalCofigFile functionalCofigFile) {
		setSample(sample);
		setContig(contig);
		setMappingFilePath(mappingFilePath);
		setTaxonomyFileConfig(taxonomyFileConfig);
		this.functional = functionalCofigFile;
	}
	
	public SampleConfigFile(String sampleName, String sampleDirectoryPath){
		try {
			setSample(sampleName);
			File folder = new File(sampleDirectoryPath);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					if (listOfFiles[i].getName().toLowerCase().startsWith("contig")) {
						setContig(listOfFiles[i].getAbsolutePath());
					}
					if (listOfFiles[i].getName().toLowerCase().startsWith("mapping")) {
						setMappingFilePath(listOfFiles[i].getAbsolutePath());
					}
					if (listOfFiles[i].getName().toLowerCase().startsWith("taxonomy")) {
						setTaxonomyFileConfig(new TaxonomyFileConfig(TaxonomyProvider.DEFAULT, listOfFiles[i].getAbsolutePath()));
					}
				}
			}
			this.functional = new FunctionalCofigFile(FunctionProvider.NO, null);
			
		} catch (Exception e) {
			throw new DomainValidateException("Requereds file not found!", e);
		}
		

	}
	
	public void validate(){
		validateSampleName(sample);
		validateContig(contig);
		validateMapping(mapping);
		validateTaxonomyFileConfig(taxonomy);
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
	
	public TaxonomyFileConfig getTaxonomy(){
		return taxonomy;
	}
	
	
	public FunctionalCofigFile getFunctionalCofigFile(){
		return functional;
	}
	
	private void setSample(String sample){
		validateSampleName(sample);
		this.sample = sample;
	}
	
	
	private void setContig(String contig){
		validateContig(contig);
		this.contig = contig;
		
	}
	
	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();
		toStringBuilder.append("Sample: ").append(getSampleName())
			.append("Contigs: ").append(getContigFilePath())
			.append("Mapping: ").append(getMappingFilePath())
			.append("Taxonomy: ").append(getTaxonomy().toString())
			.append("Functional: ").append(getFunctionalCofigFile().toString());
		return toStringBuilder.toString();
	}
	

	private void setMappingFilePath(String mappingFilePath){
		validateMapping(mappingFilePath);
		this.mapping = mappingFilePath;
	}
	
	private void setTaxonomyFileConfig(TaxonomyFileConfig taxonomyFileConfig){
		validateTaxonomyFileConfig(taxonomyFileConfig);
		this.taxonomy = taxonomyFileConfig;
	}
	
	private void validateTaxonomyFileConfig(TaxonomyFileConfig taxonomyFileConfig) {
		if(taxonomyFileConfig  == null ){
			throw new DomainValidateException("Invalid taxonomy file config");
		} 
	}

	private void validateSampleName(String sample) {
		if(sample  == null || sample.isEmpty() || sample.length() < MIN_SAMPLE_LENGTH){
			throw new DomainValidateException("Invalid sample name");
		} 
	}
	
	private void validateContig(String contig) {
		if(contig  == null || contig.isEmpty()){
			throw new DomainValidateException("Invalid contig file path");
		} 
	}
	
	private void validateMapping(String mapping) {
		if(mapping  == null || mapping.isEmpty()){
			throw new DomainValidateException("Invalid mapping file path");
		} 
	}


}
