package lbi.usp.br.caravela;

import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import lbi.usp.br.caravela.config.FunctionFileManager;
import lbi.usp.br.caravela.config.MappingFileManager;
import lbi.usp.br.caravela.config.SampleConfigFile;
import lbi.usp.br.caravela.config.TaxonomyFileConfig;
import lbi.usp.br.caravela.config.TaxonomyFileManager;
import lbi.usp.br.caravela.dto.Contig;
import lbi.usp.br.caravela.dto.Feature;
import lbi.usp.br.caravela.dto.ReadOnContig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class FileAggregator {
	
	
	private static final String CONTIG_FILE_TYPE = "contig";
	
	private static final Logger logger = LoggerFactory.getLogger(FileAggregator.class);
	
	public FileAggregator() {
		 
	}
	
	public void createJsonFileByConfigFileDirectoryPath(String sampleName, String directoryPath) throws FileNotFoundException{
		SampleConfigFile sampleConfigFile = new SampleConfigFile(sampleName, directoryPath);
		sampleConfigFile.validate();
		
		logger.info(sampleName.toString());
		
		createJsonFile(sampleConfigFile);
		
	}
	
	public void createJsonFileByConfigFileInput(String sampleConfigFilePath) throws Exception {
		FileReader fileReader = new FileReader(sampleConfigFilePath);
		
		BufferedReader br = new BufferedReader(fileReader);
		Gson gson = new Gson();
		
		SampleConfigFile sampleConfigFile = gson.fromJson(br, SampleConfigFile.class);
		sampleConfigFile.validate();
		
		createJsonFile(sampleConfigFile);
	}

	private void createJsonFile(SampleConfigFile sampleConfigFile) throws FileNotFoundException {
		
		File contigFile = getAndVerifyIfFileExists(sampleConfigFile.getContigFilePath(), CONTIG_FILE_TYPE);
		FastaSequenceFile contigFileFasta = new FastaSequenceFile(contigFile, Boolean.TRUE);
		HashMap<String, Integer> taxonomyHashMap = getTaxonomyHashMap(sampleConfigFile);
		MappingFileManager mappingFileManager = new MappingFileManager(sampleConfigFile);
		FunctionFileManager functionFileManager = new FunctionFileManager(sampleConfigFile.getFunctionalCofigFile());
		
		ReferenceSequence nextSequence = contigFileFasta.nextSequence();

		Gson gson = new Gson();
		
		while (nextSequence != null) {
			String contigReference = nextSequence.getName();
			List<Feature> featureList = functionFileManager.getFeatureList(contigReference);
			
			List<ReadOnContig> readsOnContig = mappingFileManager.getReadsOnContig(contigReference, taxonomyHashMap, Boolean.FALSE);
			String contigSequence = StringUtil.bytesToString(nextSequence.getBases());
			
			Contig contig = new Contig(contigReference, contigSequence, featureList , readsOnContig);
			
			nextSequence = contigFileFasta.nextSequence();
			
			System.out.println(gson.toJson(contig));
		}
		
		contigFileFasta.close();
	}
	
	
	private HashMap<String, Integer> getTaxonomyHashMap(SampleConfigFile sampleConfigFile) throws FileNotFoundException {
		TaxonomyFileConfig taxonomyFileConfig = sampleConfigFile.getTaxonomy();
		taxonomyFileConfig.validate();
		TaxonomyFileManager taxonomyFileManager = new TaxonomyFileManager();
		HashMap<String, Integer> taxonomyHashMap = taxonomyFileManager.getTaxonomyHashMap(taxonomyFileConfig);
		if(taxonomyHashMap.isEmpty() ){
			logger.warn("List of taxon is empty!");
		}
		
		return taxonomyHashMap;
				
	}

	private File getAndVerifyIfFileExists(String filePath, String fileType) throws FileNotFoundException{
		File file = new File(filePath);
		if( ! file.exists() || file.isDirectory() ){
			throw new FileNotFoundException(fileType + " file is required.");
		} else {
			return file;
		}
	}
	
	

}
