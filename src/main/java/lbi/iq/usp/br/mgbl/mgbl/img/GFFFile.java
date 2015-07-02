package lbi.iq.usp.br.mgbl.mgbl.img;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class GFFFile {
	
	private static final String BREAK_LINE = "\\n";
	
	private List<GFFFeature> featuresList;
	private HashMap<String, PhiloDist> philoDistMap;
	private HashMap<String, GeneProduct> geneProductMap;
	
	
	public GFFFile(String gffFilePath, String philoDistFilePath, String geneProductFilePath) {
		
		featuresList = loadFeaturesFromGFFFile(gffFilePath);
		philoDistMap = loadPhiloDistFromFile(philoDistFilePath);
		geneProductMap = loadGeneProductFromFile(geneProductFilePath);
		
		for (GFFFeature feature : featuresList) {
			
			if(feature.getSeqId().equals("metazooDRAFT_1192597")){
				String geneId = feature.getAttributeByName("locus_tag");
				if(geneId != null ){
					PhiloDist philoDist = philoDistMap.get(geneId);
					GeneProduct geneProduct = geneProductMap.get(geneId);
					
					System.out.println(feature.getSeqId());
					if(geneProduct != null ){
						System.out.println(geneProduct.getProductName() + "\t" + geneProduct.getSource() );
					}
					if(philoDist != null ){
						System.out.println(philoDist.getLineage());
					}
					
				}
			}
			
			
			
		}
	}
	
	public Integer size(){
		return featuresList.size();
	}
	

	private List<GFFFeature> loadFeaturesFromGFFFile(String gffFilePath) {
		List<GFFFeature> fileLines = new ArrayList<GFFFeature>();
		
		try {
			Scanner scanner = new Scanner(new FileReader(gffFilePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				GFFFeature line = new GFFFeature(scanner.next());
				fileLines.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return fileLines;
	}
	
	private HashMap<String, PhiloDist> loadPhiloDistFromFile(String filePath) {
		
		HashMap<String, PhiloDist> philoDistMap = new HashMap<String, PhiloDist>();
		
		try {
			Scanner scanner = new Scanner(new FileReader(filePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				PhiloDist philoDist = new PhiloDist(scanner.next());
				philoDistMap.put(philoDist.getGeneId(), philoDist);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return philoDistMap;
	}
	
	private HashMap<String, GeneProduct> loadGeneProductFromFile(String filePath) {
		
		HashMap<String, GeneProduct> geneProductMap = new HashMap<String, GeneProduct>();
		
		try {
			Scanner scanner = new Scanner(new FileReader(filePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				GeneProduct geneProduct = new GeneProduct(scanner.next());
				geneProductMap.put(geneProduct.getGeneId(), geneProduct);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return geneProductMap;
	}
	
	public static void main(String[] args) {
		GFFFile gffFile = new GFFFile("/data/mgb/img/zc3b/day-01/3300002194.a.gff", "/data/mgb/img/zc3b/day-01/3300002194.a.phylodist.txt", "/data/mgb/img/zc3b/day-01/3300002194.a.gene_product.txt");
		System.out.println(gffFile.size());


	}
	

}
