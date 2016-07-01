package lbi.usp.br.caravela.img;

import org.junit.Assert;
import org.junit.Test;

public class IMGGeneProductTest {
	
	private static final String TAB = "\t";	
	
	private static final String GENE_ID = "metazooDRAFT_10000561";
	private static final String PRODUCT_NAME = "ABC-type cobalt transport system, ATPase component";
	private static final String SOURCE = "COG1122";
	
	private static final String LINE = new StringBuilder(GENE_ID).append(TAB)
			.append(PRODUCT_NAME).append(TAB)
			.append(SOURCE).toString();
	
	@Test
	public void testGetters() throws Exception {
		IMGGeneProduct target = new IMGGeneProduct(LINE);
		Assert.assertEquals(GENE_ID, target.getGeneId());
		Assert.assertEquals(PRODUCT_NAME, target.getProductName());
		Assert.assertEquals(SOURCE, target.getSource());
	}

}
