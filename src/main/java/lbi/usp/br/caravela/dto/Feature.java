package lbi.usp.br.caravela.dto;

import java.util.List;

public class Feature {
	private final String type;
	private final Integer start;
	private final Integer end;
	private final Integer strand;
	private final String sequence;
	private final Taxon taxon;
	private List<FeatureAnnotation> annotations;
	private final PhiloDist philoDist;
	private final GeneProduct geneProduct;
	
	

	public Feature(String type, Integer start, Integer end, Integer strand, String sequence, Taxon taxon, List<FeatureAnnotation> annotations, GeneProduct geneProduct, PhiloDist philoDist) {
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

	public String getType() {
		return type;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getStrand() {
		return strand;
	}

	public String getSequence() {
		return sequence;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	public List<FeatureAnnotation> getAnnotations() {
		return annotations;
	}

	public PhiloDist getPhiloDist() {
		return philoDist;
	}

	public GeneProduct getGeneProduct() {
		return geneProduct;
	}
	
}
