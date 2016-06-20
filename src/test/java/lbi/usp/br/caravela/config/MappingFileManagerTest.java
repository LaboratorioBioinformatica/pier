package lbi.usp.br.caravela.config;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.SamInputResource;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lbi.usp.br.caravela.dto.ReadOnContig;
import lbi.usp.br.caravela.dto.Taxon;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class MappingFileManagerTest {
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@PrepareForTest({SamInputResource.class, SamReaderFactory.class})
	@Test
	public void testName() throws Exception {
		String contigPath = "/tmp/contig-filetest.txt";
		String taxonomyPath = "/tmp/filetest.txt";
		String sampleName = "zc3-day-01";
		String contigReference = "asd1f6as1df5a1a66666";
		
		String sequenceName01 = "sequence01";
		String sequence01 = "ACTGGTCCTAATCTGGTGAGTAGATGTAAACATCATCAACCAC";
		String cigar01 = "10M";
		Integer sequence01Start = 1;
		Integer sequence01End = 10;
		Integer sequence01Flag = 96;
		Boolean sequence01Pair = Boolean.TRUE;
		
		String sequenceName02 = "sequence02";
		String sequence02 = "ACTGGTCCTAATCTGGTGAGTAGATGTAAACATCATCAACCACAACATCATCAACCAC";
		String cigar02 = "19M";
		Integer sequence02Start = 8;
		Integer sequence02End = 25;
		Integer sequence02Flag = 101;
		Boolean sequence02Pair = Boolean.FALSE;
		
		Integer taxonomyId01 = 51;
		Integer taxonomyId02 = 52;
		
		Taxon taxon01 = new Taxon.Builder().setTaxonomyId(taxonomyId01).build();
		Taxon taxon02 = new Taxon.Builder().setTaxonomyId(taxonomyId02).build();
		
		File mappingFile = tmpFolder.newFile("zc3-day-01-mapping-sorted.bam");
		File indexMappingFile = tmpFolder.newFile("zc3-day-01-mapping-sorted.bai");
		
		HashMap<String, Integer> taxonomyHashMap = new HashMap<String, Integer>();
		taxonomyHashMap.put(sequenceName01, taxonomyId01);
		taxonomyHashMap.put(sequenceName02, taxonomyId02);
		
		
		
		
		TaxonomyFileConfig taxonomyFileConfig = new TaxonomyFileConfig(TaxonomyProvider.DEFAULT, taxonomyPath);
		SampleConfigFile sampleConfigFile = new SampleConfigFile(sampleName, contigPath, mappingFile.getAbsolutePath(), taxonomyFileConfig, null);
		
		PowerMockito.mockStatic(SamInputResource.class);
		PowerMockito.mockStatic(SamReaderFactory.class);
		
		
		SamInputResource inputResourceMock = Mockito.mock(SamInputResource.class);
		SamReaderFactory readerFactoryMock = Mockito.mock(SamReaderFactory.class);

		SAMRecord samRecord01Mock = createSAMRecordMock(sequenceName01, sequence01, sequence01Start, sequence01End, cigar01, sequence01Flag, sequence01Pair);
		SAMRecord samRecord02Mock = createSAMRecordMock(sequenceName02, sequence02, sequence02Start, sequence02End, cigar02, sequence02Flag, sequence02Pair);
		
		SAMRecordIterator samRecordIteratorMock = createSAMRecordIteratorMock(samRecord01Mock, samRecord02Mock);
		
		SamReader samReaderMock = Mockito.mock(SamReader.class);
		Mockito.when(samReaderMock.query(contigReference, 0,0,Boolean.TRUE)).thenReturn(samRecordIteratorMock);
		
		

		PowerMockito.when(SamInputResource.of(mappingFile)).thenReturn(inputResourceMock);
		PowerMockito.when(inputResourceMock.index(indexMappingFile)).thenReturn(inputResourceMock);
		
		PowerMockito.when(SamReaderFactory.makeDefault()).thenReturn(readerFactoryMock);
		PowerMockito.when(readerFactoryMock.open(inputResourceMock)).thenReturn(samReaderMock);
		

		MappingFileManager target = new MappingFileManager(sampleConfigFile);
		
		
		List<ReadOnContig> expectedReadsOnContig = new ArrayList<ReadOnContig>();
		expectedReadsOnContig.add(createReadOnContig(sequenceName01, sequence01, sequence01Start, sequence01End, cigar01, sequence01Flag, sequence01Pair, taxon01));
		expectedReadsOnContig.add(createReadOnContig(sequenceName02, sequence02, sequence02Start, sequence02End, cigar02, sequence02Flag, sequence02Pair, taxon02));

		List<ReadOnContig> readsOnContig = target.getReadsOnContig(contigReference, taxonomyHashMap);
		
		Assert.assertEquals(expectedReadsOnContig, readsOnContig);
		
		
		
		Mockito.verify(samReaderMock).query(contigReference, 0, 0, Boolean.TRUE);
		Mockito.verify(samRecordIteratorMock, Mockito.times(3)).hasNext();
		
		verifySAMRecordMock(samRecord01Mock);
		verifySAMRecordMock(samRecord02Mock);
		
		Mockito.verify(samRecordIteratorMock).close();
		
		PowerMockito.verifyStatic();
		
		
		
	}

	private SAMRecordIterator createSAMRecordIteratorMock(SAMRecord samRecord01Mock, SAMRecord samRecord02Mock) {
		SAMRecordIterator mock = Mockito.mock(SAMRecordIterator.class);
		Mockito.when(mock.hasNext()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		Mockito.when(mock.next()).thenReturn(samRecord01Mock, samRecord02Mock);
		return mock;
	}

	private ReadOnContig createReadOnContig(String readReference, String sequence01, Integer sequence01Start, Integer sequence01End,String cigar, Integer sequence01Flag, Boolean sequence01Pair, Taxon taxon) {
		return new ReadOnContig(readReference, sequence01, sequence01Start, sequence01End, cigar, sequence01Flag, getPair(sequence01Pair), taxon);
	}

	private void verifySAMRecordMock(SAMRecord samRecordMock) {
		Mockito.verify(samRecordMock).getReadString();
		Mockito.verify(samRecordMock).getAlignmentStart();
		Mockito.verify(samRecordMock).getAlignmentEnd();
		Mockito.verify(samRecordMock).getFlags();
		Mockito.verify(samRecordMock).getFirstOfPairFlag();
	}

	private SAMRecord createSAMRecordMock(String readName, String sequence, Integer sequenceStart, Integer sequenceEnd, String cigar, Integer sequenceFlag, Boolean sequencePair) {
		SAMRecord mock = Mockito.mock(SAMRecord.class);
		Mockito.when(mock.getReadName()).thenReturn(readName);
		Mockito.when(mock.getReadString()).thenReturn(sequence);
		Mockito.when(mock.getAlignmentStart()).thenReturn(sequenceStart);
		Mockito.when(mock.getAlignmentEnd()).thenReturn(sequenceEnd);
		Mockito.when(mock.getCigarString()).thenReturn(cigar);
		Mockito.when(mock.getFlags()).thenReturn(sequenceFlag);
		Mockito.when(mock.getFirstOfPairFlag()).thenReturn(sequencePair);

		return mock;
	}
	
	private  Integer getPair(boolean firstOfPairFlag) {
		if(firstOfPairFlag){
			return 1;
		} else {
			return 2;
		}
	}
	
	
	

}
