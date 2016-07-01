package lbi.usp.br.caravela.img;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import lbi.usp.br.caravela.exeption.DomainException;

public class GeneProductFileManagerTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	private static final String TAB = "\t";
	private static final String GENE_ID_01 = "metazooDRAFT_10000561";
	private static final String GENE_ID_02 = "metazooDRAFT_10000562";
	private static final String PRODUCT_NAME_01 = "ABC-type cobalt transport system, ATPase component";
	private static final String PRODUCT_NAME_02 = "FGH-type cobalt transport system, ATPase component";
	private static final String SOURCE_01 = "COG1122";
	private static final String SOURCE_02 = "COG1123";

	
	private static final String LINE_01 = new StringBuilder(GENE_ID_01).append(TAB).append(PRODUCT_NAME_01).append(TAB).append(SOURCE_01).toString();
	private static final String LINE_02 = new StringBuilder(GENE_ID_02).append(TAB).append(PRODUCT_NAME_02).append(TAB).append(SOURCE_02).toString();
	
	@Test
	public void testWhenFilePahtIsInvalid() throws Exception {
		
		expectedEx.expect(DomainException.class);
		expectedEx.expectMessage("Invalid Gene Product File Path");
		String filePath = "/tmp/donotexistFilePath.tsv";
		
		new GeneProductFileManager(filePath);
		
	}
	
	@Test
	public void testName() throws Exception {
		File gffFile = tmpFolder.newFile("anyFileName.gff");

		FileWriter fileWriter = new FileWriter(gffFile);
		String breakLine = System.lineSeparator();
		
		fileWriter.write(LINE_01+breakLine);
		fileWriter.write(LINE_02);
		fileWriter.close();
		
		GeneProductFileManager target = new GeneProductFileManager();
		HashMap<String,IMGGeneProduct> hashMapImgGeneProductLoaded = target.load(gffFile.getAbsolutePath());
		IMGGeneProduct imgGeneProduct01 = hashMapImgGeneProductLoaded.get(GENE_ID_01);
		IMGGeneProduct imgGeneProduct02 = hashMapImgGeneProductLoaded.get(GENE_ID_02);
		
		Assert.assertEquals(GENE_ID_01, imgGeneProduct01.getGeneId());
		Assert.assertEquals(GENE_ID_02, imgGeneProduct02.getGeneId());
		
		Assert.assertEquals(PRODUCT_NAME_01, imgGeneProduct01.getProductName());
		Assert.assertEquals(PRODUCT_NAME_02, imgGeneProduct02.getProductName());
		
		Assert.assertEquals(SOURCE_01, imgGeneProduct01.getSource());
		Assert.assertEquals(SOURCE_02, imgGeneProduct02.getSource());
		
	}

	
	
	
	

}
