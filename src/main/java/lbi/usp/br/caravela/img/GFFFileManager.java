package lbi.usp.br.caravela.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import lbi.usp.br.caravela.exeption.DomainValidateException;

public class GFFFileManager {

	private static final int ZERO = 0;
	
	private HashMap<String, List<GFFFeature>> multiHashMapGFFFeature;
	
	public GFFFileManager() {}
	
	public GFFFileManager(String filePath) {
		load(filePath);
	}
	
	public List<GFFFeature> getFeture(String sequenceReference){
		if(multiHashMapGFFFeature != null ){
			return multiHashMapGFFFeature.get(sequenceReference);
		} else {
			return null;
		}
	}

	public Integer getNumberOfFeaturesBySequenceReference(String sequenceReference){
		List<GFFFeature> fetureList = getFeture(sequenceReference);
		if(fetureList != null){
			return fetureList.size();
		} else {
			return ZERO;
		}
	}
	
	public HashMap<String, List<GFFFeature>> load(String gffFilePath) {
		System.out.println("GFF Paht: " + gffFilePath);
		HashMap<String, List<GFFFeature>> multiHashMap = new  HashMap<String, List<GFFFeature>>();
		try (Stream<String> stream = Files.lines(Paths.get(gffFilePath))) {
			stream.forEach(line-> {
				GFFFeature gffFeature = new GFFFeature(line);
				List<GFFFeature> featureList = multiHashMap.get(gffFeature.getSeqId());
				if(featureList != null){
					featureList.add(gffFeature);
				} else {
					List<GFFFeature> newFeatureList = new ArrayList<GFFFeature>();
					newFeatureList.add(gffFeature);
					multiHashMap.put(gffFeature.getSeqId(), newFeatureList);
				}
				
			});
			
		} catch (IOException e) {
			throw new DomainValidateException("Invalid gff file path", e);
		}
		this.multiHashMapGFFFeature = multiHashMap;
		return multiHashMap;
	}

}
