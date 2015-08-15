package lbi.usp.br.caravela;

import java.io.IOException;

import lbi.usp.br.caravela.mytaxa.MytaxaConverterFile;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		
		if(args.length > 0){
			Integer app = new Integer(args[0]);
			
			switch (app) {
			case 1:
				MytaxaConverterFile.main(args);
				break;
			case 2:
				JsonCreator mappingReader = new JsonCreator(args[1]);
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
