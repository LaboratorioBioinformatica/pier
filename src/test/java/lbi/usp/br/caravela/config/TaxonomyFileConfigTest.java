package lbi.usp.br.caravela.config;

import org.junit.Assert;
import org.junit.Test;


public class TaxonomyFileConfigTest {
	
	@Test
	public void testGetters() throws Exception {
		TaxonomyProvider provider = TaxonomyProvider.MYTAXA;
		String taxonomy = "files/taxonomy/sample-01-taxonomy.tsv";
		TaxonomyFileConfig target = new TaxonomyFileConfig(provider, taxonomy);
		
		Assert.assertEquals(provider, target.getProviderName());
		Assert.assertEquals(taxonomy, target.getTaxonomyFilePath());
	}
	
	@Test
	public void testEquals() throws Exception {
		
		TaxonomyProvider myTaxaProvider = TaxonomyProvider.MYTAXA;
		TaxonomyProvider ktClassifyBlastProvider = TaxonomyProvider.KT_CLASSIFY_BLAST;
		
		String myTaxaTaxonomyPath = "files/taxonomy/sample-01-taxonomy.mytaxa";
		String TVSTaxonomyPath = "files/taxonomy/sample-01-taxonomy.tsv";
		
		TaxonomyFileConfig target = new TaxonomyFileConfig(myTaxaProvider, myTaxaTaxonomyPath);
		TaxonomyFileConfig equalstarget = new TaxonomyFileConfig(myTaxaProvider, myTaxaTaxonomyPath);
		TaxonomyFileConfig targetTVS = new TaxonomyFileConfig(ktClassifyBlastProvider, TVSTaxonomyPath);
		
		
		Assert.assertTrue(target.equals(target));
		Assert.assertTrue(target.equals(equalstarget));
		Assert.assertTrue(equalstarget.equals(target));
		Assert.assertFalse(targetTVS.equals(target));
		
	}

}
