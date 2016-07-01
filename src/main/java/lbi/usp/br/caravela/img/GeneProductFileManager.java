package lbi.usp.br.caravela.img;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import lbi.usp.br.caravela.exeption.DomainException;

public class GeneProductFileManager {

	private static final String BREAK_LINE = "\\n";

	private HashMap<String, IMGGeneProduct> geneProductMap;

	public GeneProductFileManager() {
	}
	
	public GeneProductFileManager(String filePath) {
		geneProductMap = load(filePath);
	}

	public HashMap<String, IMGGeneProduct> load(String filePath) {
		HashMap<String, IMGGeneProduct> geneProductMap = new HashMap<String, IMGGeneProduct>();
		try {
			Scanner scanner = new Scanner(new FileReader(filePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				IMGGeneProduct geneProduct = new IMGGeneProduct(scanner.next());
				geneProductMap.put(geneProduct.getGeneId(), geneProduct);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new  DomainException("Invalid Gene Product File Path", e);
		}
		
		return geneProductMap;
	}
	
	public IMGGeneProduct getGeneProductByGeneId(String geneId){
		return geneProductMap.get(geneId);
	}

}
