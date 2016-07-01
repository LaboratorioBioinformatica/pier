package lbi.usp.br.caravela.dto;

public class PhiloDist {

	private Long geneOID;
	private Long taxonOID;
	private Double identity;
	private String lineage;
	
	public PhiloDist(Long geneOID, Long taxonOID, Double identity, String lineage) {
		this.geneOID = geneOID;
		this.taxonOID = taxonOID;
		this.identity = identity;
		this.lineage = lineage;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PhiloDist)){
			return false;
		}
		if(obj == this){
			return true;
		}
		PhiloDist philoDist = (PhiloDist) obj;
		return this.geneOID.equals(philoDist.geneOID) &&
				this.taxonOID.equals(philoDist.taxonOID) &&
				this.identity.equals(philoDist.identity) && 
				this.lineage.equals(philoDist.lineage);
	}
	

}
