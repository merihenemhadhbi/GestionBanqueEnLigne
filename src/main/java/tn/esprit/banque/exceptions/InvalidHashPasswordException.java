package tn.esprit.banque.exceptions;

public class InvalidHashPasswordException extends Exception{

    public InvalidHashPasswordException(String str){
        super(str);
    }

}
