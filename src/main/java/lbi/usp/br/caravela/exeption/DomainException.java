package lbi.usp.br.caravela.exeption;

public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 2334703686128174123L;
	
	public DomainException(String message) {
		super(message);
	}
	
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
