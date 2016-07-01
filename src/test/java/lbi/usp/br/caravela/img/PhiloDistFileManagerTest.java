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

public class PhiloDistFileManagerTest {

	private static final String TAB = "\t";
	
	private static final String GENE_ID_01 = "metazooDRAFT_10029651";
	private static final String GENE_ID_02 = "metazooDRAFT_10029652";
	private static final String LINEAGE = "Bacteria;Bacteroidetes;Bacteroidia;Bacteroidales;Bacteroidaceae;Bacteroides;finegoldii;Bacteroides finegoldii DSM 17565";
	
	
	private static final String LINE_01 = new StringBuilder(GENE_ID_01).append(TAB).append("2523866490").append(TAB).append("2523533587").append(TAB).append("32.2").append(TAB).append(LINEAGE).toString();
	private static final String LINE_02 = new StringBuilder(GENE_ID_02).append(TAB).append("2523866491").append(TAB).append("2523533588").append(TAB).append("32.3").append(TAB).append(LINEAGE).toString();
	
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none(); 
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@Test
	public void testWhenDoNotExistsFilePahtAndShouldBeThrowsDomainExecption() throws Exception {
		expectedEx.expect(DomainException.class);
		expectedEx.expectMessage("Invalid Philodist File Path");
		
		String filePath = "/tmp/doNotExistsFile.tsv";
		new PhiloDistFileManager(filePath );
	}
	
	@Test
	public void test() throws Exception {
		
		File philoDistFile = tmpFolder.newFile("anyFileName.tsv");
		
		FileWriter fileWriter = new FileWriter(philoDistFile);
		String breakLine = System.lineSeparator();
		fileWriter.write(LINE_01+breakLine);
		fileWriter.write(LINE_02);
		fileWriter.close();
		
		PhiloDistFileManager target = new PhiloDistFileManager();
		HashMap<String, IMGPhiloDist> hashMapIMGPhiloDistLoaded = target.load(philoDistFile.getAbsolutePath());
		
		Assert.assertEquals(GENE_ID_01, hashMapIMGPhiloDistLoaded.get(GENE_ID_01).getGeneId());
		Assert.assertEquals(GENE_ID_02, hashMapIMGPhiloDistLoaded.get(GENE_ID_02).getGeneId());
		
	}

}
