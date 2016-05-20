package lbi.usp.br.caravela.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class TaxonomyFileManager {
	
	private static final String BREAK_LINE_OR_TAB = "\\n|\\t";
	private static final String SPACE = " ";
	
	public TaxonomyFileManager(){}
	
	public HashMap<String, Integer> getTaxonomyHashMap(TaxonomyFileConfig taxonomyFileConfig) throws FileNotFoundException {
		taxonomyFileConfig.validate();
		File file = new File(taxonomyFileConfig.getTaxonomyFilePath());
		if( ! file.exists() || file.isDirectory() ){
			throw new FileNotFoundException("taxonomy file is required.");
		}
		TaxonomyProvider providerName = taxonomyFileConfig.getProviderName();
		if(TaxonomyProvider.DEFAULT.equals(providerName)){
			return createTaxonomyHashMapByFromDefautTaxonomyProvider(file);
		} else {
			return null;
		}
	}
	
	private HashMap<String, Integer> createTaxonomyHashMapByFromDefautTaxonomyProvider(File file) throws FileNotFoundException{
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		Scanner scanner = new Scanner(new FileReader(file));
		scanner.useDelimiter(BREAK_LINE_OR_TAB);
		
		while(scanner.hasNext()){
			String completeReadId = scanner.next();
			String[] completReadIdSplitted = completeReadId.split(SPACE);
			String readId = completReadIdSplitted[0];
			String taxonomyId = scanner.next();
			hashMap.put(readId.trim(),  Integer.valueOf(taxonomyId.trim()));
			
		}
		scanner.close();
		
		return hashMap;
	}

}
