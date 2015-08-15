package lbi.usp.br.caravela.img;

public class IMGPhiloDist {
	
	private static final int GENE_ID_LOCATION = 0;
	private static final int HOMOLOG_GENE_OID_LOCATION = 1;
	private static final int HOMOLOG_TAXON_OID_LOCATION = 2;
	private static final int PERCENT_IDENTITY_LOCATION = 3;
	private static final int LINEAGE_LOCATION = 4;
	
	private static final String SEMICOLON = ";";
	private static final String TAB = "\t";
	
	private String geneId;
	private String homologGeneOID;
	private String homologTaxonOID;
	private String percentIdentity;
	private String lineage;
	
	public IMGPhiloDist(String line) {
		String[] lineSplitted = line.split(TAB);
		this.geneId = lineSplitted[GENE_ID_LOCATION];
		this.homologGeneOID = lineSplitted[HOMOLOG_GENE_OID_LOCATION];
		this.homologTaxonOID = lineSplitted[HOMOLOG_TAXON_OID_LOCATION];
		this.percentIdentity = lineSplitted[PERCENT_IDENTITY_LOCATION];
		this.lineage = lineSplitted[LINEAGE_LOCATION];
		
	}

	public String getGeneId() {
		return geneId;
	}

	public String getHomologGeneOID() {
		return homologGeneOID;
	}

	public String getHomologTaxonOID() {
		return homologTaxonOID;
	}

	public String getPercentIdentity() {
		return percentIdentity;
	}

	public String getLineage() {
		return lineage;
	}
	
	public String getDeepestTaxon(){
		String[] lineageSpllited = lineage.split(SEMICOLON);
		Integer lineageSpllitedLength = lineageSpllited.length;
		Integer lastPosition = lineageSpllitedLength -1;
		return lineageSpllited[lastPosition ];
	}
	
	
	
}
