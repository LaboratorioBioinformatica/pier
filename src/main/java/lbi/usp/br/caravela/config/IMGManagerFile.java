package lbi.usp.br.caravela.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import lbi.usp.br.caravela.exeption.DomainValidateException;
import lbi.usp.br.caravela.img.GFFFeature;

public class IMGManagerFile {
	
	private static final String GFF_FILE_KEY = "gff";
	
	private List<GFFFeature> GFFFeatureList; 
	
	public IMGManagerFile(FunctionalCofigFile functionalCofigFile) {
		HashMap<String, String> fileList = functionalCofigFile.getFileList();
		this.GFFFeatureList = getGFFFile(fileList);
		
		
	}
	
	public List<GFFFeature> getGFFFeatureList(){
		return this.GFFFeatureList;
	}
	
	private List<GFFFeature> getGFFFile(HashMap<String, String> fileList) {
		
		List<GFFFeature> GFFFeatures = new ArrayList<GFFFeature>();
		String gffFilePath = fileList.get(GFF_FILE_KEY);
		
		try (Stream<String> stream = Files.lines(Paths.get(gffFilePath))) {
			stream.parallel().forEach(line -> GFFFeatures.add(new GFFFeature(line)));
			
		} catch (IOException e) {
			throw new DomainValidateException("Invalid gff file path", e);
		}
		return GFFFeatures;
		
	}

}
