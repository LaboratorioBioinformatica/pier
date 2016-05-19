package lbi.usp.br.caravela.config;

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
