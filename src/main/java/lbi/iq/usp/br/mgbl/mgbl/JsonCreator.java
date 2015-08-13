package lbi.iq.usp.br.mgbl.mgbl;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.SamInputResource;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import lbi.iq.usp.br.mgbl.mgbl.config.SampleConfigFile;
import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MyTaxaFile;
import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MyTaxaLine;

import com.google.gson.Gson;



public class JsonCreator {
	
	SampleConfigFile sampleConfig;
	Hashtable<String,List<MyTaxaLine>> taxonomyMultiHashTableFromFile;
	
	private static final String INDEX_BAM_EXT = ".bai";
	
	
	public JsonCreator(String sampleConfigFilePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(sampleConfigFilePath));
		Gson gson = new Gson();
		sampleConfig = gson.fromJson(br, SampleConfigFile.class);
		load();
	}

	private void load() throws FileNotFoundException {
		String contigFilePath =  sampleConfig.getContigFilePath();
		String mappingFilePath = sampleConfig.getMappingFilePath();
		
		taxonomyMultiHashTableFromFile = new MyTaxaFile().loadTaxonomyMultiHashTableFromFile(sampleConfig.getTaxonomyFilePath());
		
		
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
				String readReference = next.getReadName();
				
				List<Taxon> taxons = findTaxonsByReadReference(readReference);
				
				ReadOnContig readOnContig = new ReadOnContig(readReference, next.getReadString(), next.getAlignmentStart(), next.getAlignmentEnd(), next.getFlags(), getPair(next.getFirstOfPairFlag()), taxons);
				readsOnContig.add(readOnContig);
				
			}
			Contig contig = new Contig(referenceContig, contigSequence, readsOnContig);
			
			
			String jsonContig = g.toJson(contig);
			System.out.println(jsonContig);
			
			readsOnContigMapping.close();
//			if(count >= 10){
//				break;
//			}
			
			nextSequence = fastaFile.nextSequence();
			
		}
		
		fastaFile.close();
		
	}

	private List<Taxon> findTaxonsByReadReference(String readReference) {
		
		List<MyTaxaLine> mytaxaList = taxonomyMultiHashTableFromFile.get(readReference);
		List<Taxon> taxons = null;
		
		if(mytaxaList != null){
			taxons = new ArrayList<Taxon>();
			for (MyTaxaLine line : mytaxaList) {
				String deepestTaxonomy = line.getCleanDeepestTaxonomy();
				Taxon taxon = new Taxon.Builder().setTaxonomyId(new Integer(line.getTaxonomyId()))
						.setScore(new Double(line.getScore()))
						.setScientificName(deepestTaxonomy)
						.setHank(line.getTaxonomyRank())
						.build();
				taxons.add(taxon);
			}
			
		}
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
