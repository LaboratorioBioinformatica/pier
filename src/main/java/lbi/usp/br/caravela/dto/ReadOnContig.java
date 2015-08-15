package lbi.usp.br.caravela.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class ReadOnContig {
	private String reference;
	private String sequence;
	@SerializedName("start")
	private Integer startAlignment;
	@SerializedName("end")
	private Integer endAlignment;
	private Integer flag;
	private String pair;
	private List<Taxon> taxons;
	
	public ReadOnContig() {
	}
	
	public ReadOnContig(String reference, String sequence, Integer startAlignment, Integer endAlignment, Integer flag, String pair, List<Taxon> taxons) {
		this.reference = reference;
		this.sequence = sequence;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.flag = flag;
		this.pair = pair;
		this.taxons = taxons;
		
	}
	
	
	
	

}
