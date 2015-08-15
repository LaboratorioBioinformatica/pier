package lbi.usp.br.caravela.img;

import java.util.List;

public class IMGFileManager {
	
	public static void main(String[] args) {
		
		GFFFileManager gffFileManager = new GFFFileManager("/data/mgb/annotation/ZC3b-day-01-3300002194.gff");
		
		GeneProductFileManager geneProductFileManager = new GeneProductFileManager("/data/mgb/annotation/ZC3b-day-01-3300002194-gene_product.txt");
		PhiloDistFileManager philoDistFileManager = new PhiloDistFileManager("/data/mgb/annotation/ZC3b-day-01-3300002194-phylodist.txt");
		
		List<GFFFeature> fetures = gffFileManager.getFeture("metazooDRAFT_1000771");
		
		
		for (GFFFeature gffFeature : fetures) {
			
			String geneId = gffFeature.getGeneId();
			String productName = null;
			String taxon = null;
			
			IMGGeneProduct geneProduct = geneProductFileManager.getGeneProductByGeneId(geneId);
			IMGPhiloDist philoDist = philoDistFileManager.getPhiloDistByGeneId(geneId);
			
			if(geneProduct != null){
				productName = geneProduct.getProductName();
			}
			if(philoDist != null){
				taxon = philoDist.getDeepestTaxon();
			}
			
			
			String result = new StringBuffer().append(gffFeature.getType())
			.append("\t")
			.append(gffFeature.getStart() + "-" + gffFeature.getEnd())
			.append("\t")
			.append(productName)
			.append("\t")
			.append(taxon)
			.toString();
			
			System.out.println(result);
			
			
			
		}
		
		
	}

}
