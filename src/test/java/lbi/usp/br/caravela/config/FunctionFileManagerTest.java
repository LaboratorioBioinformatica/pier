package lbi.usp.br.caravela.config;

import java.util.HashMap;

import org.junit.Test;

public class FunctionFileManagerTest {
	
	
	@Test
	public void testName() throws Exception {
		HashMap<String, String> files = new HashMap<String, String>();
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile(FunctionProvider.NO, files);
		FunctionFileManager targer = new FunctionFileManager(functionalCofigFile);
		targer.getFeatureList("");
		
	}

}
