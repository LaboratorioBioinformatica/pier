package lbi.usp.br.caravela.dto;

public class GeneProduct {
	
	private final String product;
	private final String source;
	
	public GeneProduct(String product, String source) {
		this.product = product;
		this.source = source;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof GeneProduct)){
			return false;
		}
		if(obj == this){
			return true;
		}
		
		GeneProduct geneProduct = (GeneProduct) obj;
		return this.product.equals(geneProduct.product) && this.source.equals(geneProduct.source);
	}
	
	
	

}
