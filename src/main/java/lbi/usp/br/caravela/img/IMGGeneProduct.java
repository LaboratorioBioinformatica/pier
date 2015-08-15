package lbi.usp.br.caravela.img;

public class IMGGeneProduct {
	
	private static final int GENE_ID_LOCATION = 0;
	private static final int PRODUCT_NAME_LOCATION = 1;
	private static final int SOURCE = 2;
	
	private static final String TAB = "\t";
	
	private String geneId;
	private String productName;
	private String source;
	
	public IMGGeneProduct(String line) {
		String[] lineSplitted = line.split(TAB);
		this.geneId = lineSplitted[GENE_ID_LOCATION];
		this.productName = lineSplitted[PRODUCT_NAME_LOCATION];
		this.source = lineSplitted[SOURCE];
	}

	public String getGeneId() {
		return geneId;
	}

	public String getProductName() {
		return productName;
	}
	
	public String getSource(){
		return source;
	}
	
}
