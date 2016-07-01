package lbi.usp.br.caravela.img;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import lbi.usp.br.caravela.dto.FeatureAnnotation;
import lbi.usp.br.caravela.dto.FeatureAnnotationType;
import lbi.usp.br.caravela.exeption.DomainException;

public class IMGAnnotationFileManagerTest {

	private final String BREAK_LINE = System.lineSeparator();
	
	@Rule
	public ExpectedException exException = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	
	@Test
	public void testGetFeatureAnnotation() throws Exception {
		
		String geneID01 = "Ga0131843_1518711";
		String geneID02 = "Ga0131843_10000910";
		
		String ECLine01 = createFileLine(geneID01, "Yes", "EC:2.8.1.1", "98.19", "1", "166", "126", "291", "1.4e-123", "395", "166");
		String ECLine02 = createFileLine(geneID01, "Yes", "EC:2.8.1.2", "98.19", "1", "166", "126", "291", "1.4e-123", "395", "166");
		String ECLine03 = createFileLine(geneID02, "Yes", "EC:2.7.1.31", "94.10", "1", "150", "120", "255", "1.8e-15", "345", "158");
		
		FeatureAnnotation EC01 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.1", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
		FeatureAnnotation EC02 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.2", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
		
		FeatureAnnotation EC03 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.7.1.31", Double.parseDouble("94.10"), Integer.parseInt("1"), Integer.parseInt("150"), Integer.parseInt("120"), Integer.parseInt("255"), Double.parseDouble("1.8e-15"), Double.parseDouble("345"), Integer.parseInt("158"));
	
		File ECFile = tmpFolder.newFile("ec-file.tsv");
		
		String ECFilePath = ECFile.getAbsolutePath();
		
		FileWriter ECFileWriter = new FileWriter(ECFile);
		
		ECFileWriter.write(ECLine01+BREAK_LINE);
		ECFileWriter.write(ECLine02+BREAK_LINE);
		ECFileWriter.write(ECLine03);
		ECFileWriter.close();
		
		HashMap<String, List<FeatureAnnotation>> expectedHashMapFeatureAnnotatitionList = new HashMap<>();
		List<FeatureAnnotation> featureAnnotationListGeneId01 = new ArrayList<>();
		featureAnnotationListGeneId01.add(EC01);
		featureAnnotationListGeneId01.add(EC02);
		List<FeatureAnnotation> featureAnnotationListGeneId02 = new ArrayList<>();
		featureAnnotationListGeneId02.add(EC03);
		
		expectedHashMapFeatureAnnotatitionList.put(geneID01, featureAnnotationListGeneId01);
		expectedHashMapFeatureAnnotatitionList.put(geneID02, featureAnnotationListGeneId02);
		
		IMGAnnotationFileManager target = new IMGAnnotationFileManager();
		 HashMap<String,List<FeatureAnnotation>> featureAnnotationResult = target.getFeatureAnnotation(ECFilePath, FeatureAnnotationType.EC);
		 
		 Assert.assertEquals(expectedHashMapFeatureAnnotatitionList, featureAnnotationResult);
		
	}
	
	
//	@Test
//	public void testGetFeatureAnnotationFromAllType() throws Exception {
//		
//		String geneID01 = "Ga0131843_1518711";
//		
//		
//		String geneID02 = "Ga0131843_10000910";
//		
//		String ECLine01 = createFileLine(geneID01, "Yes", "EC:2.8.1.1", "98.19", "1", "166", "126", "291", "1.4e-123", "395", "166");
//		FeatureAnnotation EC01 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.1", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
//		
//		String ECLine02 = createFileLine(geneID01, "Yes", "EC:2.8.1.2", "98.19", "1", "166", "126", "291", "1.4e-123", "395", "166");
//		FeatureAnnotation EC02 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.2", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
//		
//		
//		String KOLine01 = createFileLine(geneID01, "Yes", "KO:K02014", "94.10", "1", "150", "120", "255", "1.2e-15", "345", "158");
//		FeatureAnnotation KO01 = createFeatureAnnotation(FeatureAnnotationType.KO, "KO:K02014", Double.parseDouble("94.10"), Integer.parseInt("1"), Integer.parseInt("150"), Integer.parseInt("120"), Integer.parseInt("255"), Double.parseDouble("1.2e-15"), Double.parseDouble("345"), Integer.parseInt("158"));
//		
//		String COGLine01 = createFileLine(geneID01, "COG4832", "98.55", "201", "2", "202", "3" ,"206",  "3.7e-52", "179.3");
//		FeatureAnnotation COG01 = createFeatureAnnotation(FeatureAnnotationType.COG, "COG4832", Double.parseDouble("98.55"), Integer.parseInt("2"), Integer.parseInt("202"), Integer.parseInt("3"), Integer.parseInt("206"), Double.parseDouble("3.7e-52"), Double.parseDouble("3.7e-52"), Integer.parseInt("201"));
//		
//		String PFAMLine01 = createFileLine(geneID01, "pfam01161", "100", "2", "202", "3" ,"206",  "3.4e-55", "179.3", "201");
//		FeatureAnnotation PFAM01 = createFeatureAnnotation(FeatureAnnotationType.PFAM, "pfam01161", Double.parseDouble("100"), Integer.parseInt("2"), Integer.parseInt("202"), Integer.parseInt("3"), Integer.parseInt("206"), Double.parseDouble("3.4e-55"), Double.parseDouble("179.3"), Integer.parseInt("201"));
//		
//		
//	
//		File ECFile = tmpFolder.newFile("ec-file.tsv");
//		File KOFile = tmpFolder.newFile("ko-file.tsv");
//		File COGFile = tmpFolder.newFile("cog-file.tsv");
//		File PFAMFile = tmpFolder.newFile("pfam-file.tsv");
//		
//		String ECFilePath = ECFile.getAbsolutePath();
//		
//		
//		FileWriter ECFileWriter = new FileWriter(ECFile);
//		
//		ECFileWriter.write(ECLine01+BREAK_LINE);
//		ECFileWriter.write(ECLine02);
//		ECFileWriter.close();
//		
//		HashMap<String, List<FeatureAnnotation>> expectedHashMapFeatureAnnotatitionList = new HashMap<>();
//		List<FeatureAnnotation> featureAnnotationListGeneId01 = new ArrayList<>();
//		featureAnnotationListGeneId01.add(EC01);
//		featureAnnotationListGeneId01.add(EC02);
//		
//				
//		expectedHashMapFeatureAnnotatitionList.put(geneID01, featureAnnotationListGeneId01);
//		
//		IMGAnnotationFileManager target = new IMGAnnotationFileManager();
//		 HashMap<String,List<FeatureAnnotation>> featureAnnotationResult = target.getFeatureAnnotation(ECFilePath, FeatureAnnotationType.EC);
//		 
//		 Assert.assertEquals(expectedHashMapFeatureAnnotatitionList, featureAnnotationResult);
//		
//	}

	
	
	private FeatureAnnotation createFeatureAnnotation(FeatureAnnotationType fat, String name, double identity, int queryStart, int queryEnd, int subjectStart, int subjectEnd, double evalue, double bitScore, int alignLength) {
		return new FeatureAnnotation(fat, name, identity, alignLength, queryStart, queryEnd, subjectStart, subjectEnd, evalue, bitScore);
	}

	@Test
	public void testWhenFilePahtDoesNotExists() throws Exception {
		exException.expect(DomainException.class);
		exException.expectMessage("Invalid File Path");
		
		IMGAnnotationFileManager target = new IMGAnnotationFileManager();
		String filePath = "/tmp/doesNotExists.txt";
		target.getFeatureAnnotation(filePath , FeatureAnnotationType.COG);
	}
	
	private String createFileLine(String... fields) {
		StringBuilder stringBuilder = new StringBuilder();
		int numberOfFields = fields.length;
		for (int i = 0; i < numberOfFields; i++) {
			stringBuilder.append(fields[i]);
			if((i+1) < numberOfFields){
				stringBuilder.append("\t");
			}
		}
		return stringBuilder.toString();
	}

}
