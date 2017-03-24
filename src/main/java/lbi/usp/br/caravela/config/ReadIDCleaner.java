package lbi.usp.br.caravela.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadIDCleaner {

	private static final String BREAK_LINE = "\\n";
	private static final String TAB = "\t";
	private static final String SLASH = "/";
	private static final int READ_ID = 0;
	private static final int TAXONOMY_ID = 1;
	

	public void removePairInformationOnReadId(String inputFilePath) {
		try {

			Scanner scanner = new Scanner(new FileReader(inputFilePath));
			scanner.useDelimiter(BREAK_LINE);
			while (scanner.hasNext()) {
				String next = scanner.next();
				String[] split = next.split(TAB);
				StringBuffer stringBuffer = new StringBuffer();
				String readIdUnclean = split[READ_ID];
				String taxonomyId = split[TAXONOMY_ID];
				String readId = removePairInformation(readIdUnclean);
				stringBuffer.append(readId).append(TAB).append(taxonomyId);
				System.out.println(stringBuffer.toString());
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			StringBuilder fileNotFoundMessageBuilder = new StringBuilder();

			if (inputFilePath.isEmpty()) {
				fileNotFoundMessageBuilder.append("Input file is Empty");

			} else {
				fileNotFoundMessageBuilder.append("File: ")
						.append(inputFilePath).append(" NOT FOUND!");
			}
			System.out.println(fileNotFoundMessageBuilder.toString());
		}

	}

	private String removePairInformation(String readIdUnclean) {
		String[] readIdAndPairInformation = readIdUnclean.split(SLASH);
		return readIdAndPairInformation[0];
	}

}
