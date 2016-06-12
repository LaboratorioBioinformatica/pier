package lbi.usp.br.caravela.config;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import lbi.usp.br.caravela.exeption.DomainException;

public class FunctionalCofigFileTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testGetters() throws Exception {
		FunctionProvider provider = FunctionProvider.NO; 
		HashMap<String, String> fileList = new HashMap<String, String>();
		
		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		Assert.assertEquals(provider, target.getProvider());
		Assert.assertEquals(fileList, target.getFileList());
	}
	
	@Test
	public void testValidateWhenProviderIsNOAndFilesIsIsEmpty() throws Exception {
		FunctionProvider provider = FunctionProvider.NO; 
		HashMap<String, String> fileList = new HashMap<String, String>();
		
		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		target.validate();
		
		Assert.assertEquals(provider, target.getProvider());
		Assert.assertEquals(fileList, target.getFileList());
	}
	
	@Test
	public void testValidateWhenProviderIsNOTNOAndFileNOTIsIsEmpty() throws Exception {
		FunctionProvider provider = FunctionProvider.IMG_M; 
		HashMap<String, String> fileList = new HashMap<String, String>();
		fileList.put("gff", "/tmp/file.gff");

		FunctionalCofigFile target = new FunctionalCofigFile(provider, fileList);
		target.validate();
		
		Assert.assertEquals(provider, target.getProvider());
		Assert.assertEquals(fileList, target.getFileList());
		
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
		Assert.assertEquals(fileList, target.getFileList());
		
	}
	

}
