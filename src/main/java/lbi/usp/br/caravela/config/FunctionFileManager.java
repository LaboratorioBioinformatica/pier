package lbi.usp.br.caravela.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import lbi.usp.br.caravela.dto.Feature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionFileManager {

	private static final Logger logger = LoggerFactory.getLogger(FunctionFileManager.class);

	private HashMap<String, List<Feature>> featureHashMap;

	public FunctionFileManager(FunctionalCofigFile functionalCofigFile) {
		loadFiles(functionalCofigFile);
	}

	public List<Feature> getFeatureList(String sequenceReference) {
		 List<Feature> list = featureHashMap.get(sequenceReference);
		 if(list == null){
			 list = Collections.emptyList();
		 } 
		return list;
	}

	private void loadFiles(FunctionalCofigFile functionalCofigFile) {
		functionalCofigFile.validate();
		FunctionProvider provider = functionalCofigFile.getProvider();
		if (FunctionProvider.NO.equals(provider)) {
			logger.info("There are no functional annotations");
			this.featureHashMap = new HashMap<String, List<Feature>>();
		}
	}

}
