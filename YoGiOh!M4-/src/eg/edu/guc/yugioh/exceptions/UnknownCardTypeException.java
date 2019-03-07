package eg.edu.guc.yugioh.exceptions;

public class UnknownCardTypeException extends UnexpectedFormatException{
    private String unknownType;
	private String message;

	public UnknownCardTypeException(String sourcefile,int sourceline,String unknownType){
	super(sourcefile,sourceline);
	this.unknownType=unknownType;
	this.message="";

}
	public UnknownCardTypeException(String sourcefile,int sourceline,String unknownType,String m){
		super(sourcefile,sourceline,m);
		this.unknownType=unknownType;
	}
	public String getUnknownType() {
		return unknownType;
	}
	public void setUnknownType(String unknownType) {
		this.unknownType = unknownType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
