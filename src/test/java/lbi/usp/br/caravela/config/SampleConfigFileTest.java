package lbi.usp.br.caravela.config;

import java.util.HashMap;

import lbi.usp.br.caravela.exeption.DomainValidateException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class SampleConfigFileTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testGetters() throws Exception {
		String sampleName = "sample-01";
		String contigFilePath = "files/contig/sample-01-contig.fasta";
		String mappingFilePath = "files/mapping/sample-01-mapping.bam";
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		SampleConfigFile target = new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);
		
		Assert.assertEquals(sampleName, target.getSampleName());
		Assert.assertEquals(contigFilePath, target.getContigFilePath());
		Assert.assertEquals(mappingFilePath, target.getMappingFilePath());
		Assert.assertEquals(taxonomyFileConfig, target.getTaxonomy());
		Assert.assertEquals(functionalCofigFile, target.getFunctionalCofigFile());
		
	}

	private FunctionalCofigFile createValidFunctionConfigFile() {
		FunctionProvider functionProvider = FunctionProvider.IMG_M;
		HashMap<String, String> files = new HashMap<String, String>();
		return new FunctionalCofigFile(functionProvider, files);
	}

	private TaxonomyFileConfig createValidTaxonomyFileConfig() {
		TaxonomyProvider taxonomyProvider = TaxonomyProvider.DEFAULT;
		String taxonomy = "files/taxonomy/sample-01-taxonomy.tsv";
		return new TaxonomyFileConfig(taxonomyProvider, taxonomy);
	}
	
	@Test
	public void testWhenSampleNameIsInvalidShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid sample name");
		
		String sampleName = "sa";
		String contigFilePath = "files/contig/sample-01-contig.fasta";
		String mappingFilePath = "files/mapping/sample-01-mapping.bam";
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}
	
	@Test
	public void testWhenContigFilePathIsNullShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid contig file path");
		
		String sampleName = "samaple-01";
		String contigFilePath = null;
		String mappingFilePath = "files/mapping/sample-01-mapping.bam";
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}
	
	@Test
	public void testWhenContigFilePathIsEmptyShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid contig file path");
		
		String sampleName = "samaple-01";
		String contigFilePath = "";
		String mappingFilePath = "files/mapping/sample-01-mapping.bam";
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}
	
	@Test
	public void testWhenMappingFilePathIsNullShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid mapping file path");
		
		String sampleName = "sample-01";
		String contigFilePath = "files/contig/sample-01-contig.fasta";
		String mappingFilePath = null;
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}
	
	@Test
	public void testWhenMappingFilePathIsEmptyShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid mapping file path");
		
		String sampleName = "sample-01";
		String contigFilePath = "files/contig/sample-01-contig.fasta";
		String mappingFilePath = "";
		
		TaxonomyFileConfig taxonomyFileConfig = createValidTaxonomyFileConfig();
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}
	
	@Test
	public void testWhenTaxonomyFileConfigIsNullShouldBeThrowsDomainValidateException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid taxonomy file config");
		
		String sampleName = "sample-01";
		String contigFilePath = "files/contig/sample-01-contig.fasta";
		String mappingFilePath = "files/mapping/sample-01-mapping.bam";
		
		TaxonomyFileConfig taxonomyFileConfig = null;
		FunctionalCofigFile functionalCofigFile = createValidFunctionConfigFile();
		
		new SampleConfigFile(sampleName, contigFilePath, mappingFilePath, taxonomyFileConfig, functionalCofigFile);

	}



}
