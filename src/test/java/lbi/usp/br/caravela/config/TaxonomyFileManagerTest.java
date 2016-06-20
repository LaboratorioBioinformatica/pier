package lbi.usp.br.caravela.config;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TaxonomyFileManagerTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testWhenFilePathDoedNotExists() throws Exception {
		expectedEx.expect(FileNotFoundException.class);
		expectedEx.expectMessage("taxonomy file is required.");
		TaxonomyFileConfig taxonomyFileConfig = new TaxonomyFileConfig(TaxonomyProvider.DEFAULT, "/tmp/no-file");
		TaxonomyFileManager target = new TaxonomyFileManager();
		target.getTaxonomyHashMap(taxonomyFileConfig);
	}
	
	@Test
	public void test() throws Exception {
		TaxonomyFileConfig taxonomyFileConfig = new TaxonomyFileConfig(TaxonomyProvider.DEFAULT, "files/taxonomy/sample-01-taxonomy.tsv");
		
		TaxonomyFileManager target = new TaxonomyFileManager();
		HashMap<String,Integer> expectedTaxonomyHashMap = createExpectedTaxonomyHashMap();
		HashMap<String,Integer> taxonomyHashMap = target.getTaxonomyHashMap(taxonomyFileConfig);
		
		
		Assert.assertEquals(expectedTaxonomyHashMap, taxonomyHashMap);
	}

	private HashMap<String, Integer> createExpectedTaxonomyHashMap() {
		HashMap<String,Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("M01677:6:000000000-A41BV:1:1101:12003:18311", 203691);
		hashMap.put("M01677:6:000000000-A41BV:1:1104:10695:21203", 511746);
		hashMap.put("M01677:6:000000000-A41BV:1:1106:20572:10660", 743722);
		hashMap.put("M01677:6:000000000-A41BV:1:1109:24465:11683", 976);
		hashMap.put("M01677:6:000000000-A41BV:1:1113:5694:13940", 831);
		hashMap.put("M01677:6:000000000-A41BV:1:2102:16109:26536", 817);
		hashMap.put("M01677:6:000000000-A41BV:1:2103:12603:24313", 817);
		hashMap.put("M01677:6:000000000-A41BV:1:2107:8033:10203", 28118);
		hashMap.put("M01677:6:000000000-A41BV:1:2108:23148:26388", 853);
		hashMap.put("M01677:6:000000000-A41BV:1:2109:13046:20137", 751585);
		hashMap.put("M01677:6:000000000-A41BV:1:2111:12097:7718", 1363);
		hashMap.put("M01677:6:000000000-A41BV:1:2111:5846:16144", 312279);
		hashMap.put("M01677:6:000000000-A41BV:1:2114:16295:17407", 853);
		return hashMap;
	}
	

}
