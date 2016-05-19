package lbi.usp.br.caravela.config;


public class TaxonomyFileConfig {
	private String provider;
	private String taxonomy;
	
	public TaxonomyFileConfig(String provider, String taxonomy) {
		this.taxonomy = taxonomy;
		this.provider = provider;
	}
	
	public String getProviderName(){
		return provider;
	}
	
	public String getTaxonomyFilePath(){
		return taxonomy;
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
