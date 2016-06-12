package lbi.usp.br.caravela.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.SamInputResource;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import lbi.usp.br.caravela.dto.ReadOnContig;

public class MappingFileManager {
	
	private static final int END_REFERENCE = 0;
	private static final int START_REFERENCE = 0;
	private static final String BAM_EXT = ".bam";
	private static final String INDEX_BAM_EXT = ".bai";
	private static final String MAPPING_FILE_TYPE = "mapping";
	private static final String MAPPING_FILE_INDEX_TYPE = "index mapping";
	
	private final SamReader SAMReader; 

	
	public MappingFileManager(SampleConfigFile sampleConfigFile) throws FileNotFoundException {
		
		File mappingFile = getAndVerifyIfFileExists(sampleConfigFile.getMappingFilePath(), MAPPING_FILE_TYPE); 
		File mappingFileIndex = getAndVerifyIfFileExists(sampleConfigFile.getMappingFilePath().replace(BAM_EXT, INDEX_BAM_EXT), MAPPING_FILE_INDEX_TYPE);
		SamInputResource resource = SamInputResource.of(mappingFile).index(mappingFileIndex);
		SAMReader = SamReaderFactory.makeDefault().open(resource);
	}
	
	
	public List<ReadOnContig> getReadsOnContig(String reference){
		
		SAMRecordIterator queryResult = SAMReader.query(reference, START_REFERENCE, END_REFERENCE, Boolean.TRUE);
		List<ReadOnContig> readsOnContig = new ArrayList<ReadOnContig>();
		
		while(queryResult.hasNext()){
			SAMRecord currentSAMRecord = queryResult.next();
			ReadOnContig readOnContig = new ReadOnContig(reference, currentSAMRecord.getReadString(), currentSAMRecord.getAlignmentStart(), currentSAMRecord.getAlignmentEnd(), currentSAMRecord.getFlags(), getPair(currentSAMRecord.getFirstOfPairFlag()), null);
			readsOnContig.add(readOnContig);
		}
		
		queryResult.close();
		return readsOnContig;
	}
	
	
	private  Integer getPair(boolean firstOfPairFlag) {
		if(firstOfPairFlag){
			return 1;
		} else {
			return 2;
		}
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
