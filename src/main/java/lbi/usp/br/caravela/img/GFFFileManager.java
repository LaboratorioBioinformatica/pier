package lbi.usp.br.caravela.img;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GFFFileManager {

	private static final String BREAK_LINE = "\\n";
	private HashMap<String, List<GFFFeature>> multiHashMapGFFFeature;
	
	public GFFFileManager(String filePath) {
		multiHashMapGFFFeature = load(filePath);
	}
	
	public List<GFFFeature> getFeture(String sequenceReference){
		return multiHashMapGFFFeature.get(sequenceReference);
	}

	public Integer getNumberOfFeaturesBySequenceReference(String sequenceReference){
		List<GFFFeature> fetureList = getFeture(sequenceReference);
		return fetureList.size();
	}
	
	private HashMap<String, List<GFFFeature>> load(String gffFilePath) {
		HashMap<String, List<GFFFeature>> multiHashMap = new  HashMap<String, List<GFFFeature>>();
		try {
			Scanner scanner = new Scanner(new FileReader(gffFilePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				GFFFeature gffFeature = new GFFFeature(scanner.next());
				List<GFFFeature> featureList = multiHashMap.get(gffFeature.getSeqId());
				if(featureList != null){
					featureList.add(gffFeature);
				} else {
					List<GFFFeature> newFeatureList = new ArrayList<GFFFeature>();
					newFeatureList.add(gffFeature);
					multiHashMap.put(gffFeature.getSeqId(), newFeatureList);
				}
				
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return multiHashMap;
	}
	

}
