package lbi.iq.usp.br.mgbl.mgbl;

public class ReadOnContig {
	
	private String reference;
	private String sequence;
	private Integer flag;
	private String pair;
	private Integer taxonomyId;
	private String scientificName;
	private String hank;
	
	public ReadOnContig() {
	}
	
	public ReadOnContig(String reference, String sequence, Integer flag, String pair, Integer taxonomyId ,String scientificName, String hank) {
		this.reference = reference;
		this.sequence = sequence;
		this.flag = flag;
		this.pair = pair;
		this.taxonomyId = taxonomyId;
		this.scientificName = scientificName;
		this.hank = hank;
	}
	
	
	
	

}
