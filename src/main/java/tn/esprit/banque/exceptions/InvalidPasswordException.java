package tn.esprit.banque.exceptions;

public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(String str){
        super(str);
    }

}
