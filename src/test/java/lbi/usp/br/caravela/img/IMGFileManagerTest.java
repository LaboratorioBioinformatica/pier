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
import org.mockito.Mockito;

import lbi.usp.br.caravela.config.FunctionProvider;
import lbi.usp.br.caravela.config.FunctionalCofigFile;
import lbi.usp.br.caravela.dto.Feature;
import lbi.usp.br.caravela.dto.FeatureAnnotation;
import lbi.usp.br.caravela.dto.FeatureAnnotationType;
import lbi.usp.br.caravela.dto.GeneProduct;
import lbi.usp.br.caravela.dto.PhiloDist;
import lbi.usp.br.caravela.exeption.DomainValidateException;


public class IMGFileManagerTest {
	
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final String TAB = "\t";
	private static final String SEMICOLON = ";";
	private static final String EQUALS = "=";
	
	private static final String CONTIG_ID_01 = "metazooDRAFT_1000035";
	private static final String CONTIG_ID_02 = "metazooDRAFT_1000034";
	private static final String ATTRIBUTE_NAME_ID = "ID";
	private static final String ATTRIBUTE_VALUE_ID = "metazooDRAFT_1000035.1";
	
	private static final String ATTRIBUTE_NAME_LOCUS_TAG = "locus_tag";
	private static final String ATTRIBUTE_VALUE_LOCUS_TAG_01 = "metazooDRAFT_10000351";
	private static final String ATTRIBUTE_VALUE_LOCUS_TAG_02 = "metazooDRAFT_10000354";
	private static final String GENE_ID_01 = ATTRIBUTE_VALUE_LOCUS_TAG_01;
	
	private static final String ATTRIBUTES_01 = new StringBuilder(ATTRIBUTE_NAME_ID).append(EQUALS).append(ATTRIBUTE_VALUE_ID).append(SEMICOLON).append(ATTRIBUTE_NAME_LOCUS_TAG).append(EQUALS).append(ATTRIBUTE_VALUE_LOCUS_TAG_01).append(SEMICOLON).toString(); 
	private static final String ATTRIBUTES_02 = new StringBuilder(ATTRIBUTE_NAME_ID).append(EQUALS).append(ATTRIBUTE_VALUE_ID).append(SEMICOLON).append(ATTRIBUTE_NAME_LOCUS_TAG).append(EQUALS).append(ATTRIBUTE_VALUE_LOCUS_TAG_02).append(SEMICOLON).toString();
	
	private static final String GFF_FILE_LINE_01 = new StringBuilder(CONTIG_ID_01).append(TAB).append("FGMP").append(TAB).append("CDS").append(TAB).append(1).append(TAB).append(300).append(TAB).append(".").append(TAB).append(1).append(TAB).append("0").append(TAB).append(ATTRIBUTES_01).append(TAB).toString();
	private static final String GFF_FILE_LINE_02 = new StringBuilder(CONTIG_ID_01).append(TAB).append("FGMP").append(TAB).append("CDS").append(TAB).append(1).append(TAB).append(300).append(TAB).append(".").append(TAB).append(1).append(TAB).append("0").append(TAB).append(ATTRIBUTES_02).append(TAB).toString();
	private static final String GFF_FILE_LINE_03 = new StringBuilder(CONTIG_ID_02).append(TAB).append("FGMP").append(TAB).append("CDS").append(TAB).append(10).append(TAB).append(310).append(TAB).append(".").append(TAB).append(1).append(TAB).append("0").append(TAB).append(ATTRIBUTES_01).append(TAB).toString();
	private static final String GENE_SEQUENCE = "MSTPGEFLILRRGEPFLFGNALLGATWIHPLLFERPIERIGSDFELYWNG";
	private static final String FASTA_MARKER = ">";
	
	private static final String PRODUCT_NAME = "ABC-type cobalt transport system, ATPase component";
	private static final String SOURCE = "COG1122";
	
	private static final String IMG_GENE_PRODUCT_LINE = new StringBuilder(GENE_ID_01).append(TAB).append(PRODUCT_NAME).append(TAB).append(SOURCE).toString();
	
	
	private static final String HOMOLOG_GENE_OID = "2523866490";
	private static final String HOMOLOG_TAXON_OID = "2523533587";
	private static final String PERCENT_IDENTITY = "32.2";
	private static final String LINEAGE = "Bacteria;Bacteroidetes;Bacteroidia;Bacteroidales;Bacteroidaceae;Bacteroides;finegoldii;Bacteroides finegoldii DSM 17565";
	
	private static final String IMG_PHILO_DIST_LINE = new StringBuilder(GENE_ID_01).append(TAB).append(HOMOLOG_GENE_OID).append(TAB).append(HOMOLOG_TAXON_OID).append(TAB).append(PERCENT_IDENTITY).append(TAB).append(LINEAGE).toString();

	
	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	
	@Test
	public void testWhenGFFFilePathDoNotExistShouldBeThrowException() throws Exception {
		
		expectedEx.expect(DomainValidateException.class);
		expectedEx.expectMessage("Invalid gff file path");

		HashMap<String, String> files = new HashMap<>();
		files.put("gff", "/tmp/donotexist.gff");
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile(FunctionProvider.IMG_M, files);

		IMGFileManager IMGFileManager = new IMGFileManager();
		IMGFileManager.getFeatureHashMap(functionalCofigFile);
		
	}

	
	@Test
	public void testName() throws Exception {
	
		File geneSequecenFileFasta = tmpFolder.newFile("geneSequecenFileFasta.fa");
		String breakLine = System.lineSeparator();
		
		FileWriter fileWriter = new FileWriter(geneSequecenFileFasta);
		fileWriter.write(FASTA_MARKER+ATTRIBUTE_VALUE_LOCUS_TAG_01+breakLine);
		fileWriter.write(GENE_SEQUENCE);
		fileWriter.close();
		
		String directory = geneSequecenFileFasta.getParent() + "/";
		
		
		HashMap<String, String> files = new HashMap<>();
		String gffFilePath =  directory + "fileExist.gff";
		String geneProductFilePath = directory + "imgGeneProductFileExist.tsv";
		String philoDistFilePath = directory +  "imgPhiloDistFileExist.tsv";
		String COGFilePath = directory +  "IMGCOGFileExist.tsv";
		String KOFilePath =  directory +  "IMGKOFileExist.tsv";
		String PFAMFilePath = directory +  "IMGPFAMFileExist.tsv";
		String ECFilePath = directory + "IMGECFileExist.tsv";
		
		
		
		String geneSequenceFilaName = geneSequecenFileFasta.getName();
		String gffFileName = "fileExist.gff";
		String geneProductFileName = "imgGeneProductFileExist.tsv";
		String philoDistFileName = "imgPhiloDistFileExist.tsv";
		String COGFileName = "IMGCOGFileExist.tsv";
		String KOFileName = "IMGKOFileExist.tsv";
		String PFAMFileName = "IMGPFAMFileExist.tsv";
		String ECFileName = "IMGECFileExist.tsv";
		
		
		files.put("directory", directory);
		
		files.put("gff", gffFileName);
		files.put("geneSequence", geneSequenceFilaName);
		files.put("geneProduct", geneProductFileName);
		files.put("phylodist", philoDistFileName);
		files.put("cog", COGFileName);
		files.put("ec", ECFileName);
		files.put("ko", KOFileName);
		files.put("pfam", PFAMFileName);
		
		
		FunctionalCofigFile functionalCofigFile = new FunctionalCofigFile(FunctionProvider.IMG_M, files);
		
		HashMap<String, List<GFFFeature>> hashMapGFFFeature = createHashMapGFFFeature();
		HashMap<String, IMGGeneProduct> hashMapIMGGeneProduct = createHashMapIMGGeneProduct();
		HashMap<String, IMGPhiloDist> hashMapIMGPhiloDist = createHashMapIMGPhiloDist();
		
		
		FeatureAnnotation EC01 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.1", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
		FeatureAnnotation EC02 = createFeatureAnnotation(FeatureAnnotationType.EC, "EC:2.8.1.2", Double.parseDouble("98.19"), Integer.parseInt("1"), Integer.parseInt("166"), Integer.parseInt("126"), Integer.parseInt("291"), Double.parseDouble("1.4e-123"), Double.parseDouble("395"), Integer.parseInt("166"));
		List<FeatureAnnotation> ECFeatureAnnotationList = new ArrayList<>();
		ECFeatureAnnotationList.add(EC01);
		ECFeatureAnnotationList.add(EC02);
		HashMap<String, List<FeatureAnnotation>> ECHashMapFeatureAnnotationList = new HashMap<>();
		ECHashMapFeatureAnnotationList.put(GENE_ID_01, ECFeatureAnnotationList);
		
		FeatureAnnotation KO01 = createFeatureAnnotation(FeatureAnnotationType.KO, "KO:K02014", Double.parseDouble("94.10"), Integer.parseInt("1"), Integer.parseInt("150"), Integer.parseInt("120"), Integer.parseInt("255"), Double.parseDouble("1.2e-15"), Double.parseDouble("345"), Integer.parseInt("158"));
		List<FeatureAnnotation> KOFeatureAnnotationList = new ArrayList<>();
		KOFeatureAnnotationList.add(KO01);
		HashMap<String, List<FeatureAnnotation>> KOHashMapFeatureAnnotationList = new HashMap<>();
		KOHashMapFeatureAnnotationList.put(GENE_ID_01, KOFeatureAnnotationList);
		
		FeatureAnnotation COG01 = createFeatureAnnotation(FeatureAnnotationType.COG, "COG4832", Double.parseDouble("98.55"), Integer.parseInt("2"), Integer.parseInt("202"), Integer.parseInt("3"), Integer.parseInt("206"), Double.parseDouble("3.7e-52"), Double.parseDouble("3.7e-52"), Integer.parseInt("201"));
		List<FeatureAnnotation> COGFeatureAnnotationList = new ArrayList<>();
		COGFeatureAnnotationList.add(COG01);
		HashMap<String, List<FeatureAnnotation>> COGHashMapFeatureAnnotationList = new HashMap<>();
		COGHashMapFeatureAnnotationList.put(GENE_ID_01, COGFeatureAnnotationList);
		
		FeatureAnnotation PFAM01 = createFeatureAnnotation(FeatureAnnotationType.PFAM, "pfam01161", Double.parseDouble("100"), Integer.parseInt("2"), Integer.parseInt("202"), Integer.parseInt("3"), Integer.parseInt("206"), Double.parseDouble("3.4e-55"), Double.parseDouble("179.3"), Integer.parseInt("201"));
		List<FeatureAnnotation> PFAMFeatureAnnotationList = new ArrayList<>();
		PFAMFeatureAnnotationList.add(PFAM01);
		HashMap<String, List<FeatureAnnotation>> PFAMHashMapFeatureAnnotationList = new HashMap<>();
		PFAMHashMapFeatureAnnotationList.put(GENE_ID_01, PFAMFeatureAnnotationList);
		
		List<FeatureAnnotation> expectedAllFeatureAnnotation = new ArrayList<>();
		expectedAllFeatureAnnotation.addAll(KOFeatureAnnotationList);
		expectedAllFeatureAnnotation.addAll(COGFeatureAnnotationList);
		expectedAllFeatureAnnotation.addAll(ECFeatureAnnotationList);
		expectedAllFeatureAnnotation.addAll(PFAMFeatureAnnotationList);
		
		IMGAnnotationFileManager mockAnnotationFileManager = Mockito.mock(IMGAnnotationFileManager.class);
		Mockito.when(mockAnnotationFileManager.getFeatureAnnotation(COGFilePath, FeatureAnnotationType.COG)).thenReturn(COGHashMapFeatureAnnotationList);
		Mockito.when(mockAnnotationFileManager.getFeatureAnnotation(ECFilePath, FeatureAnnotationType.EC)).thenReturn(ECHashMapFeatureAnnotationList);
		Mockito.when(mockAnnotationFileManager.getFeatureAnnotation(KOFilePath, FeatureAnnotationType.KO)).thenReturn(KOHashMapFeatureAnnotationList);
		Mockito.when(mockAnnotationFileManager.getFeatureAnnotation(PFAMFilePath, FeatureAnnotationType.PFAM)).thenReturn(PFAMHashMapFeatureAnnotationList);
		
		
		
		GFFFileManager mockGFFFileManager = createMockGFFFileManager(gffFilePath, hashMapGFFFeature);
		GeneProductFileManager mockGeneProductFileManager = createMockGeneProductFileManager(geneProductFilePath, hashMapIMGGeneProduct);
		PhiloDistFileManager mockPhiloDistFileManager = createMockPhiloDistFileManager(philoDistFilePath, hashMapIMGPhiloDist);
		
		IMGFileManager target = Mockito.spy(new IMGFileManager());
		Mockito.when(target.getGFFFileManager()).thenReturn(mockGFFFileManager);
		Mockito.when(target.getGeneProductFileManager()).thenReturn(mockGeneProductFileManager);
		Mockito.when(target.getPhiloDistFileManager()).thenReturn(mockPhiloDistFileManager);
		Mockito.when(target.getIMGAnnotationFileManager()).thenReturn(mockAnnotationFileManager);
		
		HashMap<String, List<Feature>> featureHashMap = target.getFeatureHashMap(functionalCofigFile);
	
		GeneProduct expectedGeneProduct = new GeneProduct(PRODUCT_NAME, SOURCE);
		PhiloDist expectedPhiloDist = new PhiloDist(new Long(HOMOLOG_GENE_OID), new Long(HOMOLOG_TAXON_OID), new Double(PERCENT_IDENTITY), LINEAGE);
		
		List<Feature> featureListFromContig01 = featureHashMap.get(CONTIG_ID_01);
		List<Feature> featureListFromContig02 = featureHashMap.get(CONTIG_ID_02);
	
		Assert.assertEquals(2, featureListFromContig01.size());
		Assert.assertEquals(1, featureListFromContig02.size());
		
		Feature firstFeatureFromContig01 = featureListFromContig01.get(FIRST);
		Feature secondFeatureFromContig01 = featureListFromContig01.get(SECOND);
		Feature firstFeatureFromContig02 = featureListFromContig02.get(FIRST);
		
		
		
		Assert.assertEquals("CDS", firstFeatureFromContig02.getType());
		Assert.assertEquals(new Integer(10), firstFeatureFromContig02.getStart());
		Assert.assertEquals(new Integer(310), firstFeatureFromContig02.getEnd());
		Assert.assertEquals(GENE_SEQUENCE, firstFeatureFromContig02.getSequence());
		
		
		Assert.assertEquals(expectedGeneProduct, firstFeatureFromContig02.getGeneProduct());
		Assert.assertEquals(expectedPhiloDist, firstFeatureFromContig02.getPhiloDist());
		
		List<FeatureAnnotation> featureAnnotationFromFirstContig1 = firstFeatureFromContig01.getAnnotations();
		List<FeatureAnnotation> featureAnnotationFromSecondContig1 = secondFeatureFromContig01.getAnnotations();
		List<FeatureAnnotation> featureAnnotationFirstFromContig2 = firstFeatureFromContig02.getAnnotations();
		
		Assert.assertEquals(expectedAllFeatureAnnotation, featureAnnotationFromFirstContig1);
		Assert.assertEquals(expectedAllFeatureAnnotation, featureAnnotationFirstFromContig2);
		Assert.assertNull(featureAnnotationFromSecondContig1);
		
		
	
		
		Mockito.verify(target).getGFFFileManager();
		Mockito.verify(target).getGeneProductFileManager();
		Mockito.verify(target).getPhiloDistFileManager();
		Mockito.verify(target).getIMGAnnotationFileManager();
		
		Mockito.verify(mockGeneProductFileManager).load(geneProductFilePath);
		Mockito.verify(mockPhiloDistFileManager).load(philoDistFilePath);
		Mockito.verify(mockAnnotationFileManager).getFeatureAnnotation(COGFilePath, FeatureAnnotationType.COG);
		Mockito.verify(mockAnnotationFileManager).getFeatureAnnotation(ECFilePath, FeatureAnnotationType.EC);
		Mockito.verify(mockAnnotationFileManager).getFeatureAnnotation(KOFilePath, FeatureAnnotationType.KO);
		Mockito.verify(mockAnnotationFileManager).getFeatureAnnotation(PFAMFilePath, FeatureAnnotationType.PFAM);
		
		
		
	}

	private FeatureAnnotation createFeatureAnnotation(FeatureAnnotationType fat, String name, double identity, int queryStart, int queryEnd, int subjectStart, int subjectEnd, double evalue, double bitScore, int alignLength) {
		return new FeatureAnnotation(fat, name, identity, alignLength, queryStart, queryEnd, subjectStart, subjectEnd, evalue, bitScore);
	}


	private PhiloDistFileManager createMockPhiloDistFileManager(String philoDistFilePath, HashMap<String, IMGPhiloDist> hashMapIMGPhiloDist) {
		PhiloDistFileManager mock = Mockito.mock(PhiloDistFileManager.class);
		Mockito.when(mock.load(philoDistFilePath)).thenReturn(hashMapIMGPhiloDist);
		return mock;
	}


	private GeneProductFileManager createMockGeneProductFileManager(String geneProductFilePath, HashMap<String, IMGGeneProduct> hashMapIMGGeneProduct) {
		GeneProductFileManager mock = Mockito.mock(GeneProductFileManager.class);
		Mockito.when(mock.load(geneProductFilePath)).thenReturn(hashMapIMGGeneProduct);
		return mock;
	}


	private HashMap<String, IMGGeneProduct> createHashMapIMGGeneProduct() {
		HashMap<String, IMGGeneProduct> hashMap = new HashMap<String, IMGGeneProduct>();
		hashMap.put(ATTRIBUTE_VALUE_LOCUS_TAG_01, new IMGGeneProduct(IMG_GENE_PRODUCT_LINE));
		return hashMap;
	}
	
	private HashMap<String, IMGPhiloDist> createHashMapIMGPhiloDist() {
		HashMap<String, IMGPhiloDist> hashMap = new HashMap<String, IMGPhiloDist>();
		hashMap.put(ATTRIBUTE_VALUE_LOCUS_TAG_01, new IMGPhiloDist(IMG_PHILO_DIST_LINE));
		return hashMap;
	}


	private HashMap<String, List<GFFFeature>> createHashMapGFFFeature() {
		GFFFeature gffFeature01 = new GFFFeature(GFF_FILE_LINE_01);
		GFFFeature gffFeature02 = new GFFFeature(GFF_FILE_LINE_02);
		GFFFeature gffFeature03 = new GFFFeature(GFF_FILE_LINE_03);
		ArrayList<GFFFeature> gffFeatureListWithTwoGenes = new ArrayList<GFFFeature>();
		
		gffFeatureListWithTwoGenes.add(gffFeature01);
		gffFeatureListWithTwoGenes.add(gffFeature02);
		
		ArrayList<GFFFeature> gffFeatureListWithOneGene = new ArrayList<GFFFeature>();
		gffFeatureListWithOneGene.add(gffFeature03);
		
		HashMap<String, List<GFFFeature>> hashMap = new HashMap<String, List<GFFFeature>>();
		
		hashMap.put(CONTIG_ID_01, gffFeatureListWithTwoGenes);
		hashMap.put(CONTIG_ID_02, gffFeatureListWithOneGene);
		
		return hashMap;
	}


	private GFFFileManager createMockGFFFileManager(String gffFilePath, HashMap<String, List<GFFFeature>> hashMapGFFFeature) {
		GFFFileManager mock = Mockito.mock(GFFFileManager.class);
		Mockito.when(mock.load(gffFilePath)).thenReturn(hashMapGFFFeature);
		return mock;
	}
	

}
