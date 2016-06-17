package lbi.usp.br.caravela;

import lbi.usp.br.caravela.mytaxa.MytaxaConverterFile;

/**
 * Hello world!
 *
 */
public class App {
	
	
	private static final String MY_TAXA_CONVERTER = "mytaxa";
	private static final String FEATURES_JOIN = "join";
	private static final Object AGGREGATOR = "aggregator";

	public static void main(String[] args) throws Exception {
		String app = args[0];
		
		if(app.equals(MY_TAXA_CONVERTER)){
			MytaxaConverterFile.main(args);
		} else if(app.equals(FEATURES_JOIN)){
			JsonCreator mappingReader = new JsonCreator(args[1]);
		} else  if(app.equals(AGGREGATOR)) {
			FileAggregator fileAggregator = new FileAggregator(args[1]);
			fileAggregator.createJsonFile();
			
		}
		else {
			System.out.println("app: " +  app + " not found!");
		}
	}
}
