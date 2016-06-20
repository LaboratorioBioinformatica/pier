package lbi.usp.br.caravela;

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

import lbi.usp.br.caravela.config.SampleConfigFile;
import lbi.usp.br.caravela.dto.Contig;
import lbi.usp.br.caravela.dto.Feature;
import lbi.usp.br.caravela.dto.GeneProduct;
import lbi.usp.br.caravela.dto.PhiloDist;
import lbi.usp.br.caravela.dto.ReadOnContig;
import lbi.usp.br.caravela.dto.Taxon;
import lbi.usp.br.caravela.img.GFFFeature;
import lbi.usp.br.caravela.img.GFFFileManager;
import lbi.usp.br.caravela.img.GeneProductFileManager;
import lbi.usp.br.caravela.img.IMGGeneProduct;
import lbi.usp.br.caravela.img.IMGPhiloDist;
import lbi.usp.br.caravela.img.PhiloDistFileManager;
import lbi.usp.br.caravela.mytaxa.MyTaxaFile;
import lbi.usp.br.caravela.mytaxa.MyTaxaLine;

import com.google.gson.Gson;



public class JsonCreator {
	
	SampleConfigFile sampleConfig;
	Hashtable<String,List<MyTaxaLine>> taxonomyMultiHashTableFromFile;
	GFFFileManager gffFileManager;
	GeneProductFileManager geneProductFileManager;
	PhiloDistFileManager philoDistFileManager;
	
	private static final String INDEX_BAM_EXT = ".bai";
	
	
	public JsonCreator(String sampleConfigFilePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(sampleConfigFilePath));
		Gson gson = new Gson();
		sampleConfig = gson.fromJson(br, SampleConfigFile.class);
		load();
	}

	private void load() throws FileNotFoundException {
		FastaSequenceFile fastaFile = new FastaSequenceFile(new File(sampleConfig.getContigFilePath()), Boolean.FALSE);
		
		String mappingFilePath = sampleConfig.getMappingFilePath();
		SamInputResource resource = SamInputResource.of(new File(mappingFilePath)).index(new File(mappingFilePath.concat(INDEX_BAM_EXT)));
		SamReader myReader = SamReaderFactory.makeDefault().open(resource);
		
		
//		taxonomyMultiHashTableFromFile = new MyTaxaFile().loadTaxonomyMultiHashTableFromFile(sampleConfig.getTaxonomyFilePath());

//		gffFileManager = new GFFFileManager(sampleConfig.getGFFFilePath());
//		geneProductFileManager = new GeneProductFileManager(sampleConfig.getGeneProductFilePath());
//		philoDistFileManager = new PhiloDistFileManager(sampleConfig.getPhiloDistFilePath());
		
		
		
		Gson g = new Gson();
		ReferenceSequence nextSequence = fastaFile.nextSequence();
		
		Integer count = 0; 
		while (nextSequence != null) {
			count++;
			String referenceContig = nextSequence.getName();
			String contigSequence = StringUtil.bytesToString(nextSequence.getBases());
			
			List<Feature> featureList =	createFeatureList(gffFileManager.getFeture(referenceContig));
			
			
			SAMRecordIterator readsOnContigMapping = myReader.query(referenceContig, 0, 0, true);
			
			List<ReadOnContig> readsOnContig = new ArrayList<ReadOnContig>();
			while (readsOnContigMapping.hasNext()) {
				SAMRecord next = readsOnContigMapping.next();
				String readReference = next.getReadName();
				
				Taxon taxon = findTaxonWithGreaterScoreByReadReference(readReference);
				
				ReadOnContig readOnContig = new ReadOnContig(readReference, next.getReadString(), next.getAlignmentStart(), next.getAlignmentEnd(), next.getCigarString(), next.getFlags(), getPair(next.getFirstOfPairFlag()), taxon);
				readsOnContig.add(readOnContig);
				
			}
			Contig contig = new Contig(referenceContig, contigSequence, featureList, readsOnContig);
			
			
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

	private List<Feature> createFeatureList(List<GFFFeature> gffFetureList) {
		List<Feature> features = new ArrayList<Feature>();
		if(gffFetureList != null){
			for (GFFFeature gffFeature : gffFetureList) {
				
				String geneId = gffFeature.getGeneId();
				GeneProduct geneProduct = createGeneProduct(geneProductFileManager.getGeneProductByGeneId(geneId));
				PhiloDist philoDist = createPhiloDist(philoDistFileManager.getPhiloDistByGeneId(geneId));
				Feature feature = createFeature(gffFeature, geneProduct, philoDist);
				features.add(feature);
			}
		}
		
		return features;
	}

	private Feature createFeature(GFFFeature gffFeature, GeneProduct geneProduct, PhiloDist philoDist) {
		Feature feature = new Feature(gffFeature.getSource(), gffFeature.getType(), gffFeature.getStart(), gffFeature.getEnd(), gffFeature.getStrand(), geneProduct, philoDist);
		return feature;
	}

	private PhiloDist createPhiloDist(IMGPhiloDist imgPhiloDist) {
		PhiloDist philoDist = null;
		
		if(imgPhiloDist != null){
			philoDist = new PhiloDist( new Long(imgPhiloDist.getHomologGeneOID()), new Long(imgPhiloDist.getHomologTaxonOID()), new Double(imgPhiloDist.getPercentIdentity()), imgPhiloDist.getLineage());
		}
		
		return philoDist;
	}

	private GeneProduct createGeneProduct(IMGGeneProduct imgGeneProduct) {
		GeneProduct geneProduct = null;
		if(imgGeneProduct != null){
			geneProduct = new GeneProduct(imgGeneProduct.getProductName(), imgGeneProduct.getSource());
		}
		return geneProduct;
	}

	private Taxon findTaxonWithGreaterScoreByReadReference(String readReference) {
		List<MyTaxaLine> mytaxaList = taxonomyMultiHashTableFromFile.get(readReference);
		
		Taxon selectedTaxon = null;
		
		if(mytaxaList != null){
			for (MyTaxaLine line : mytaxaList) {
				String deepestTaxonomy = line.getCleanDeepestTaxonomy();
				Taxon taxon = new Taxon.Builder().setTaxonomyId(new Integer(line.getTaxonomyId()))
						.setScore(new Double(line.getScore()))
						.setScientificName(deepestTaxonomy)
						.setHank(line.getTaxonomyRank())
						.build();
				
				if(selectedTaxon == null ){
					selectedTaxon = taxon;
				} else {
					
					if(taxon.getScore() > selectedTaxon.getScore()){
						selectedTaxon = taxon;
					}
					
				}
			}
			
		}
		
		return selectedTaxon;
	}

	private  Integer getPair(boolean firstOfPairFlag) {
		if(firstOfPairFlag){
			return 1;
		} else {
			return 2;
		}
	}
	
	
	
	
	

}
