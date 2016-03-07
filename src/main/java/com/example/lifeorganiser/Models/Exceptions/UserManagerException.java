package com.example.lifeorganiser.src.Models.Exceptions;


public class UserManagerException extends Exception {

    public UserManagerException(){
        super();
    }

    public UserManagerException(String message){
        super(message);
    }

    public UserManagerException(String message, Throwable cause){
        super(message, cause);
    }

    public UserManagerException(Throwable cause){
        super(cause);
    }
}
