package lbi.iq.usp.br.mgbl.mgbl.img;

import junit.framework.Assert;

import org.junit.Test;


public class PhiloDistTest {
	
	private static final String GENE_ID = "metazooDRAFT_10029651";
	private static final String HOMOLOG_GENE_OID = "2523866490";
	private static final String HOMOLOG_TAXON_OID = "2523533587";
	private static final String PERCENT_IDENTITY = "32.2";
	//domain;phylum;class;order;family;genus;species;taxon_name
	private static final String LINEAGE = "Bacteria;Bacteroidetes;Bacteroidia;Bacteroidales;Bacteroidaceae;Bacteroides;finegoldii;Bacteroides finegoldii DSM 17565";
	
	private static final String TAB = "\t";
	
	private static final String LINE = new StringBuilder(GENE_ID).append(TAB)
			.append(HOMOLOG_GENE_OID).append(TAB)
			.append(HOMOLOG_TAXON_OID).append(TAB)
			.append(PERCENT_IDENTITY).append(TAB)
			.append(LINEAGE).toString();
	
	@Test
	public void testGetters() throws Exception {
		PhiloDist target = new PhiloDist(LINE);
		
		Assert.assertEquals(GENE_ID, target.getGeneId());
		Assert.assertEquals(HOMOLOG_GENE_OID, target.getHomologGeneOID());
		Assert.assertEquals(HOMOLOG_TAXON_OID, target.getHomologTaxonOID());
		Assert.assertEquals(PERCENT_IDENTITY, target.getPercentIdentity());
		Assert.assertEquals(LINEAGE, target.getLineage());
	}

}
