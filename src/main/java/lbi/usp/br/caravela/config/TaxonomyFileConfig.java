package lbi.usp.br.caravela.config;

import lbi.usp.br.caravela.exeption.DomainValidateException;


public class TaxonomyFileConfig {
	
	private TaxonomyProvider provider;
	private String taxonomy;
	
	public TaxonomyFileConfig(TaxonomyProvider provider, String taxonomy) {
		setProvider(provider);
		setTaxonomy(taxonomy);
	}
	
	public void validate(){
		validateTaxonomyFilePath(taxonomy);
		validateTaxonomyProvider(provider);
		
	}
	
	
	private void setProvider(TaxonomyProvider provider) {
		validateTaxonomyProvider(provider);
		this.provider = provider;
	}

	private void setTaxonomy(String taxonomy) {
		validateTaxonomyFilePath(taxonomy);
		this.taxonomy = taxonomy;
	}

	public TaxonomyProvider getProviderName(){
		return provider;
	}
	
	public String getTaxonomyFilePath(){
		return taxonomy;
	}
	
	
	private void validateTaxonomyProvider(TaxonomyProvider taxonomyProvider) {
		if(taxonomyProvider  == null){
			throw new DomainValidateException("Invalid taxonomy provider");
		} 
	}
	
	private void validateTaxonomyFilePath(String taxonomy) {
		if(taxonomy  == null || taxonomy.isEmpty()){
			throw new DomainValidateException("Invalid taxonomy file path");
		} 
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof TaxonomyFileConfig)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		TaxonomyFileConfig taxonomyFileConfig = (TaxonomyFileConfig) obj;
		return this.provider.equals(taxonomyFileConfig.getProviderName()) && this.taxonomy.equals(taxonomyFileConfig.getTaxonomyFilePath()); 
	}

	
}
