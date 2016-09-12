package lbi.usp.br.caravela.img;

public enum IMGFileType {
	
	DIRECTORY_FILE_PATH_KEY("directory"),
	GFF_FILE_KEY("gff"),
	GENE_PRODUCT_FILE_KEY("geneProduct"),
	PHILO_DIST_FILE_KEY("phylodist"),
	GENE_SEQUENCE_FILE_KEY("geneSequence"),
	GENE_TAXONOMY_FILE_KEY("geneTaxonomy"),
	COG_FILE_KEY("cog"),
	KO_FILE_KEY("ko"),
	PFAM_FILE_KEY("pfam"),
	EC_FILE_KEY("ec");
	
	private final String value;
	
	IMGFileType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	

}
