package lbi.iq.usp.br.mgbl.mgbl.img;

import junit.framework.Assert;

import org.junit.Test;


public class GeneProductTest {
	
	private static final String TAB = "\t";	
	
	private static final String GENE_ID = "metazooDRAFT_10000561";
	private static final String PRODUCT_NAME = "ABC-type cobalt transport system, ATPase component";
	private static final String SOURCE = "COG1122";
	
	private static final String LINE = new StringBuilder(GENE_ID).append(TAB)
			.append(PRODUCT_NAME).append(TAB)
			.append(SOURCE).toString();
	
	@Test
	public void testGetters() throws Exception {
		GeneProduct target = new GeneProduct(LINE);
		Assert.assertEquals(GENE_ID, target.getGeneId());
		Assert.assertEquals(PRODUCT_NAME, target.getProductName());
		Assert.assertEquals(SOURCE, target.getSource());
	}

}
