package eg.edu.guc.yugioh.exceptions;


public class MissingFieldException extends UnexpectedFormatException {
	private String message;
	public MissingFieldException(String sourceFile, int sourceLine) {
		super(sourceFile, sourceLine);
		this.message="";
	}
	public MissingFieldException(String sourceFile, int sourceLine,String m) {
		super(sourceFile, sourceLine,m);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
