package lbi.usp.br.caravela.dto;

import com.google.gson.annotations.SerializedName;
  


public class ReadOnContig {
	private final String reference;
	private final String sequence;
	@SerializedName("start")
	private final Integer startAlignment;
	@SerializedName("end")
	private final Integer endAlignment;
	private final Integer flag;
	private final Integer pair;
	private final Taxon taxon;
	
	public ReadOnContig(String reference, String sequence, Integer startAlignment, Integer endAlignment, Integer flag, Integer pair, Taxon taxon) {
		this.reference = reference;
		this.sequence = sequence;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.flag = flag;
		this.pair = pair;
		this.taxon = taxon;	
	}
	
	public boolean hasTaxon(){
		if(this.taxon != null){
			return  true;
		} else {
			return false;
		}
	}

}
