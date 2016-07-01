package lbi.usp.br.caravela.img;

import java.io.File;
import java.io.FileWriter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import lbi.usp.br.caravela.exeption.DomainValidateException;

public class GFFFileManagerTest {
	
	private static final String GENE_ID_CDS = "metazooDRAFT_10000351";
	private static final String GENE_ID_tRNA = "metazooDRAFT_10000352";

	private static final String FEATURE_TYPE_CDS = "CDS";
	private static final String FEATURE_TYPE_tRNA = "tRNA";


	private static final String CONTIG_REFERENCE = "metazooDRAFT_1000035";
	private static final String OTHER_CONTIG_REFERENCE = "metazooDRAFT_1000037";
	private static final String DO_NOT_EXISTS_CONTIG_REFERENCE = "doNotExistsContigReference";

	private static final String TAB = "\t";
	
	private static final Integer ZERO = 0;
	private static final Integer ONE = 1;
	private static final Integer TWO = 2;
	
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	private static final String LINE1 = createLine(CONTIG_REFERENCE, FEATURE_TYPE_CDS, GENE_ID_CDS);
	private static final String LINE2 = createLine(CONTIG_REFERENCE, FEATURE_TYPE_tRNA, GENE_ID_tRNA);
	private static final String LINE3 = createLine(OTHER_CONTIG_REFERENCE, FEATURE_TYPE_CDS, GENE_ID_CDS);
	
	
	@Test
	public void test() throws Exception {
		
		File gffFile = tmpFolder.newFile("anyFileName.gff");
		
		FileWriter fileWriter = new FileWriter(gffFile);
		String breakLine = System.lineSeparator();
		fileWriter.write(LINE1+breakLine);
		fileWriter.write(LINE2+breakLine);
		fileWriter.write(LINE3);
		fileWriter.close();
		

		
		GFFFileManager gffFileManager = new GFFFileManager(gffFile.getAbsolutePath());
		
		Assert.assertEquals(TWO, gffFileManager.getNumberOfFeaturesBySequenceReference(CONTIG_REFERENCE));
		Assert.assertEquals(ONE, gffFileManager.getNumberOfFeaturesBySequenceReference(OTHER_CONTIG_REFERENCE));
		
		
		Assert.assertNotNull(gffFileManager.getFeture(CONTIG_REFERENCE));
		Assert.assertNotNull(gffFileManager.getFeture(OTHER_CONTIG_REFERENCE));
		
		Assert.assertEquals(ZERO, gffFileManager.getNumberOfFeaturesBySequenceReference(DO_NOT_EXISTS_CONTIG_REFERENCE));
		Assert.assertNull(gffFileManager.getFeture(DO_NOT_EXISTS_CONTIG_REFERENCE));
		
		
	}
	
	private static String createLine(String contigReference, String featureType, String geneId) {
		return new StringBuilder(contigReference).append(TAB)
		.append("FGMP").append(TAB)
		.append(featureType).append(TAB)
		.append(1).append(TAB)
		.append(300).append(TAB)
		.append(".").append(TAB)
		.append(1).append(TAB)
		.append("0").append(TAB)
		.append(new StringBuilder("ID").append("=").append("metazooDRAFT_1000035.1").append(";")
				.append("locus_tag").append("=").append(geneId).append(";").toString()).append("\t")
		.toString();

	}

	@Test
	public void testWhenGFFFilePathDoNotExistShouldBeThrowException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid gff file path");
		
		new GFFFileManager("/tmp/donotexist.gff");
		
	}

}
