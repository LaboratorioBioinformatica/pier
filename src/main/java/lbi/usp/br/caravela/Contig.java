package lbi.usp.br.caravela;

import java.util.List;

public class Contig {
	
	private String reference;
	private String sequence;
	private List<Feature> features;
	private List<ReadOnContig> readsOnCotig;
	
	public Contig(String reference, String sequence, List<Feature> features, List<ReadOnContig> readsOnCotig) {
		this.reference = reference;
		this.sequence = sequence;
		this.features = features;
		this.readsOnCotig = readsOnCotig;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("reference: ")
		.append(reference)
		.append("\t")
		.append("Number of reads on contig: ")
		.append(readsOnCotig.size())
		.append("Number of features on contig: ")
		.append(features.size()).toString();
	}
	
	

}
