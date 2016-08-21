package lbi.usp.br.caravela.dto;

import com.google.gson.annotations.SerializedName;
  


public class ReadOnContig {
	private final String reference;
	private final String sequence;
	private final Integer sequenceLenth;
	@SerializedName("start")
	private final Integer startAlignment;
	@SerializedName("end")
	private final Integer endAlignment;
	private final String cigar;
	private final Integer flag;
	private final Integer pair;
	private final Taxon taxon;
	
	public ReadOnContig(String reference, String sequence, Integer sequenceLenth, Integer startAlignment, Integer endAlignment, String cigar, Integer flag, Integer pair, Taxon taxon) {
		this.reference = reference;
		this.sequence = sequence;
		this.sequenceLenth = sequenceLenth;
		this.startAlignment = startAlignment;
		this.endAlignment = endAlignment;
		this.cigar = cigar;
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

	public String getCigar() {
		return cigar;
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
	
	public Integer getSequenceLenth(){
		return sequenceLenth;
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
				this.sequenceLenth.equals(readOnContig.getSequenceLenth()) &&
				this.startAlignment.equals(readOnContig.getStartAlignment()) &&
				this.endAlignment.equals(readOnContig.getEndAlignment()) &&
				this.cigar.equals(readOnContig.getCigar()) &&
				this.flag.equals(readOnContig.getFlag()) &&
				this.taxon.equals(readOnContig.getTaxon()) &&
				this.pair.equals(readOnContig.getPair());
	}
	
	
}
