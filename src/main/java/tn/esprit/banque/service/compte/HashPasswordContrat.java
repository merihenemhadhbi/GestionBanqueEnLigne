package tn.esprit.banque.service.compte;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Hex;

import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidHashPasswordException;

@Service
public class HashPasswordContrat implements hashPassword{
	final String salt = "1234";
    final byte[] saltBytes = salt.getBytes();
    final int iterations = 10000;
    final int keyLength = 255;

    
    @Override
    public  String hashPassword(String password) throws InvalidHashPasswordException {


        try {
            char[] passwordChars = password.toCharArray();
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( passwordChars, saltBytes, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );

            return Hex.encodeHexString(res);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new InvalidHashPasswordException("L'algorithme cryptographique est demandé mais n'est pas disponible dans l'environnement et/ou La clé de cryptage est invalide");
        }
    }

}
