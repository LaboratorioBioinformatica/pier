package lbi.usp.br.caravela;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import lbi.usp.br.caravela.exeption.DomainValidateException;




public class FileAggregatorTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test(expected=FileNotFoundException.class)
	public void testCreateFileAggregatorWhenFileDoesNotExist() throws Exception {
		String fileSamplePath = "files/sampleConfigFile/no-sample-01.json";
		new FileAggregator(fileSamplePath);
		
	}
	
	@Test
	public void testCreateJsonFileWhenSampleFileNameIsInvalid() throws Exception {
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid sample name");
		String fileSamplePath = "files/sampleConfigFile/sample-config-when-sample-name-is-invalid.json";
		FileAggregator fileAggregator = new FileAggregator(fileSamplePath);
		
		fileAggregator.createJsonFile();
	}
	
	@Test
	public void testCreateJsonFileWhenContigFilePathDoesNotExist() throws Exception {
		expectedEx.expect(FileNotFoundException.class);
		expectedEx.expectMessage("contig file is required.");
		String fileSamplePath = "files/sampleConfigFile/sample-config-when-contig-file-path-does-not-exist.json";
		FileAggregator fileAggregator = new FileAggregator(fileSamplePath);
		
		fileAggregator.createJsonFile();
	}
	
	@Test
	public void testCreateJsonFileWhenMappingFilePathDoesNotExist() throws Exception {
		expectedEx.expect(FileNotFoundException.class);
		expectedEx.expectMessage("mapping file is required.");
		String fileSamplePath = "files/sampleConfigFile/sample-config-when-mapping-file-path-does-not-exist.json";
		FileAggregator fileAggregator = new FileAggregator(fileSamplePath);
		
		fileAggregator.createJsonFile();
	}
	
	@Test
	public void testCreateJsonFileWhenTaxonomyFilePathDoesNotExist() throws Exception {
		expectedEx.expect(FileNotFoundException.class);
		expectedEx.expectMessage("taxonomy file is required.");
		String fileSamplePath = "files/sampleConfigFile/sample-config-when-taxonomy-file-path-does-not-exist.json";
		FileAggregator fileAggregator = new FileAggregator(fileSamplePath);
		
		fileAggregator.createJsonFile();
	}
	
	

}
