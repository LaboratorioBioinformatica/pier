package lbi.usp.br.caravela.img;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;
import lbi.usp.br.caravela.config.FunctionalCofigFile;
import lbi.usp.br.caravela.dto.FeatureAnnotation;
import lbi.usp.br.caravela.dto.FeatureAnnotationType;
import lbi.usp.br.caravela.dto.Feature;
import lbi.usp.br.caravela.dto.GeneProduct;
import lbi.usp.br.caravela.dto.PhiloDist;
import lbi.usp.br.caravela.dto.Taxon;

public class IMGFileManager {
	
	private static final String GFF_FILE_KEY = "gff";
	private static final String GENE_PRODUCT_FILE_KEY = "geneProduct";
	private static final String PHILO_DIST_FILE_KEY = "phylodist";
	private static final String GENE_SEQUENCE_FILE_KEY = "geneSequence";
	private static final String GENE_TAXONOMY_FILE_KEY = "geneTaxonomy";
	
	private static final String COG_FILE_KEY = "cog";
	private static final String KO_FILE_KEY = "ko";
	private static final String PFAM_FILE_KEY = "pfam";
	private static final String EC_FILE_KEY = "ec";
	
	public IMGFileManager() {
		
	}
	
	public HashMap<String, List<Feature>>  getFeatureHashMap(FunctionalCofigFile functionalCofigFile){
		HashMap<String, String> fileList = functionalCofigFile.getFileList();
		
		HashMap<String, List<GFFFeature>> hashMapGFFFeature = getHashMapGFFFeature(fileList, getGFFFileManager());
		HashMap<String, IMGGeneProduct> hashMapGeneProduct = getHashMapGeneProduct(fileList, getGeneProductFileManager());
		HashMap<String, IMGPhiloDist>  hashMapPhiloDist = getHashMapPhiloDist(fileList, getPhiloDistFileManager());
		HashMap<String,byte[]> hashMapGeneSequence = getHashMapGeneSequence(fileList);
		HashMap<String,Taxon> hashMapTaxon = getHashMapTaxon(fileList, getTaxonFileManager());
		HashMap<String, List<FeatureAnnotation>> hashMapFeatureAnnotationList = getHashMapFeatureAnnotationList(fileList, getIMGAnnotationFileManager());

		
		HashMap<String, List<Feature>> featureHashMap = new HashMap<String, List<Feature>>();
		
		Set<String> contigReferenceList = hashMapGFFFeature.keySet();
		
		for (String contigReference : contigReferenceList) {
			List<GFFFeature> GFFFeatureList = hashMapGFFFeature.get(contigReference);
			List<Feature> featureList = new ArrayList<Feature>();
			
			GFFFeatureList.forEach(gffFeature -> {
				String geneId = gffFeature.getGeneId();
				GeneProduct geneProduct = getGeneProduct(hashMapGeneProduct.get(geneId));
				PhiloDist philoDist = getPhiloDist(hashMapPhiloDist.get(geneId));
				String geneSequence = StringUtil.bytesToString(hashMapGeneSequence.get(geneId));
				Taxon taxon = hashMapTaxon.get(geneId);
				List<FeatureAnnotation> annotations = hashMapFeatureAnnotationList.get(geneId);
				
				Feature feature = new Feature(gffFeature.getType(), gffFeature.getStart(), gffFeature.getEnd(), gffFeature.getStrand(), geneSequence, taxon, annotations, geneProduct, philoDist);
				featureList.add(feature);
				
			});
			
			featureHashMap.put(contigReference, featureList);
			
		}
		
		return featureHashMap;
		
	}
	
	private HashMap<String, List<FeatureAnnotation>> getHashMapFeatureAnnotationList(HashMap<String, String> fileList, IMGAnnotationFileManager imgAnnotationFileManager) {
		
		HashMap<String, List<FeatureAnnotation>> allFeatureAnnotation = new HashMap<>();
		
		String KOFilePath = fileList.get(KO_FILE_KEY);
		if(KOFilePath != null && ! KOFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.KO ,KOFilePath);
		}
		
		String COGFilePath = fileList.get(COG_FILE_KEY);
		if(COGFilePath != null && ! COGFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.COG ,COGFilePath);
		}
		String ECFilePath = fileList.get(EC_FILE_KEY);
		if(ECFilePath != null && ! ECFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.EC ,ECFilePath);
		}
		String PFAMFilePath = fileList.get(PFAM_FILE_KEY);
		if(PFAMFilePath != null && ! PFAMFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.PFAM ,PFAMFilePath);
		}
		
		return allFeatureAnnotation;
	}


	private HashMap<String, List<FeatureAnnotation>> retreaveAndAggregateFeatureAnnotation(HashMap<String, List<FeatureAnnotation>> allFeatureAnnotation, IMGAnnotationFileManager imgAnnotationFileManager, FeatureAnnotationType fat, String filePath) {
		
		HashMap<String, List<FeatureAnnotation>> featureAnnotation = imgAnnotationFileManager.getFeatureAnnotation(filePath, fat);

		if (featureAnnotation != null && !featureAnnotation.isEmpty()) {
			if (allFeatureAnnotation.isEmpty()) {
				allFeatureAnnotation.putAll(featureAnnotation);
			} else {
				Set<String> keySet = featureAnnotation.keySet();
				for (String geneId : keySet) {
					if (allFeatureAnnotation.containsKey(geneId)) {
						allFeatureAnnotation.get(geneId).addAll(featureAnnotation.get(geneId));
					} else {
						allFeatureAnnotation.put(geneId, featureAnnotation.get(geneId));
					}
				}
			}
		}
		return allFeatureAnnotation;
	}


	private PhiloDist getPhiloDist(IMGPhiloDist imgPhiloDist) {
		PhiloDist philoDist = null;
		if(imgPhiloDist != null){
			philoDist = new PhiloDist(
					new Long(imgPhiloDist.getHomologGeneOID()),
					new Long(imgPhiloDist.getHomologTaxonOID()), 
					new Double(imgPhiloDist.getPercentIdentity()), 
					imgPhiloDist.getLineage()
			);
		}
		return philoDist;
	}


	private GeneProduct getGeneProduct(IMGGeneProduct imgGeneProduct) {
		GeneProduct geneProduct = null;
		if(imgGeneProduct != null){
			geneProduct = new GeneProduct(imgGeneProduct.getProductName(), imgGeneProduct.getSource());
		}
		return geneProduct;
	}


	private HashMap<String,byte[]>  getHashMapGeneSequence(HashMap<String, String> fileList){
		String geneSequenceFilePath = fileList.get(GENE_SEQUENCE_FILE_KEY);
		
		HashMap<String, byte[]> hashMap = new HashMap<String, byte []>();
		if(null != geneSequenceFilePath && ! geneSequenceFilePath.isEmpty()){
			FastaSequenceFile fastaFile = new FastaSequenceFile(new File(geneSequenceFilePath), Boolean.FALSE);
			ReferenceSequence nextSequence = fastaFile.nextSequence();
			while(nextSequence != null){
				hashMap.put(nextSequence.getName(), nextSequence.getBases());
				nextSequence = fastaFile.nextSequence();
			}
			fastaFile.close();
		} 
		
		return hashMap;
	}
	
	private HashMap<String, IMGPhiloDist> getHashMapPhiloDist(HashMap<String, String> fileList, PhiloDistFileManager philoDistFileManager) {
		HashMap<String, IMGPhiloDist> hashMap = new HashMap<String, IMGPhiloDist>();
		String philoDistFilePath = fileList.get(PHILO_DIST_FILE_KEY);
		if(null != philoDistFilePath && ! philoDistFilePath.isEmpty()){
			hashMap.putAll(philoDistFileManager.load(philoDistFilePath));
		} 
		return hashMap;
	}
	
	private HashMap<String, Taxon> getHashMapTaxon(HashMap<String, String> fileList, TaxonFileManager taxonFileManager) {
		HashMap<String, Taxon> hashMap = new HashMap<String, Taxon>();
		String geneTaxonomyFilePath = fileList.get(GENE_TAXONOMY_FILE_KEY);
		if(null != geneTaxonomyFilePath && ! geneTaxonomyFilePath.isEmpty()){
			hashMap.putAll(taxonFileManager.loadAndGetTaxonHashMap(geneTaxonomyFilePath));
		} 
		return hashMap;
	}


	private HashMap<String, IMGGeneProduct> getHashMapGeneProduct(HashMap<String, String> fileList, GeneProductFileManager geneProductFileManager) {
		HashMap<String, IMGGeneProduct> hashMap = new HashMap<String, IMGGeneProduct>();
		String geneProductFilePath = fileList.get(GENE_PRODUCT_FILE_KEY);
		if(null != geneProductFilePath && ! geneProductFilePath.isEmpty()){
			hashMap.putAll(geneProductFileManager.load(geneProductFilePath));
		}
		return hashMap;
		
	}
	
	protected PhiloDistFileManager getPhiloDistFileManager() {
		return new PhiloDistFileManager();
	}
	
	protected TaxonFileManager getTaxonFileManager(){
		return new TaxonFileManager();
	}
	
	protected IMGAnnotationFileManager getIMGAnnotationFileManager() {
		return new IMGAnnotationFileManager();
	}

	private HashMap<String, List<GFFFeature>> getHashMapGFFFeature(HashMap<String, String> fileList, GFFFileManager gffFileManager) {
		return gffFileManager.load(fileList.get(GFF_FILE_KEY));
	}

	protected GFFFileManager getGFFFileManager() {
		return new GFFFileManager();
	}
	
	protected GeneProductFileManager getGeneProductFileManager(){
		return new GeneProductFileManager();
	}

}
