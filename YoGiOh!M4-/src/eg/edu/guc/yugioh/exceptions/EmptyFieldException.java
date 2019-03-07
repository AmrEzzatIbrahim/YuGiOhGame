package eg.edu.guc.yugioh.exceptions;

public class EmptyFieldException extends UnexpectedFormatException {
	private String message;
	private int sourceField;
	public EmptyFieldException(String sourcefile,int  sourceline,int sourcefield){
	super(sourcefile,sourceline);
	this.sourceField=sourcefield;
	this.message="";
}
	public EmptyFieldException(String sourcefile,int  sourceline,int sourcefield,String m){
		super(sourcefile,sourceline,m);
		this.sourceField=sourcefield;
	}
	public int getSourceField() {
		return sourceField;
	}
	public void setSourceField(int sourceField) {
		this.sourceField = sourceField;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
}
