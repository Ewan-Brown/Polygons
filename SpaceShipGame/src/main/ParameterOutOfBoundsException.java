package main;

public class ParameterOutOfBoundsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterOutOfBoundsException(){
		super("This thing is too high or too low :(");
	}
	
}
