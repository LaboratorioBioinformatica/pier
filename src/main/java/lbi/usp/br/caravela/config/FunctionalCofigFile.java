package lbi.usp.br.caravela.config;

import java.util.HashMap;

import lbi.usp.br.caravela.exeption.DomainValidateException;

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

	public HashMap<String, String> getFileList() {
		return files;
	}
	
	
	private void validateProviderName(FunctionProvider provider) {
		if(provider  == null){
			throw new DomainValidateException("Invalid function provider name");
		} 
	}
	
	private void validateFiles(HashMap<String, String> files) {
		if( ! FunctionProvider.NO.equals(provider)){
			if(files  == null != files.isEmpty()){
				throw new DomainValidateException("Invalid function file list");
			} 
		}
	}
	

}
