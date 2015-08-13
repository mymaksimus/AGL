package de.skysoldier.abstractgl2.mklmbversion.lib.base;

public class AGLException extends RuntimeException {

	public AGLException(String error){
		super(error);
	}
	
	public AGLException(Exception parentException){
		super(parentException);
	}
	
	public AGLException(String error, Exception parentException){
		super(error, parentException);
	}
}
