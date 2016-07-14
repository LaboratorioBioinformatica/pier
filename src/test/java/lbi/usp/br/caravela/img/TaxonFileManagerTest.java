package lbi.usp.br.caravela.img;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import lbi.usp.br.caravela.dto.Taxon;
import lbi.usp.br.caravela.exeption.DomainException;

public class TaxonFileManagerTest {
	
	private static final String TAB = "\t";
	private static final String SEQ_REFERENCE_01 = "ZCTH0002_100016";
	private static final String SEQ_REFERENCE_02 = "ZCTH0002_100018";
	
	private static final Integer NCBI_TAXONOMY_ID_01 = 457570;
	private static final Integer NCBI_TAXONOMY_ID_02 = 1121088;
	
	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@Test
	public void testName() throws Exception {
		String fileName = "geneTaxonomyFileName.tsv";
		File geneTaxonFile = tmpFolder.newFile(fileName);
		FileWriter fileWriter = new FileWriter(geneTaxonFile);
		fileWriter.write(SEQ_REFERENCE_01 + TAB + NCBI_TAXONOMY_ID_01);
		fileWriter.write(System.lineSeparator());
		fileWriter.write(SEQ_REFERENCE_02 + TAB + NCBI_TAXONOMY_ID_02);
		fileWriter.close();
		
		Taxon taxon01 = new Taxon.Builder().setTaxonomyId(NCBI_TAXONOMY_ID_01).build();
		Taxon taxon02 = new Taxon.Builder().setTaxonomyId(NCBI_TAXONOMY_ID_02).build();
		
		HashMap<String,Taxon> taxonHashMapExpected = new HashMap<>();
		taxonHashMapExpected.put(SEQ_REFERENCE_01, taxon01);
		taxonHashMapExpected.put(SEQ_REFERENCE_02, taxon02);
		
		TaxonFileManager target = new TaxonFileManager();
		HashMap<String,Taxon> taxonHashMapResult = target.loadAndGetTaxonHashMap(geneTaxonFile.getAbsolutePath());
		
		Assert.assertEquals(taxonHashMapExpected, taxonHashMapResult);

		
	}
	
	@Test
	public void testGetTaxonBySequenceReference() throws Exception {
		String fileName = "geneTaxonomyFileName.tsv";
		File geneTaxonFile = tmpFolder.newFile(fileName);
		FileWriter fileWriter = new FileWriter(geneTaxonFile);
		fileWriter.write(SEQ_REFERENCE_01 + TAB + NCBI_TAXONOMY_ID_01);
		fileWriter.write(System.lineSeparator());
		fileWriter.write(SEQ_REFERENCE_02 + TAB + NCBI_TAXONOMY_ID_02);
		fileWriter.close();
		
		Taxon taxon01 = new Taxon.Builder().setTaxonomyId(NCBI_TAXONOMY_ID_01).build();
		Taxon taxon02 = new Taxon.Builder().setTaxonomyId(NCBI_TAXONOMY_ID_02).build();
		
		TaxonFileManager target = new TaxonFileManager(geneTaxonFile.getAbsolutePath());
		Assert.assertEquals(taxon01, target.getTaxonBySequenceReferenfe(SEQ_REFERENCE_01));
		Assert.assertEquals(taxon02, target.getTaxonBySequenceReferenfe(SEQ_REFERENCE_02));
		
	}
	
	@Test
	public void testWhenInvalidFilePathShouldBeTrowsException() throws Exception {
		expectedEx.expect(DomainException.class);
		expectedEx.expectMessage("Invalid Gene Taxon File Path");
		
		String invalidFilePath = "/tm/invalidFilepath.txt";
		new TaxonFileManager(invalidFilePath);
	}

}
