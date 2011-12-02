package edu.uib.info310.exception;

public class NoSuchFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public NoSuchFormatException(){
		super();
	}
	
	public NoSuchFormatException(String e){
		super(e);
	}
}
