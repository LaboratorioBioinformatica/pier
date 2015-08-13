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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MyTaxaFile;
import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MyTaxaLine;

import com.google.gson.Gson;

import sun.org.mozilla.javascript.internal.json.JsonParser;


public class JsonCreator {
	private static final String INDEX_BAM_EXT = ".bai";
	
	
	public JsonCreator(String contigFilePath, String mappingFilePath) throws FileNotFoundException {
		load(contigFilePath, mappingFilePath);
	}

	private void load(String contigFilePath, String mappingFilePath) throws FileNotFoundException {
		
		
//		String mytaxaSampleFilePath = "";
//		List<MyTaxaLine> loadTaxonomyFileList = new MyTaxaFile().loadTaxonomyFileList(mytaxaSampleFilePath);
		
		
		FastaSequenceFile fastaFile = new FastaSequenceFile(new File(contigFilePath), Boolean.FALSE);
		ReferenceSequence nextSequence = fastaFile.nextSequence();
		
		SamInputResource resource = SamInputResource.of(new File(mappingFilePath)).index(new File(mappingFilePath.concat(INDEX_BAM_EXT)));
		SamReader myReader = SamReaderFactory.makeDefault().open(resource);
		
		Gson g = new Gson();
		
		
		Integer count = 0; 
		while (nextSequence != null) {
			count++;
			
			String referenceContig = nextSequence.getName();
			String contigSequence = StringUtil.bytesToString(nextSequence.getBases());
			
			SAMRecordIterator readsOnContigMapping = myReader.query(referenceContig, 0, 0, true);
			
			List<ReadOnContig> readsOnContig = new ArrayList<ReadOnContig>();
			while (readsOnContigMapping.hasNext()) {
				SAMRecord next = readsOnContigMapping.next();
				
				List<Taxon> taxons = findTaxonsByReadReference();
				
				
//				System.out.println(next.getReadName() +" = " +  );
				ReadOnContig readOnContig = new ReadOnContig(next.getReadName(), next.getReadString(), next.getAlignmentStart(), next.getAlignmentEnd(), next.getFlags(), getPair(next.getFirstOfPairFlag()), taxons);
				readsOnContig.add(readOnContig);
				
			}
			Contig contig = new Contig(referenceContig, contigSequence, readsOnContig);
			
			
			String jsonContig = g.toJson(contig);
			System.out.println(jsonContig);
			
			readsOnContigMapping.close();
			if(count >= 10){
				break;
			}
			
			nextSequence = fastaFile.nextSequence();
			
		}
		
		fastaFile.close();
		System.out.println("DONE!");
		
	}

	private List<Taxon> findTaxonsByReadReference() {
		Taxon taxon = new Taxon.Builder().setScientificName("bixo x").build();
		
		List<Taxon> taxons = new ArrayList<Taxon>();
		taxons.add(taxon);
		return taxons;
	}

	private  String getPair(boolean firstOfPairFlag) {
		
		if(firstOfPairFlag){
			return "first";
		} else {
			return "second";
		}
		
	}
	
	
	
	
	

}
