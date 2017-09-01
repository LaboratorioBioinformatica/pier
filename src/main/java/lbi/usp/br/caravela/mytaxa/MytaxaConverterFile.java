package lbi.usp.br.caravela.mytaxa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class MytaxaConverterFile {

	private static final int SEQUENCE_ID_POSITION = 0;
	private static final String SPACE = " ";

	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		System.exit(0);

		HashSet<String> files = new HashSet<String>();

		files.add("ZC3DAY78IQMSIPER01R1-mytaxa-output.txt");
		files.add("ZC3DAY30IQMSIPER01R1-mytaxa-output.txt");
		files.add("ZC3DAY99IQMSIPER01R1-mytaxa-output.txt");
		files.add("ZC3DAY64IQMSIPER01R1-mytaxa-output.txt");
		files.add("ZC3DAY01IQMSIPER01R1-mytaxa-output.txt");

		String filePath = "/home/gianluca/git/mgwbrowser/files/mytaxa/";

		// String fileName = "ZC3DAY01IQ454TIR00R0-mytaxa.output";

		// convert(filePath, fileName);

		for (String fileName : files) {
			convert(filePath, fileName);
		}

	}

	private static void convert(String filePath, String fileName) {
		File newFile = new File(filePath.concat(fileName).replace(".txt", ".mgb"));

		try {

			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);

			Scanner scanner = new Scanner(new FileReader(filePath.concat(fileName)));

			scanner.useDelimiter("\\n|\\t");
			while (scanner.hasNext()) {

				String sequenceId = scanner.next();

				String biologicalHierarchyType = scanner.next().concat(SPACE).concat(scanner.next());
				String taxonomyId = scanner.next();
				String biologicalHierarchy = scanner.next();

				taxonomyId = setToNoClassifiedWhenInformationFromFileIsNA(taxonomyId);

				if (taxonomyId.isEmpty() || taxonomyId.equals("0")) {
					continue;
				}

				String line = sequenceId.concat("\t".concat(taxonomyId));
				bw.write(line);
				bw.newLine();

			}
			scanner.close();
			bw.close();
			fw.close();

		} catch (IOException IOEx) {
			IOEx.printStackTrace();
		}

		System.out.println("Done!");
	}

	private static String setToNoClassifiedWhenInformationFromFileIsNA(String taxonomyId) {
		if (taxonomyId.equals("NA")) {
			taxonomyId = "0";
		}
		return taxonomyId;
	}
}