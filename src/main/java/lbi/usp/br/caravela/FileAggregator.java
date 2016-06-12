package lbi.usp.br.caravela;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import htsjdk.samtools.SamInputResource;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.reference.FastaSequenceFile;
import lbi.usp.br.caravela.config.SampleConfigFile;
import lbi.usp.br.caravela.config.TaxonomyFileConfig;
import lbi.usp.br.caravela.config.TaxonomyFileManager;

public class FileAggregator {
	
	
	private static final String CONTIG_FILE_TYPE = "contig";
	private static final String TAXONOMY_FILE_TYPE = "taxonomy";
	private static final String MAPPING_FILE_TYPE = "mapping";
	private static final String BAM_EXT = ".bam";
	private static final String INDEX_BAM_EXT = ".bai";
	private final FileReader fileReader;
	
	private static final Logger logger = LoggerFactory.getLogger(FileAggregator.class);

	public FileAggregator(String sampleFilePath) throws FileNotFoundException {
		fileReader = new FileReader(sampleFilePath);
	}
	
	public void createJsonFile() throws Exception {
		
		BufferedReader br = new BufferedReader(fileReader);
		Gson gson = new Gson();
		
		SampleConfigFile sampleConfigFile = gson.fromJson(br, SampleConfigFile.class);
		sampleConfigFile.validate();
		
		File contigFile = getAndVerifyIfFileExists(sampleConfigFile.getContigFilePath(), CONTIG_FILE_TYPE);
		FastaSequenceFile contigFileFasta = new FastaSequenceFile(contigFile, Boolean.FALSE);

		File mappingFile = getAndVerifyIfFileExists(sampleConfigFile.getMappingFilePath(), MAPPING_FILE_TYPE); 
		File mappingFileIndex = getAndVerifyIfFileExists(sampleConfigFile.getMappingFilePath().replace(BAM_EXT, INDEX_BAM_EXT), MAPPING_FILE_TYPE);
		
		HashMap<String,Integer> taxonomyHashMap = getTaxonomyHashMap(sampleConfigFile);
		
		SamInputResource resource = SamInputResource.of(mappingFile).index(mappingFileIndex);
		SamReader myReader = SamReaderFactory.makeDefault().open(resource);
		
		
		
		
	}

	
	private HashMap<String, Integer> getTaxonomyHashMap(SampleConfigFile sampleConfigFile) throws FileNotFoundException {
		TaxonomyFileConfig taxonomyFileConfig = sampleConfigFile.getTaxonomy();
		taxonomyFileConfig.validate();
		TaxonomyFileManager taxonomyFileManager = new TaxonomyFileManager();
		HashMap<String,Integer> taxonomyHashMap = taxonomyFileManager.getTaxonomyHashMap(taxonomyFileConfig);
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
