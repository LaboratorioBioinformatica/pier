package lbi.usp.br.caravela.img;

import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lbi.usp.br.caravela.config.FunctionalCofigFile;
import lbi.usp.br.caravela.dto.Feature;
import lbi.usp.br.caravela.dto.FeatureAnnotation;
import lbi.usp.br.caravela.dto.FeatureAnnotationType;
import lbi.usp.br.caravela.dto.GeneProduct;
import lbi.usp.br.caravela.dto.PhiloDist;
import lbi.usp.br.caravela.dto.Taxon;

public class IMGFileManager {
	
	public IMGFileManager() {
		
	}
	
	public HashMap<String, List<Feature>>  getFeatureHashMap(FunctionalCofigFile functionalCofigFile){
		
		HashMap<String, List<GFFFeature>> hashMapGFFFeature = getHashMapGFFFeature(functionalCofigFile, getGFFFileManager());
		HashMap<String, IMGGeneProduct> hashMapGeneProduct = getHashMapGeneProduct(functionalCofigFile, getGeneProductFileManager());
		HashMap<String, IMGPhiloDist>  hashMapPhiloDist = getHashMapPhiloDist(functionalCofigFile, getPhiloDistFileManager());
		HashMap<String,byte[]> hashMapGeneSequence = getHashMapGeneSequence(functionalCofigFile);
		HashMap<String,Taxon> hashMapTaxon = getHashMapTaxon(functionalCofigFile, getTaxonFileManager());
		HashMap<String, List<FeatureAnnotation>> hashMapFeatureAnnotationList = getHashMapFeatureAnnotationList(functionalCofigFile, getIMGAnnotationFileManager());

		
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
	
	private HashMap<String, List<FeatureAnnotation>> getHashMapFeatureAnnotationList(FunctionalCofigFile functionalCofigFile, IMGAnnotationFileManager imgAnnotationFileManager) {
		
		HashMap<String, List<FeatureAnnotation>> allFeatureAnnotation = new HashMap<>();
		
		
		String KOFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.KO_FILE_KEY);
		if(KOFilePath != null && ! KOFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.KO ,KOFilePath);
		}
		
		String COGFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.COG_FILE_KEY);
		if(COGFilePath != null && ! COGFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.COG ,COGFilePath);
		}
		String ECFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.EC_FILE_KEY);
		if(ECFilePath != null && ! ECFilePath.isEmpty()){
			allFeatureAnnotation = retreaveAndAggregateFeatureAnnotation(allFeatureAnnotation, imgAnnotationFileManager, FeatureAnnotationType.EC ,ECFilePath);
		}
		String PFAMFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.PFAM_FILE_KEY);
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


	private HashMap<String,byte[]>  getHashMapGeneSequence(FunctionalCofigFile functionalCofigFile){
		String geneSequenceFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.GENE_SEQUENCE_FILE_KEY);
		
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
	
	private HashMap<String, IMGPhiloDist> getHashMapPhiloDist(FunctionalCofigFile functionalCofigFile, PhiloDistFileManager philoDistFileManager) {
		HashMap<String, IMGPhiloDist> hashMap = new HashMap<String, IMGPhiloDist>();
		String philoDistFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.PHILO_DIST_FILE_KEY);
		if(null != philoDistFilePath && ! philoDistFilePath.isEmpty()){
			hashMap.putAll(philoDistFileManager.load(philoDistFilePath));
		} 
		return hashMap;
	}
	
	private HashMap<String, Taxon> getHashMapTaxon(FunctionalCofigFile functionalCofigFile, TaxonFileManager taxonFileManager) {
		HashMap<String, Taxon> hashMap = new HashMap<String, Taxon>();
		String geneTaxonomyFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.GENE_TAXONOMY_FILE_KEY);
		if(null != geneTaxonomyFilePath && ! geneTaxonomyFilePath.isEmpty()){
			hashMap.putAll(taxonFileManager.loadAndGetTaxonHashMap(geneTaxonomyFilePath));
		} 
		return hashMap;
	}


	private HashMap<String, IMGGeneProduct> getHashMapGeneProduct(FunctionalCofigFile functionalCofigFile, GeneProductFileManager geneProductFileManager) {
		HashMap<String, IMGGeneProduct> hashMap = new HashMap<String, IMGGeneProduct>();
		String geneProductFilePath = functionalCofigFile.getFilePathByFileType(IMGFileType.GENE_PRODUCT_FILE_KEY);
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

	private HashMap<String, List<GFFFeature>> getHashMapGFFFeature(FunctionalCofigFile functionalCofigFile, GFFFileManager gffFileManager) {
		String filePathByFileType = functionalCofigFile.getFilePathByFileType(IMGFileType.GFF_FILE_KEY);
		return gffFileManager.load(filePathByFileType);
	}

	protected GFFFileManager getGFFFileManager() {
		return new GFFFileManager();
	}
	
	protected GeneProductFileManager getGeneProductFileManager(){
		return new GeneProductFileManager();
	}

}
