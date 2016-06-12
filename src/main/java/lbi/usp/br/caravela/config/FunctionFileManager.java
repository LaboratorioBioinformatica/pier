package lbi.usp.br.caravela.config;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lbi.usp.br.caravela.dto.Feature;

public class FunctionFileManager {

	private static final Logger logger = LoggerFactory.getLogger(FunctionFileManager.class);

	private HashMap<String, List<Feature>> featureHashMap;

	public FunctionFileManager(FunctionalCofigFile functionalCofigFile) {
		
		loadFiles(functionalCofigFile);
	}

	public List<Feature> getFeatureList(String sequenceReference) {
		return featureHashMap.get(sequenceReference);
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
