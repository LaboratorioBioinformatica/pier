package lbi.usp.br.caravela.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import lbi.usp.br.caravela.dto.Taxon;
import lbi.usp.br.caravela.exeption.DomainException;

public class TaxonFileManager {
	
	
	private static final int NO_TAXON = 0;

	private static final String TAB = "\t";

	private static final int NCBI_TAXONOMY_ID_IDX = 1;
	private static final int SEQ_REFERENCE_ID_IDX = 0;

	HashMap<String, Taxon> taxonHashMap = new HashMap<>();
	
	public TaxonFileManager() {}
	
	public TaxonFileManager(String filePath) {
		loadAndGetTaxonHashMap(filePath);
	}

	public  HashMap<String, Taxon> loadAndGetTaxonHashMap(String filePath) {
		try(Stream<String> stream = Files.lines(Paths.get(filePath))){
			stream.map(line -> Arrays.asList(line.split(TAB)))
			.forEach(taxonLine ->{
				Taxon taxon = createTaxonByLine(taxonLine);
				if(taxon != null){
					taxonHashMap.put(taxonLine.get(SEQ_REFERENCE_ID_IDX), taxon);
				}
			});
			
		} catch (IOException e) {
			throw new DomainException("Invalid Gene Taxon File Path", e);
		}
		
		return taxonHashMap;
		
	}
	
	public Taxon getTaxonBySequenceReferenfe(String sequenceReference){
		return taxonHashMap.get(sequenceReference);
	}
	
	private Taxon createTaxonByLine(List<String> taxonLine) {
		Taxon taxon = null;
		Integer ncbiTaxonomyId = Integer.valueOf(taxonLine.get(NCBI_TAXONOMY_ID_IDX));
		if(ncbiTaxonomyId > NO_TAXON){
			taxon = new Taxon.Builder().setTaxonomyId(ncbiTaxonomyId).build();
		}
		return taxon;
		
	}

}
