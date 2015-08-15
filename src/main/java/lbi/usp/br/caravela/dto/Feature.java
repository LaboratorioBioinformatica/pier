package lbi.usp.br.caravela.dto;

public class Feature {
	private String source;
	private String type;
	private Integer start;
	private Integer end;
	private Integer strand;
	private PhiloDist philoDist;
	private GeneProduct geneProduct;
	
	public Feature(String source, String type, Integer start, Integer end, Integer strand, GeneProduct geneProduct, PhiloDist philoDist) {
		this.source = source;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.geneProduct = geneProduct;
		this.philoDist = philoDist;
	}

}
