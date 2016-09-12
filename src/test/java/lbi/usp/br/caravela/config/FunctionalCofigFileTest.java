package lbi.usp.br.caravela.config;

import java.util.HashMap;

import lbi.usp.br.caravela.exeption.DomainException;
import lbi.usp.br.caravela.img.IMGFileType;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FunctionalCofigFileTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testGetters() throws Exception {
		FunctionProvider provider = FunctionProvider.NO; 
		HashMap<String, String> files = new HashMap<String, String>();
		
		
		String directory = "/tmp/";
		String gffFilePath =  directory  + "fileExist.gff";
		String geneProductFilePath = directory + "imgGeneProductFileExist.tsv";
		String philoDistFilePath = directory +  "imgPhiloDistFileExist.tsv";
		String COGFilePath = directory +  "IMGCOGFileExist.tsv";
		String KOFilePath =  directory +  "IMGKOFileExist.tsv";
		String PFAMFilePath = directory +  "IMGPFAMFileExist.tsv";
		String ECFilePath = directory + "IMGECFileExist.tsv";
		
		
		
		String gffFileName = "fileExist.gff";
		String geneProductFileName = "imgGeneProductFileExist.tsv";
		String philoDistFileName = "imgPhiloDistFileExist.tsv";
		String COGFileName = "IMGCOGFileExist.tsv";
		String KOFileName = "IMGKOFileExist.tsv";
		String PFAMFileName = "IMGPFAMFileExist.tsv";
		String ECFileName = "IMGECFileExist.tsv";
		String geneSequenceFilaName = "geneSequenceFileExist.fasta";
		
		
		files.put("directory", directory);
		
		files.put("gff", gffFileName);
		files.put("geneSequence", geneSequenceFilaName);
		files.put("geneProduct", geneProductFileName);
		files.put("phylodist", philoDistFileName);
		files.put("cog", COGFileName);
		files.put("ec", ECFileName);
		files.put("ko", KOFileName);
		files.put("pfam", PFAMFileName);

		
		
		FunctionalCofigFile target = new FunctionalCofigFile(provider, files);
		
		Assert.assertEquals(provider, target.getProvider());
		Assert.assertEquals(gffFilePath, target.getFilePathByFileType(IMGFileType.GFF_FILE_KEY));
		
		Assert.assertEquals(geneProductFilePath, target.getFilePathByFileType(IMGFileType.GENE_PRODUCT_FILE_KEY));
		Assert.assertEquals(philoDistFilePath, target.getFilePathByFileType(IMGFileType.PHILO_DIST_FILE_KEY));
		Assert.assertEquals(COGFilePath, target.getFilePathByFileType(IMGFileType.COG_FILE_KEY));
		Assert.assertEquals(KOFilePath, target.getFilePathByFileType(IMGFileType.KO_FILE_KEY));
		Assert.assertEquals(PFAMFilePath, target.getFilePathByFileType(IMGFileType.PFAM_FILE_KEY));
		Assert.assertEquals(ECFilePath, target.getFilePathByFileType(IMGFileType.EC_FILE_KEY));
		
		
	}
	
	@Test
	public void testValidateWhenProviderIsNOAndFilesIsIsEmpty() throws Exception {
		FunctionProvider provider = FunctionProvider.NO; 
		HashMap<String, String> fileList = new HashMap<String, String>();
		
		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		target.validate();
		
		Assert.assertEquals(provider, target.getProvider());
	}
	
	@Test
	public void testValidateWhenProviderIsNOTNOAndFileNOTIsIsEmpty() throws Exception {
		FunctionProvider provider = FunctionProvider.IMG_M; 
		HashMap<String, String> fileList = new HashMap<String, String>();
		
		String dirPath = "/tmp/";
		String gffFileName = "file.gff";
		String gffFilePath = "/tmp/file.gff";
		
		fileList.put("gff", gffFileName);
		fileList.put("directory", dirPath);

		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		target.validate();
		
		Assert.assertEquals(provider, target.getProvider());
		Assert.assertEquals(gffFilePath, target.getFilePathByFileType(IMGFileType.GFF_FILE_KEY));
		
	}
	
	@Test
	public void testValidateWhenProviderIsNull() throws Exception {
		
		expectedEx.expect(DomainException.class);
		FunctionProvider[] values = FunctionProvider.values();
		StringBuilder msgAppend = new StringBuilder().append("Invalid function provider name")
				.append(System.lineSeparator())
				.append("Valid providers are: ").append(System.lineSeparator());
		for (FunctionProvider functionProvider : values) {
			msgAppend.append(functionProvider.name()).append(System.lineSeparator());
			
		}
		expectedEx.expectMessage(msgAppend.toString());
		
		FunctionProvider provider = null; 

		HashMap<String, String> fileList = new HashMap<String, String>();
		
		String dirPath = "/tmp/";
		String gffFileName = "file.gff";
		
		fileList.put("gff", gffFileName);
		fileList.put("directory", dirPath);

		new FunctionalCofigFile(provider, fileList);
		
		
	}


	
	@Test
	public void testValidateWhenProviderIsNOTNOAndFileIsIsEmptyShouldByThrowsDomainValidateExecption() throws Exception {
		expectedEx.expect(DomainException.class);
		expectedEx.expectMessage("Invalid function file list");
		
		FunctionProvider provider = FunctionProvider.IMG_M; 
		HashMap<String, String> fileList = new HashMap<String, String>();

		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		target.validate();
		
		Assert.assertEquals(provider, target.getProvider());
		
	}
	

}
