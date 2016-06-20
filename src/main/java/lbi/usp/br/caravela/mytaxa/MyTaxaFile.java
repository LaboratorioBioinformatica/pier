package lbi.usp.br.caravela.mytaxa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import lbi.usp.br.caravela.dto.Taxon;

public class MyTaxaFile {

	private static final String BREAK_LINE_OR_TAB = "\\n|\\t";
	private static final String SPACE = " ";
	
	public MyTaxaFile() {}
	
	public Hashtable<String, MyTaxaLine> loadTaxonomyFile(String fullFilePath) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(new FileReader(fullFilePath));
		Hashtable<String, MyTaxaLine>  hashTableMytaxaLine = new Hashtable<String, MyTaxaLine>();
		
		scanner.useDelimiter(BREAK_LINE_OR_TAB);
		while (scanner.hasNext()) {
			String sequenceReference = scanner.next();
			String biologicalHierarchyType = scanner.next(); 
			String score = scanner.next();
			String taxonomyId = scanner.next();
			String biologicalHierarchy = scanner.next();
			hashTableMytaxaLine.put(sequenceReference.split(SPACE)[0], new MyTaxaLine(sequenceReference, biologicalHierarchyType, taxonomyId, score, biologicalHierarchy));
		
		}
		
		scanner.close();
		return hashTableMytaxaLine;
	}
	
	public Hashtable<String, List<MyTaxaLine>> loadTaxonomyMultiHashTableFromFile(String fullFilePath) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(new FileReader(fullFilePath));
		Hashtable<String, List<MyTaxaLine>>  multiHashTableMytaxaLine = new Hashtable<String, List<MyTaxaLine>>();
		 
		scanner.useDelimiter(BREAK_LINE_OR_TAB);
		
		while (scanner.hasNext()) {
			String sequenceReference = scanner.next();
			String sequenceReferenceWithoutPairInformation = sequenceReference.split(SPACE)[0];
			String biologicalHierarchyType = scanner.next(); 
			String score = scanner.next();
			String taxonomyId = scanner.next();
			String biologicalHierarchy = scanner.next();
			
			MyTaxaLine myTaxaLine = new MyTaxaLine(sequenceReference, biologicalHierarchyType, taxonomyId, score, biologicalHierarchy);
			
			if( myTaxaLine.hasTaxonomy()){
				
				List<MyTaxaLine> mytaxaLineList = multiHashTableMytaxaLine.get(sequenceReferenceWithoutPairInformation);
				
				if(mytaxaLineList == null){
					List<MyTaxaLine> hits = new ArrayList<MyTaxaLine>();
					hits.add(myTaxaLine);
					multiHashTableMytaxaLine.put(sequenceReferenceWithoutPairInformation, hits);
				} else {
					mytaxaLineList.add(myTaxaLine);
				}
				
			}
		}
		
		scanner.close();
		return multiHashTableMytaxaLine;
	}
	
	public List<MyTaxaLine> loadTaxonomyFileList(String fullFilePath) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(new FileReader(fullFilePath));
		ArrayList<MyTaxaLine> list = new ArrayList<MyTaxaLine>();
		
		scanner.useDelimiter(BREAK_LINE_OR_TAB);
		while (scanner.hasNext()) {
			String sequenceReference = scanner.next();
			String biologicalHierarchyType = scanner.next(); 
			String score = scanner.next();
			String taxonomyId = scanner.next();
			String biologicalHierarchy = scanner.next();
			list.add(new MyTaxaLine(sequenceReference, biologicalHierarchyType, taxonomyId, score, biologicalHierarchy));
		}
		scanner.close();
		return list;
	}
	
	public void mytaxaFileToPierDefaultFile(String myTaxaFilePath, String outputFilePath) throws IOException {
		
		Hashtable<String, List<MyTaxaLine>> loadTaxonomyMultiHashTableFromFile = new MyTaxaFile().loadTaxonomyMultiHashTableFromFile(myTaxaFilePath);
		
		Set<String> keySet = loadTaxonomyMultiHashTableFromFile.keySet();
		
		File newFile = new File(outputFilePath);
		newFile.createNewFile();
		
		FileWriter fw = new FileWriter(newFile, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (String sequenceReference : keySet) {
			
			List<MyTaxaLine> myTaxaLineList = loadTaxonomyMultiHashTableFromFile.get(sequenceReference);
			
			Taxon taxon = findTaxonWithGreaterScoreByReadReference(myTaxaLineList);
			
			String newLine = null;
			newLine = sequenceReference + "\t" + taxon.getTaxonomyId();
			
			bw.write(newLine);
			bw.newLine();
		}
		
		bw.close();
		fw.close();
	}
	
	private Taxon findTaxonWithGreaterScoreByReadReference(List<MyTaxaLine> mytaxaList) {
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

}
