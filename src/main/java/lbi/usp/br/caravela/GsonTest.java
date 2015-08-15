package lbi.usp.br.caravela;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import lbi.usp.br.caravela.dto.ReadOnContig;
import lbi.usp.br.caravela.dto.Taxon;

public class GsonTest {
	
	public static void main(String[] args) {
		
//		List<Contig> listContig = new ArrayList<Contig>();
		List<ReadOnContig> readsOnContig = new ArrayList<ReadOnContig>();
		
		
		Taxon taxon = new Taxon.Builder().setTaxonomyId(12345).setScientificName("bixo x").setHank("spicie").setScore(1d).build();
		List<Taxon> taxons = new ArrayList<Taxon>();
		taxons.add(taxon);
		List<Taxon> emptyTaxons = new ArrayList<Taxon>();
		
		
		ReadOnContig readOnContig01 = new ReadOnContig("referenceid12331230", "CCCAAATTGTGTAGTGTACTACTA", 1, 10, 192, "first", taxons);
		ReadOnContig readOnContig02 = new ReadOnContig("asdfasdfasdf", "CCCAAATTGTGTAGTGTACTACTA", 1, 10, 192, "first", taxons);
		ReadOnContig readOnContig03 = new ReadOnContig("121561asd51f6as1d561asdf", "CCCAAATTGTGTAGTGTACTACTA", 1, 10, 192, "first", emptyTaxons);
		readsOnContig.add(readOnContig01);
		readsOnContig.add(readOnContig02);
		readsOnContig.add(readOnContig03);
		
		Gson g = new Gson();
		
//		Contig contig = new Contig("referenceidsadf15asd4f5as46df465asd46f", "AAHHHADHIUAHIUDAIGDGYDGSIGYSGDYIDIGSDUYGSYDGSYDGSGDUYSIGDYUSGDIUYSGDUDGYUDS", readsOnContig);
//		listContig.add(contig);
		
//		String jsonString = g.toJson(contig);
		
//		System.out.println(jsonString);
				
	}

}
