package lbi.usp.br.caravela.img;

public enum IMGPFAMFileIndex {
	
	QUERY_START_LOCATION(3),
	QUERY_END_LOCATION(4),
	SUBJECT_START_LOCATION(5),
	SUBJECT_END_LOCATION(6),
	EVALUE_LOCATION(7),
	BIT_SCORE_LOCATION(8),
	ALIGN_LENGTH_LOCATION(9);
	
	private final int value;
	
	private IMGPFAMFileIndex(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
}
