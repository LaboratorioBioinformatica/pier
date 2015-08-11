package lbi.iq.usp.br.mgbl.mgbl.mytaxa;

import junit.framework.Assert;

import org.junit.Test;

public class MyTaxaLineTest {

	
	
	private static final String SEQUENCE_REFERENCE = "M01677:6:000000000-A41BV:1:1101:17611:1840 1:N:0:8";
	private static final String TAXONOMY_RANK_SPECIE = "Species 1";
	private static final String TAXONOMY_RANK_GENUS = "Genus 1";
	private static final String TAXONOMY_ID = "28118";
	private static final String LINEAGE_SPECIES = "<superkingdom>Bacteria;<superphylum>Bacteroidetes/Chlorobi group;<phylum>Bacteroidetes;<class>Bacteroidia;<order>Bacteroidales;<family>Porphyromonadaceae;<genus>Odoribacter;<species>Odoribacter splanchnicus";
	private static final String LINEAGE_GENUS = "<superkingdom>Bacteria;<phylum>Proteobacteria;<subphylum>delta/epsilon subdivisions;<class>Epsilonproteobacteria;<order>Campylobacterales;<family>Campylobacteraceae;<genus>Arcobacter";
	

	@Test
	public void testGetters() {
		MyTaxaLine myTaxaLine = new MyTaxaLine(SEQUENCE_REFERENCE, TAXONOMY_RANK_SPECIE, TAXONOMY_ID, LINEAGE_SPECIES);
		
		Assert.assertEquals(myTaxaLine.getSequenceReference(), SEQUENCE_REFERENCE);
		Assert.assertEquals(myTaxaLine.getTaxonomyRank(), TAXONOMY_RANK_SPECIE);
		Assert.assertEquals(myTaxaLine.getTaxonomyId(), TAXONOMY_ID);
		Assert.assertEquals(myTaxaLine.getLineage(), LINEAGE_SPECIES);
		
	}

	@Test
	public void testGetDeepestTaxonomyWhenTaxonomyDeepIsSpecie() throws Exception {
		MyTaxaLine myTaxaLine = new MyTaxaLine(SEQUENCE_REFERENCE, TAXONOMY_RANK_SPECIE, TAXONOMY_ID, LINEAGE_SPECIES);
		String deepestTaxonomy = myTaxaLine.getDeepestTaxonomy();
		Assert.assertEquals(deepestTaxonomy, "<species>Odoribacter splanchnicus");
	}
	
	@Test
	public void testGetDeepestTaxonomyWhenTaxonomyDeepIsGenus() throws Exception {
		MyTaxaLine myTaxaLine = new MyTaxaLine(SEQUENCE_REFERENCE, TAXONOMY_RANK_GENUS, TAXONOMY_ID, LINEAGE_GENUS);
		String deepestTaxonomy = myTaxaLine.getDeepestTaxonomy();
		Assert.assertEquals(deepestTaxonomy, "<genus>Arcobacter");
	}
	
	@Test
	public void testGetDeepestTaxonomyWhenHasNoLineage() throws Exception {
		MyTaxaLine myTaxaLine = new MyTaxaLine(SEQUENCE_REFERENCE, TAXONOMY_RANK_SPECIE, TAXONOMY_ID, null);
		String deepestTaxonomy = myTaxaLine.getDeepestTaxonomy();
		Assert.assertEquals(deepestTaxonomy, "NA");
	}

}
