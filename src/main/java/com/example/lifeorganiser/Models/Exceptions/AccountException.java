package com.example.lifeorganiser.src.Models.Exceptions;


public class AccountException extends Exception {

    public AccountException(){
        super();
    }

    public AccountException(String message){
        super(message);
    }

    public AccountException(String message, Throwable couse){
        super(message, couse);
    }

    public AccountException(Throwable couse){
        super(couse);
    }
}
