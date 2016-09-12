package lbi.usp.br.caravela.img;


import org.junit.Assert;
import org.junit.Test;



public class IMGFileTypeTest {
	
	@Test
	public void testeGetByValue() {
		Assert.assertEquals("gff", IMGFileType.GFF_FILE_KEY.getValue());
		Assert.assertEquals("geneProduct", IMGFileType.GENE_PRODUCT_FILE_KEY.getValue());
		Assert.assertEquals("phylodist", IMGFileType.PHILO_DIST_FILE_KEY.getValue());
		Assert.assertEquals("geneSequence", IMGFileType.GENE_SEQUENCE_FILE_KEY.getValue());
		Assert.assertEquals("geneTaxonomy", IMGFileType.GENE_TAXONOMY_FILE_KEY.getValue());
		
		Assert.assertEquals("cog", IMGFileType.COG_FILE_KEY.getValue());
		Assert.assertEquals("ko", IMGFileType.KO_FILE_KEY.getValue());
		Assert.assertEquals("pfam", IMGFileType.PFAM_FILE_KEY.getValue());
		Assert.assertEquals("ec", IMGFileType.EC_FILE_KEY.getValue());

	}

}
