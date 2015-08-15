package lbi.usp.br.caravela.img;

import java.util.HashMap;

public class GFFFeature {
	
	private static final int SEQ_ID_LOCATION = 0;
	private static final int SOURCE_LOCATION = 1;
	private static final int TYPE_LOCATION = 2;
	private static final int START_LOCATION = 3;
	private static final int END_LOCATION = 4;
	private static final int SCORE_LOCATION = 5;
	private static final int STRAND_LOCATION = 6;
	private static final int PHASE_LOCATION = 7;
	private static final int ATTRIBUTES_LOCATION = 8;
	
	private static final int ATTRIBUTE_KEY = 0;
	private static final int ATTRIBUTE_VALUE = 1;

	private static final String TAB = "\t";
	private static final String SEMICOLON = ";";
	private static final String EQUALS = "=";
	
	private static final String GENE_ID_ATTRIBUTE_NAME = "locus_tag";
	
	private String seqId;
	private String source;
	private String type;
	private Integer start;
	private Integer end;
	private String score;
	private Integer strand;
	private String phase;
	private String attributes;
	private HashMap<String, String> attributesList;
	
	public GFFFeature(String line) {
		populateDefaultFields(line);
		
		if(this.attributes != null){
			String[] attributeListSplit = this.attributes.split(SEMICOLON);
			attributesList = new HashMap<String, String>();
			
			for (int i = 0; i < attributeListSplit.length; i++) {
				String completeAttribute = attributeListSplit[i];
				String[] completeAttributeSplit = completeAttribute.split(EQUALS);
				attributesList.put(completeAttributeSplit[ATTRIBUTE_KEY], completeAttributeSplit[ATTRIBUTE_VALUE]);
			}
			
		}
		
	}

	public String getSeqId() {
		return seqId;
	}

	public String getSource() {
		return source;
	}

	public String getType() {
		return type;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public String getScore() {
		return score;
	}

	public Integer getStrand() {
		return strand;
	}

	public String getPhase() {
		return phase;
	}

	public String getAttributes() {
		return attributes;
	}
	
	public String getAttributeByName(String attributeName){
		return attributesList.get(attributeName);
	}
	
	public String getGeneId(){
		return getAttributeByName(GENE_ID_ATTRIBUTE_NAME);
	}

	private void populateDefaultFields(String line) {
		String[] listSplit = line.split(TAB);
		this.seqId =  listSplit[SEQ_ID_LOCATION];
		this.source = listSplit[SOURCE_LOCATION];
		this.type = listSplit[TYPE_LOCATION];
		this.start = new Integer(listSplit[START_LOCATION]);
		this.end = new Integer(listSplit[END_LOCATION]);
		this.score = listSplit[SCORE_LOCATION];
		this.strand = new Integer(listSplit[STRAND_LOCATION]);
		this.phase = listSplit[PHASE_LOCATION];
		this.attributes = listSplit[ATTRIBUTES_LOCATION];
	}
	

}
