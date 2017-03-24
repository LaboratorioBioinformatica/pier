package lbi.usp.br.caravela.clark;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ClackFile {
	
	private static final String COMMA_SEPARATOR = ",";
	private static final String SPACE = " ";
	private static final String NA = "NA";
	private static final String TAB = "\t";
	
	private static final Integer READ_ID=0;
	private static final Integer LENGTH=1;
	private static final Integer GAMMA=2;
	private static final Integer TAXONOMY_ID_01=3;
	private static final Integer TAXONOMY_ID_SCORE_01=4;
	private static final Integer TAXONOMY_ID_02=5;
	private static final Integer TAXONOMY_ID_SCORE_02=6;
	private static final Integer CONFIDENCE=7;
	
	
	public void toPierFile(String inputClarkInputFile){
		try {
			
			Scanner scanner = new Scanner(new FileReader(inputClarkInputFile ));
			String headLine = scanner.next();
			
			while(scanner.hasNext()) {
				String next = scanner.next();
				String[] split = next.split(COMMA_SEPARATOR);
				
				StringBuilder stringBuilder = new StringBuilder();
				
				String readId = split[READ_ID];
				String taxonomyId01 = split[TAXONOMY_ID_01];
				String taxonomyId02 = split[TAXONOMY_ID_02];
				
				if( ! NA.equals(taxonomyId01)){
					stringBuilder.append(readId).append(TAB).append(taxonomyId01);
					
				} else if(! NA.equals(taxonomyId02)){
					stringBuilder.append(readId).append(TAB).append(taxonomyId02);
				}
					
				String line = stringBuilder.toString();
				if( ! line.isEmpty()){
					System.out.println(line);
				}
			}
			
			scanner.close();

			
		} catch (FileNotFoundException e) {
			StringBuilder fileNotFoundMessageBuilder = new StringBuilder();
			
			if(inputClarkInputFile.isEmpty()){
				fileNotFoundMessageBuilder.append("Input file is Empty");
				
			} else {
				fileNotFoundMessageBuilder.append("File: ").append(inputClarkInputFile).append(" NOT FOUND!");
			}
			
			System.out.println(fileNotFoundMessageBuilder.toString());
		} 		
	}
}
