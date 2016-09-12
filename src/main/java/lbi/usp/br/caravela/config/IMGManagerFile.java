package lbi.usp.br.caravela.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lbi.usp.br.caravela.exeption.DomainValidateException;
import lbi.usp.br.caravela.img.GFFFeature;
import lbi.usp.br.caravela.img.IMGFileType;

public class IMGManagerFile {
	
	
	private List<GFFFeature> GFFFeatureList; 
	
	public IMGManagerFile(FunctionalCofigFile functionalCofigFile) {
		String gffIMGFileType = functionalCofigFile.getFilePathByFileType(IMGFileType.GFF_FILE_KEY);
		this.GFFFeatureList = getGFFFile(gffIMGFileType);
		
		
	}
	
	public List<GFFFeature> getGFFFeatureList(){
		return this.GFFFeatureList;
	}
	
	private List<GFFFeature> getGFFFile(String gffIMGFilePath) {
		List<GFFFeature> GFFFeatures = new ArrayList<GFFFeature>();
		try (Stream<String> stream = Files.lines(Paths.get(gffIMGFilePath))) {
			stream.parallel().forEach(line -> GFFFeatures.add(new GFFFeature(line)));
			
		} catch (IOException e) {
			throw new DomainValidateException("Invalid gff file path", e);
		}
		return GFFFeatures;
		
	}

}
