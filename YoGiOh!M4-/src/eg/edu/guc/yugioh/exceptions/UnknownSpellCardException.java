package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException{
    private String unknownSpell;
	private String message;
	public UnknownSpellCardException(String sourcefile,int sourceline,String unknownSpell){
	super(sourcefile,sourceline);
	this.unknownSpell=unknownSpell;
	this.message="";
}
	public UnknownSpellCardException(String sourcefile,int sourceline,String unknownSpell,String m){
		super(sourcefile,sourceline,m);
		this.unknownSpell=unknownSpell;
	}
	public String getUnknownSpell() {
		return unknownSpell;
	}
	public void setUnknownSpell(String unknownSpell) {
		this.unknownSpell = unknownSpell;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
