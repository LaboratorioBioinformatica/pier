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
	
	public String getReference() {
		return reference;
	}

	public String getSequence() {
		return sequence;
	}

	public Integer getStartAlignment() {
		return startAlignment;
	}

	public Integer getEndAlignment() {
		return endAlignment;
	}

	public Integer getFlag() {
		return flag;
	}

	public Integer getPair() {
		return pair;
	}

	public Taxon getTaxon() {
		return taxon;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ReadOnContig)) {
			return false;
		}
		if(obj == this){
			return true;
		}
		
		ReadOnContig readOnContig = (ReadOnContig) obj;
		
		return this.reference.equals(readOnContig.getReference()) && 
				this.sequence.equals(readOnContig.getSequence()) && 
				this.startAlignment.equals(readOnContig.getStartAlignment()) &&
				this.endAlignment.equals(readOnContig.getEndAlignment()) &&
				this.flag.equals(readOnContig.getFlag()) &&
				this.pair.equals(readOnContig.getPair());
	}
	
	
}
