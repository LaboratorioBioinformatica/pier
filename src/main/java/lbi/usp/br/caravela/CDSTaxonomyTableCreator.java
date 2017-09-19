package lbi.usp.br.caravela;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lbi.usp.br.caravela.mytaxa.MyTaxaFile;
import lbi.usp.br.caravela.mytaxa.MyTaxaLine;

/**
 * 
 * @author gianluca
 * 
 * Criando table com identificação taxonômica por cds dos dados de rna de zc4 
 * Identificador mytaxa
 * 
 *
 */
public class CDSTaxonomyTableCreator {
	
	public static void main(String[] args) throws IOException {
		
		
		HashMap<String, String> samplesHash = new HashMap<String, String>();
		samplesHash.put("ZC4-RNA-DAY-01", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-01-3300003896.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-03", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-03-3300003897.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-07", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-07-3300003898.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-15", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-15-3300003912.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-30", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-30-3300003900.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-64", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-64-3300003913.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-78", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-78-3300003899.a.faa-mytaxa-output.txt");
		samplesHash.put("ZC4-RNA-DAY-99", "/data/metazoo/mytaxa-cds-rna-zc4/ZC4-RNA-DAY-99-3300003901.a.faa-mytaxa-output.txt");
		
		
		ArrayList<String> samples = new ArrayList<String>();
		samples.add("ZC4-RNA-DAY-01");
		samples.add("ZC4-RNA-DAY-03");
		samples.add("ZC4-RNA-DAY-07");
		samples.add("ZC4-RNA-DAY-15");
		samples.add("ZC4-RNA-DAY-30");
		samples.add("ZC4-RNA-DAY-64");
		samples.add("ZC4-RNA-DAY-78");
		samples.add("ZC4-RNA-DAY-99");
		
		for (String sample : samples) {
			
			String mytaxaSampleFilePath = samplesHash.get(sample);
			List<MyTaxaLine> loadTaxonomyFileList = new MyTaxaFile().loadTaxonomyFileList(mytaxaSampleFilePath);
			
			
			File newFile = new File("/data/metazoo/mytaxa-cds-rna-zc4/".concat(sample).concat("-mytaxa-cds.tab"));
			newFile.createNewFile();
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (MyTaxaLine myTaxaLine : loadTaxonomyFileList) {
				String newLine =  new StringBuilder().append(myTaxaLine.getSequenceReference()).append("\t")
						.append(myTaxaLine.getTaxonomyRank()).append("\t")
						.append(myTaxaLine.getDeepestTaxonomy()).append("\t")
						.append(myTaxaLine.getLineage()).toString();
				bw.write(newLine);
				bw.newLine();
				
			}
			bw.close();
			fw.close();
			System.out.println(sample + " done!");
			
		}
		
		System.out.println("done all!");
		
		
		
		
//		List<HashMap<String, String>> samplesList = new ArrayList<>();
//		samplesList.add(samplesHash);
		
	}
	
	
	

}
