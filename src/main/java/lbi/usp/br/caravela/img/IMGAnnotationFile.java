package lbi.usp.br.caravela.img;

public interface IMGAnnotationFile {
	
	public int getGeneIdLocation();
	public int getTypeIdLocation();
	public int getIdentityLocation();
	public int getAlignLengthLocation();
	public int getQueryStartLocation();
	public int getQueryEndLocation();
	public int getSubjectStartLocation();
	public int getSubjectEndLocation();
	public int getEvalueLocation();
	public int getBitScoreLocation();

}
