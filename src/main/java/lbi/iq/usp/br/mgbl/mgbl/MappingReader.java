package lbi.iq.usp.br.mgbl.mgbl;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.SamInputResource;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;

import java.io.File;
import java.io.IOException;

import sun.org.mozilla.javascript.internal.json.JsonParser;


public class MappingReader {
	private static final String INDEX_BAM_EXT = ".bai";
	
	
	public MappingReader(String contigFilePath, String mappingFilePath) {
		load(contigFilePath, mappingFilePath);
	}

	private void load(String contigFilePath, String mappingFilePath) {
		
		
		
		FastaSequenceFile fastaFile = new FastaSequenceFile(new File(contigFilePath), Boolean.FALSE);
		ReferenceSequence nextSequence = fastaFile.nextSequence();
		
		SamInputResource resource = SamInputResource.of(new File(mappingFilePath)).index(new File(mappingFilePath.concat(INDEX_BAM_EXT)));
		SamReader myReader = SamReaderFactory.makeDefault().open(resource);
		
		
		Integer count = 0; 
		while (nextSequence != null) {
			
			count++;
			
			String contigSequence = StringUtil.bytesToString(nextSequence.getBases());
			
			
			System.out.println(contigSequence);
			SAMRecordIterator readsOnContigMapping = myReader.query(nextSequence.getName(), 0, 0, true);
			
			
			while (readsOnContigMapping.hasNext()) {
				SAMRecord next = readsOnContigMapping.next();
				System.out.println(next.getReadName() +" = " +  next.getReadString());
			}
			System.out.println("##########################################################################");
			
			readsOnContigMapping.close();
			if(count >= 10){
				break;
			}
			
			nextSequence = fastaFile.nextSequence();
			
		}
		
		fastaFile.close();
		
	}
	
	
	
	
	

}
