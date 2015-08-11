package lbi.iq.usp.br.mgbl.mgbl.img;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class GFFFile {
	
	private static final String GENE_ID_ATTRIBUTE_NAME = "locus_tag";

	private static final String BREAK_LINE = "\\n";
	
	private List<GFFFeature> featuresList;
	private HashMap<String, PhiloDist> philoDistMap;
	private HashMap<String, GeneProduct> geneProductMap;
	
	
	public GFFFile(String gffFilePath, String philoDistFilePath, String geneProductFilePath) {
		
		featuresList = loadFeaturesFromGFFFile(gffFilePath);
		philoDistMap = loadPhiloDistFromFile(philoDistFilePath);
		geneProductMap = loadGeneProductFromFile(geneProductFilePath);
		
	}
	
	public void methodNameTobeDefine(){
		for (GFFFeature feature : featuresList) {
			
			if(feature.getSeqId().equals("metazooDRAFT_1192597")){
				String geneId = feature.getAttributeByName(GENE_ID_ATTRIBUTE_NAME);
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
	
	public List<GFFFeature> getFeture(String sequenceReference){
		List<GFFFeature> features = new ArrayList<GFFFeature>();
		for (GFFFeature feature : featuresList) {
			if(feature.getSeqId().equals(sequenceReference)){
				features.add(feature);
			}
		}
		return features;
		
	}

	public Integer getNumberOfFeaturesBySequenceReference(String sequenceReference){
		List<GFFFeature> fetureList = getFeture(sequenceReference);
		return fetureList.size();
	}
	
	public GeneProduct getGeneProduct(GFFFeature gffFeture){
		String geneId = gffFeture.getAttributeByName(GENE_ID_ATTRIBUTE_NAME);
		GeneProduct geneProduct = null;
		if(geneId != null ){
			geneProduct = geneProductMap.get(geneId);
		} 
		return geneProduct;
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
		
		List<GFFFeature> fetureBySequenceReference = gffFile.getFeture("metazooDRAFT_1192417");
		System.out.println("TOTAL DE FEATURES:" + fetureBySequenceReference.size());
		for (GFFFeature gffFeature : fetureBySequenceReference) {
			System.out.println(gffFeature.getType());
			System.out.println(gffFeature.getStart() + "-" + gffFeature.getEnd());
			GeneProduct geneProduct = gffFile.getGeneProduct(gffFeature);
			if(geneProduct != null){
				System.out.println(geneProduct.getSource() + " : " + geneProduct.getProductName());
			}
		}


	}
	

}
