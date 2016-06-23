package lbi.usp.br.caravela.dto;

import java.util.List;

public class Feature {
	
	private final String source;
	private final String type;
	private final Integer start;
	private final Integer end;
	private final Integer strand;
	private final String sequence;
	private final Taxon taxon;
	private List<Annotation> annotations;
	private final PhiloDist philoDist;
	private final GeneProduct geneProduct;
	
	
	public Feature(String source, String type, Integer start, Integer end, Integer strand, String sequence, Taxon taxon, List<Annotation> annotations, GeneProduct geneProduct, PhiloDist philoDist) {
		this.source = source;
		this.type = type;
		this.start = start;
		this.end = end;
		this.strand = strand;
		this.sequence = sequence;
		this.taxon = taxon;
		this.annotations = annotations;
		this.geneProduct = geneProduct;
		this.philoDist = philoDist;
	}

}
