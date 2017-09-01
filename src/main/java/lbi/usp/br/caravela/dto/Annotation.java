package lbi.usp.br.caravela.dto;

public class Annotation {
	private String type;
	private String name;
	private Double identity;
	private Integer alignLength;
	private Integer queryStart;
	private Integer queryEnd;
	private Integer subjectStart;
	private Integer subjectEnd;
	private Double evalue;
	private Double bitScore;

	public Annotation(String annotationType, String annotationName, Double identity, Integer alignLength,
			Integer queryStart, Integer queryEnd, Integer subjectStart, Integer subjectEnd, Double evalue,
			Double bitScore) {
		this.type = annotationType;
		this.name = annotationName;
		this.identity = identity;
		this.alignLength = alignLength;
		this.queryStart = queryStart;
		this.queryEnd = queryEnd;
		this.subjectStart = subjectStart;
		this.subjectEnd = subjectEnd;
		this.evalue = evalue;
		this.bitScore = bitScore;
	}
}