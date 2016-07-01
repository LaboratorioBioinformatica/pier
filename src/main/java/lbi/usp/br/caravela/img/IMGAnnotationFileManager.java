package lbi.usp.br.caravela.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import lbi.usp.br.caravela.dto.FeatureAnnotation;
import lbi.usp.br.caravela.dto.FeatureAnnotationType;
import lbi.usp.br.caravela.exeption.DomainException;

public class IMGAnnotationFileManager {
	
	private static final String TAB = "\t";
	
	public IMGAnnotationFileManager() {
		
	}
	
	public HashMap<String, List<FeatureAnnotation>> getFeatureAnnotation(String filePath, FeatureAnnotationType featureAnnotationType){
		IMGAnnotationFile faf = FactoryIMGAnnotationFile.get(featureAnnotationType);
		
		HashMap<String, List<FeatureAnnotation>> hashMap = new HashMap<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath))){
			stream.map(line -> Arrays.asList(line.split(TAB)))
			.forEach(featureAnnotationLine->{
				
				String geneId = featureAnnotationLine.get(faf.getGeneIdLocation());
				List<FeatureAnnotation> featureAnnotations = hashMap.get(geneId);
				
				if(featureAnnotations == null) {
					featureAnnotations = new ArrayList<>();
					featureAnnotations.add(createFeatureAnnotation(featureAnnotationLine, featureAnnotationType, faf));
					hashMap.put(geneId, featureAnnotations);
				} else {
					featureAnnotations.add(createFeatureAnnotation(featureAnnotationLine, featureAnnotationType, faf));
				}
				
			});
			
		} catch (IOException e) {
			throw new DomainException("Invalid File Path", e);
		}
		
		return hashMap;
	}
	
	private FeatureAnnotation createFeatureAnnotation(List<String> a, FeatureAnnotationType featureAnnotationType, IMGAnnotationFile faf) {
		
		return new FeatureAnnotation(featureAnnotationType, 
				a.get(faf.getTypeIdLocation()), 
				Double.parseDouble(a.get(faf.getIdentityLocation())), 
				Integer.parseInt(a.get(faf.getAlignLengthLocation())), 
				Integer.parseInt(a.get(faf.getQueryStartLocation())),
				Integer.parseInt(a.get(faf.getQueryEndLocation())), 
				Integer.parseInt(a.get(faf.getSubjectStartLocation())), 
				Integer.parseInt(a.get(faf.getSubjectEndLocation())), 
				Double.parseDouble(a.get(faf.getEvalueLocation())), 
				Double.parseDouble(a.get(faf.getBitScoreLocation())));
	}


}
