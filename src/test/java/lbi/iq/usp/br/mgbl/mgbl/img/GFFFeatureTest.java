package lbi.iq.usp.br.mgbl.mgbl.img;

import junit.framework.Assert;

import org.junit.Test;


public class GFFFeatureTest {
	
	private static final String SEMICOLON = ";";
	private static final String EQUALS = "=";
	private static final String TAB = "\t";
	
	private static final String SEQ_ID = "metazooDRAFT_1000035";
	private static final String SOURCE = "FGMP";
	private static final String TYPE = "CDS";
	private static final Integer START = 1;
	private static final Integer END = 300;
	private static final String SCORE = ".";
	private static final Integer STRAND = 1;
	private static final String PHASE = "0";

	private static final String ATTRIBUTE_NAME_ID = "ID";
	private static final String ATTRIBUTE_VALUE_ID = "metazooDRAFT_1000035.1";
	
	private static final String ATTRIBUTE_NAME_LOCUS_TAG = "locus_tag";
	private static final String ATTRIBUTE_VALUE_LOCUS_TAG = "metazooDRAFT_10000351";
	
	private static final String ATTRIBUTES = new StringBuilder(ATTRIBUTE_NAME_ID).append(EQUALS).append(ATTRIBUTE_VALUE_ID).append(SEMICOLON)
			.append(ATTRIBUTE_NAME_LOCUS_TAG).append(EQUALS).append(ATTRIBUTE_VALUE_LOCUS_TAG).append(SEMICOLON).toString(); 
			
	
	
	
	private static final String GFF_FILE_LINE = new StringBuilder(SEQ_ID).append(TAB)
			.append(SOURCE).append(TAB)
			.append(TYPE).append(TAB)
			.append(START).append(TAB)
			.append(END).append(TAB)
			.append(SCORE).append(TAB)
			.append(STRAND).append(TAB)
			.append(PHASE).append(TAB)
			.append(ATTRIBUTES).append(TAB)
			.toString();
	
	@Test
	public void testGetters() throws Exception {
		GFFFeature gffFeature = new GFFFeature(GFF_FILE_LINE);
		Assert.assertEquals(SEQ_ID, gffFeature.getSeqId());
		Assert.assertEquals(SOURCE, gffFeature.getSource());
		Assert.assertEquals(TYPE, gffFeature.getType());
		Assert.assertEquals(START, gffFeature.getStart());
		Assert.assertEquals(END, gffFeature.getEnd());
		Assert.assertEquals(SCORE, gffFeature.getScore());
		Assert.assertEquals(STRAND, gffFeature.getStrand());
		Assert.assertEquals(PHASE, gffFeature.getPhase());
		Assert.assertEquals(ATTRIBUTES, gffFeature.getAttributes());
		
	}
	
	@Test
	public void testGetSeqIdByAttribute() throws Exception {
		GFFFeature gffFeature = new GFFFeature(GFF_FILE_LINE);
		Assert.assertEquals(ATTRIBUTE_VALUE_LOCUS_TAG, gffFeature.getAttributeByName(ATTRIBUTE_NAME_LOCUS_TAG));
		Assert.assertEquals(ATTRIBUTE_VALUE_ID, gffFeature.getAttributeByName(ATTRIBUTE_NAME_ID));
		
	}

}
