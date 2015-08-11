package lbi.iq.usp.br.mgbl.mgbl;

import java.util.List;

public class Contig {
	
	private String reference;
	private String sequence;
	private List<ReadOnContig> readsOnCotig;
	
	public Contig(String reference, String sequence, List<ReadOnContig> readsOnCotig) {
		this.reference = reference;
		this.sequence = sequence;
		this.readsOnCotig = readsOnCotig;
	}
	
	

}
