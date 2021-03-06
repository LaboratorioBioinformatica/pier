package lbi.usp.br.caravela.config;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import lbi.usp.br.caravela.exeption.DomainValidateException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;


public class IMGManagerFileTest {
	
	private static final String TAB = "\t";

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	private static final String LINE = new StringBuilder("metazooDRAFT_1000035").append(TAB)
			.append("FGMP").append(TAB)
			.append("CDS").append(TAB)
			.append(1).append(TAB)
			.append(300).append(TAB)
			.append(".").append(TAB)
			.append(1).append(TAB)
			.append("0").append(TAB)
			.append(new StringBuilder("ID").append("=").append("metazooDRAFT_1000035.1").append(";")
					.append("locus_tag").append("=").append("metazooDRAFT_10000351").append(";").toString()).append("\t")
			.toString();
	
	@Test
	public void test() throws Exception {
		
		File gffFile = tmpFolder.newFile("anyFileName.gff");
		
		FileWriter fileWriter = new FileWriter(gffFile);
		
		fileWriter.write(LINE);
		fileWriter.close();
		
		HashMap<String, String> files = new HashMap<>();
		String directory = gffFile.getParent() + "/";
		files.put("directory", directory);
		files.put("gff", gffFile.getName());
		
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile(FunctionProvider.IMG_M, files);
		
		IMGManagerFile target = new IMGManagerFile(functionalCofigFile);
		target.getGFFFeatureList();
		
	}
	
	@Test
	public void testWhenGFFFilePathDoNotExistShouldBeThrowException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid gff file path");
		String directory = "/tmp/";
		HashMap<String, String> files = new HashMap<>();
		files.put("directory", directory);
		files.put("gff", "donotexist.gff");
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile(FunctionProvider.IMG_M, files);
		
		new IMGManagerFile(functionalCofigFile);
		
		
		
	}

}
