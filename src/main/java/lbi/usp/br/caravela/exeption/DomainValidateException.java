package lbi.usp.br.caravela.exeption;

public class DomainValidateException extends DomainException {

	private static final long serialVersionUID = 5039259751020258929L;
	
	public DomainValidateException(String message) {
		super(message);
	}
	
	public DomainValidateException(String message, Throwable cause) {
		super(message, cause);
	}
	


}
