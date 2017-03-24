package lbi.usp.br.caravela;

import lbi.usp.br.caravela.clark.ClackFile;
import lbi.usp.br.caravela.config.ReadIDCleaner;
import lbi.usp.br.caravela.mytaxa.MyTaxaFile;

/**
 * Hello world!
 *
 */
public class App {
	
	
	private static final String MY_TAXA_CONVERTER = "mytaxa";
	private static final String MY_TAXA_TO_PIER = "mytaxaToPier";
	private static final String CLARK_TO_PIER = "clarkToPier";
	private static final String FEATURES_JOIN = "join";
	private static final String AGGREGATOR = "aggregator";
	private static final String CLEAR_PAIR_INFO_FROM_READ_ID = "clearPairInfoFromReadId";

	public static void main(String[] args) throws Exception {
		try {
			String app = args[0];
			
			if(app.equals(MY_TAXA_CONVERTER)){
	//			MytaxaConverterFile.main(args);
			} else if(app.equals(MY_TAXA_TO_PIER)){
				new MyTaxaFile().mytaxaFileToPierDefaultFile(args[1], args[2]);
			} else if(app.equals(FEATURES_JOIN)){
				JsonCreator mappingReader = new JsonCreator(args[1]);
			} else  if(app.equals(AGGREGATOR)) {
				FileAggregator fileAggregator = new FileAggregator();
				String sampleDirectoryPath = args[2];
				if(sampleDirectoryPath.isEmpty()){
					fileAggregator.createJsonFileByConfigFileInput(args[1]);
				} else {
					fileAggregator.createJsonFileByConfigFileDirectoryPath(args[1], sampleDirectoryPath);
				}
			}
			else if(app.equals(CLARK_TO_PIER)){
				ClackFile clackFile = new ClackFile();
				clackFile.toPierFile(args[1]);
			} else if(app.equals(CLEAR_PAIR_INFO_FROM_READ_ID)){
				ReadIDCleaner readIDCleaner = new ReadIDCleaner();
				readIDCleaner.removePairInformationOnReadId(args[1]);
			} 
			
			else {
				System.out.println("app: " +  app + " not found!");
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println("OPTIONS:");
			System.out.println("aggregator <sample-config-file.json>");
			System.out.println("aggregator <sample-name> <directory-path>");
			System.out.println("mytaxaToPier <mytaxafilepath> <outputfilepath>");
			System.out.println("clarkToPier <clarkfilepath>");
			System.out.println("clearPairInfoFromReadId <taxonomy-file-path>");
			
		} catch (Exception e) {
			throw e;
		}
	}
}
