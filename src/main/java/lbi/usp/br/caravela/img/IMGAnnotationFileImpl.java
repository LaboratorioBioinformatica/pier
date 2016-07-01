package lbi.usp.br.caravela.img;

import java.util.HashMap;

public class IMGAnnotationFileImpl implements IMGAnnotationFile {
	
	HashMap<String, Integer> indexFieldMap;
	public IMGAnnotationFileImpl(HashMap<String, Integer> indexFieldMap) {
		this.indexFieldMap = indexFieldMap;
	}

	@Override
	public int getGeneIdLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.GENE_ID_LOCATION.name());
	}

	@Override
	public int getTypeIdLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.TYPE_ID_LOCATION.name());
	}

	@Override
	public int getIdentityLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.IDENTITY_LOCATION.name());
	}

	@Override
	public int getAlignLengthLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.ALIGN_LENGTH_LOCATION.name());
	}

	@Override
	public int getQueryStartLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.QUERY_START_LOCATION.name());
	}

	@Override
	public int getQueryEndLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.QUERY_END_LOCATION.name());
	}

	@Override
	public int getSubjectStartLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.SUBJECT_START_LOCATION.name());
	}

	@Override
	public int getSubjectEndLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.SUBJECT_END_LOCATION.name());
	}

	@Override
	public int getEvalueLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.EVALUE_LOCATION.name());
	}

	@Override
	public int getBitScoreLocation() {
		return indexFieldMap.get(IMGFileFieldIndex.BIT_SCORE_LOCATION.name());
	}

}
