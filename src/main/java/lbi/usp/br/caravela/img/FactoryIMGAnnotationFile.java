package lbi.usp.br.caravela.img;

import java.util.HashMap;

import lbi.usp.br.caravela.dto.FeatureAnnotationType;

public class FactoryIMGAnnotationFile {
	
	public FactoryIMGAnnotationFile() {}
	
	public static IMGAnnotationFile get(FeatureAnnotationType type){
		
		IMGAnnotationFile imgAnnotationFile = null;
		if(FeatureAnnotationType.COG.equals(type)){
			HashMap<String, Integer> indexFieldMap = new HashMap<String, Integer>();
			indexFieldMap.put(IMGFileFieldIndex.GENE_ID_LOCATION.name(), 0);
			indexFieldMap.put(IMGFileFieldIndex.TYPE_ID_LOCATION.name(), 1);
			indexFieldMap.put(IMGFileFieldIndex.IDENTITY_LOCATION.name(), 2);
			indexFieldMap.put(IMGFileFieldIndex.ALIGN_LENGTH_LOCATION.name(), 3);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_START_LOCATION.name(), 4);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_END_LOCATION.name(), 5);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_START_LOCATION.name(), 6);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_END_LOCATION.name(), 7);
			indexFieldMap.put(IMGFileFieldIndex.EVALUE_LOCATION.name(), 8);
			indexFieldMap.put(IMGFileFieldIndex.BIT_SCORE_LOCATION.name(), 9);
			
			imgAnnotationFile =  new IMGAnnotationFileImpl(indexFieldMap);
		}
		
		if(FeatureAnnotationType.PFAM.equals(type)){
			HashMap<String, Integer> indexFieldMap = new HashMap<String, Integer>();
			indexFieldMap.put(IMGFileFieldIndex.GENE_ID_LOCATION.name(), 0);
			indexFieldMap.put(IMGFileFieldIndex.TYPE_ID_LOCATION.name(), 1);
			indexFieldMap.put(IMGFileFieldIndex.IDENTITY_LOCATION.name(), 2);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_START_LOCATION.name(), 3);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_END_LOCATION.name(), 4);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_START_LOCATION.name(), 5);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_END_LOCATION.name(), 6);
			indexFieldMap.put(IMGFileFieldIndex.EVALUE_LOCATION.name(), 7);
			indexFieldMap.put(IMGFileFieldIndex.BIT_SCORE_LOCATION.name(), 8);
			indexFieldMap.put(IMGFileFieldIndex.ALIGN_LENGTH_LOCATION.name(), 9);
			
			imgAnnotationFile =  new IMGAnnotationFileImpl(indexFieldMap);
		}
		
		if(FeatureAnnotationType.KO.equals(type)){
			HashMap<String, Integer> indexFieldMap = new HashMap<String, Integer>();
			indexFieldMap.put(IMGFileFieldIndex.GENE_ID_LOCATION.name(), 0);
			indexFieldMap.put(IMGFileFieldIndex.TYPE_ID_LOCATION.name(), 2);
			indexFieldMap.put(IMGFileFieldIndex.IDENTITY_LOCATION.name(), 3);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_START_LOCATION.name(), 4);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_END_LOCATION.name(), 5);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_START_LOCATION.name(), 6);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_END_LOCATION.name(), 7);
			indexFieldMap.put(IMGFileFieldIndex.EVALUE_LOCATION.name(), 8);
			indexFieldMap.put(IMGFileFieldIndex.BIT_SCORE_LOCATION.name(), 9);
			indexFieldMap.put(IMGFileFieldIndex.ALIGN_LENGTH_LOCATION.name(), 10);
			
			imgAnnotationFile =  new IMGAnnotationFileImpl(indexFieldMap);
		}
		
		if(FeatureAnnotationType.EC.equals(type)){
			HashMap<String, Integer> indexFieldMap = new HashMap<String, Integer>();
			indexFieldMap.put(IMGFileFieldIndex.GENE_ID_LOCATION.name(), 0);
			indexFieldMap.put(IMGFileFieldIndex.TYPE_ID_LOCATION.name(), 2);
			indexFieldMap.put(IMGFileFieldIndex.IDENTITY_LOCATION.name(), 3);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_START_LOCATION.name(), 4);
			indexFieldMap.put(IMGFileFieldIndex.QUERY_END_LOCATION.name(), 5);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_START_LOCATION.name(), 6);
			indexFieldMap.put(IMGFileFieldIndex.SUBJECT_END_LOCATION.name(), 7);
			indexFieldMap.put(IMGFileFieldIndex.EVALUE_LOCATION.name(), 8);
			indexFieldMap.put(IMGFileFieldIndex.BIT_SCORE_LOCATION.name(), 9);
			indexFieldMap.put(IMGFileFieldIndex.ALIGN_LENGTH_LOCATION.name(), 10);
			
			imgAnnotationFile =  new IMGAnnotationFileImpl(indexFieldMap);
		}
		return imgAnnotationFile;
	}
	
}

