package lbi.iq.usp.br.mgbl.mgbl;

import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MytaxaConverterFile;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		if(args.length > 0){
			Integer app = new Integer(args[0]);
			
			switch (app) {
			case 1:
				MytaxaConverterFile.main(args);
				break;
			case 2:
				MappingReader mappingReader = new MappingReader(args[1], args[2]);
				break;	

			default:
				System.out.println("app: " +  app + " not found!");
				break;
			}
		} else {
			System.out.println("invalid command");
		}
		

	}
}
