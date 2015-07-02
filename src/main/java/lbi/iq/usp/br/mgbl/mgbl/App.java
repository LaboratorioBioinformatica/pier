package lbi.iq.usp.br.mgbl.mgbl;

import lbi.iq.usp.br.mgbl.mgbl.mytaxa.MytaxaConverterFile;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		if(args.length > 0){
			String app = args[0];
			
			switch (app) {
			case "mytaxa":
				MytaxaConverterFile.main(args);
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
