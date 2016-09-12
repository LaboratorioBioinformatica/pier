package lbi.usp.br.caravela.config;

import java.util.HashMap;

import lbi.usp.br.caravela.exeption.DomainValidateException;
import lbi.usp.br.caravela.img.IMGFileType;

public class FunctionalCofigFile {
	
	private FunctionProvider provider;
	private HashMap<String, String> files;
	
	public FunctionalCofigFile(FunctionProvider provider, HashMap<String, String> files) {
		setProviderName(provider);
		this.files = files;
	}
	
	
	public void validate(){
		validateProviderName(provider);
		validateFiles(files);
	}

	private void setProviderName(FunctionProvider provider){
		validateProviderName(provider);
		this.provider = provider;
	}

	public FunctionProvider getProvider(){
		return this.provider;
	}

	
	private String getDirectoryIMGFilePaht(){
		String value = IMGFileType.DIRECTORY_FILE_PATH_KEY.getValue();
		return files.get(value);
	}
	
	public String getFilePathByFileType(IMGFileType IMGFileType){
		String filePath = null;
		String fileName = files.get(IMGFileType.getValue());
		if(fileName != null &&  ! fileName.isEmpty()){
			filePath =  new StringBuilder().append(getDirectoryIMGFilePaht()).append(fileName).toString();
		}
		
		return filePath;
		
	}
	
	
	private void validateProviderName(FunctionProvider provider) {
		if(provider  == null) {
			FunctionProvider[] values = FunctionProvider.values();
			StringBuilder msgAppend = new StringBuilder().append("Invalid function provider name")
					.append(System.lineSeparator())
					.append("Valid providers are: ").append(System.lineSeparator());
			for (FunctionProvider functionProvider : values) {
				msgAppend.append(functionProvider.name()).append(System.lineSeparator());
				
			}
			throw new DomainValidateException(msgAppend.toString());
		} 
	}
	
	private void validateFiles(HashMap<String, String> files) {
		if( ! FunctionProvider.NO.equals(provider)){
			if(files  == null || files.isEmpty()){
				throw new DomainValidateException("Invalid function file list");
			} 
		}
	}
	
	
	

}
