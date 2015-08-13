package lbi.iq.usp.br.mgbl.mgbl.mytaxa;

public class MyTaxaLine {
	
	private static final String SEMICOLON = ";";
	private static final String SPACE = " ";
	private static final String SPECIE_RANK = "Species";
	private String sequenceReference;
	private String taxonomyRank;
	private String taxonomyId;
	private String score;
	private String lineage;
	
	
	public MyTaxaLine(String sequenceReference, String taxonomyRank, String taxonomyId, String score, String lineage) {
		this.sequenceReference = sequenceReference;
		this.taxonomyRank = taxonomyRank;
		this.taxonomyId = taxonomyId;
		this.score = score;
		this.lineage = lineage;
	}
	
	public String getDeepestTaxonomy() {
		String deepestTaxonomy = "NA";
		
		if( null != lineage &&  ! lineage.isEmpty()) {
			String[] lineageSplit = lineage.split(SEMICOLON);
			return lineageSplit[lineageSplit.length -1];
		}
		return deepestTaxonomy;
		
	}
	
	public String getCleanDeepestTaxonomy(){
		String[] split = getDeepestTaxonomy().split(">");
		return split[1];
	}

	public String getSequenceReference() {
		return sequenceReference;
	}

	public String getTaxonomyRank() {
		return taxonomyRank;
	}
	
	public boolean hasSpecieLevel(){
		boolean hasSpecieLevel = false;
		if(this.taxonomyRank != null){
			String[] rankSplit = this.taxonomyRank.split(SPACE);
				if(SPECIE_RANK.equals(rankSplit[0])){
					hasSpecieLevel = true;
				}
		}
		return hasSpecieLevel;
		
	}
	
	public String getScore(){
		return this.score;
	}
	
	public String getSpecie(){
		String specie = "NA";
		if(hasSpecieLevel()){
			specie = getDeepestTaxonomy().replaceAll("<species>", "");
		} 
		return specie;
	}
	
	public String getTaxonomyId() {
		return taxonomyId;
	}

	public String getLineage() {
		return lineage;
	}

}
