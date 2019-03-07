package eg.edu.guc.yugioh.exceptions;

public class WrongPhaseException extends RuntimeException {
public WrongPhaseException(){
	super();
}
public WrongPhaseException(String m){
	super(m);
}
}
