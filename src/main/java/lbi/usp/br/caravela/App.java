package lbi.usp.br.caravela;

import java.io.IOException;

import lbi.usp.br.caravela.mytaxa.MytaxaConverterFile;

/**
 * Hello world!
 *
 */
public class App {
	
	
	private static final String MY_TAXA_CONVERTER = "mytaxa";
	private static final String FEATURES_JOIN = "join";

	public static void main(String[] args) throws IOException {
		String app = args[0];
		
		if(app.equals(MY_TAXA_CONVERTER)){
			MytaxaConverterFile.main(args);
		} else if(app.equals(FEATURES_JOIN)){
			JsonCreator mappingReader = new JsonCreator(args[1]);
		} else {
			System.out.println("app: " +  app + " not found!");
		}
	}
}
