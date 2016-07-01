package lbi.usp.br.caravela.img;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import lbi.usp.br.caravela.exeption.DomainException;

public class PhiloDistFileManager {
	
	private static final String BREAK_LINE = "\\n";
	
	private HashMap<String, IMGPhiloDist> philoDistMap;

	public PhiloDistFileManager() {
	}
	
	public PhiloDistFileManager(String filePath) {
		philoDistMap = load(filePath);
	}

	public HashMap<String, IMGPhiloDist> load(String filePath) {
		HashMap<String, IMGPhiloDist> philoDistMap = new HashMap<String, IMGPhiloDist>();
		try {
			Scanner scanner = new Scanner(new FileReader(filePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				IMGPhiloDist philoDist = new IMGPhiloDist(scanner.next());
				philoDistMap.put(philoDist.getGeneId(), philoDist);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new DomainException("Invalid Philodist File Path", e);
		}
		return philoDistMap;
	}
	
	public IMGPhiloDist getPhiloDistByGeneId(String geneId){
		return philoDistMap.get(geneId);
	}

}
