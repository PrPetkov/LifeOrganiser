package com.example.lifeorganiser.src.Models.Exceptions;

public class IllegalAmountExeption extends Exception {
	
	public IllegalAmountExeption() {};
	
	public IllegalAmountExeption(String text) {
		super(text);
	}
	
	public IllegalAmountExeption(String text, Throwable couse) {
        super(text, couse);
    }

    public IllegalAmountExeption(Throwable couse) {
        super(couse);
    }

}
