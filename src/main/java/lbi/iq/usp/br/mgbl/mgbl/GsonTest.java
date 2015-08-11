package lbi.iq.usp.br.mgbl.mgbl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GsonTest {
	
	public static void main(String[] args) {
		
//		List<Contig> listContig = new ArrayList<Contig>();
		List<ReadOnContig> readsOnContig = new ArrayList<ReadOnContig>();
		
		
		ReadOnContig readOnContig01 = new ReadOnContig("referenceid12331230", "CCCAAATTGTGTAGTGTACTACTA", 192, "first", 1231515, null, null);
		ReadOnContig readOnContig02 = new ReadOnContig("asdfasdfasdf", "CCCAAATTGTGTAGTGTACTACTA", 192, "first", null, null, null);
		ReadOnContig readOnContig03 = new ReadOnContig("121561asd51f6as1d561asdf", "CCCAAATTGTGTAGTGTACTACTA", 192, "first", 123545, "bixo x", "spice");
		readsOnContig.add(readOnContig01);
		readsOnContig.add(readOnContig02);
		readsOnContig.add(readOnContig03);
		
		Gson g = new Gson();
		
		Contig contig = new Contig("referenceidsadf15asd4f5as46df465asd46f", "AAHHHADHIUAHIUDAIGDGYDGSIGYSGDYIDIGSDUYGSYDGSYDGSGDUYSIGDYUSGDIUYSGDUDGYUDS", readsOnContig);
//		listContig.add(contig);
		
		String jsonString = g.toJson(contig);
		
		System.out.println(jsonString);
	}

}
