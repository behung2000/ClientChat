package SERCURITY;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KhoaRSA {
    private PublicKey publicKey;

    private static final String PUBLIC_KEY_STRING =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkQCLRvqKyZfbUYrXRmg+vU3Foov75R0DfvCvoUSR6bOPEZXOkZMkstR+tA7XULNbp1Ep46Dyj0/mYIz9do13amkgjGu5wqj3yDyCRCN8agPfI8eewoxoRK6FZgSJQF6T5iAx/DWrbCtnOo0MJPOA5QuNoMIBs/m4gy4193q9upwIDAQAB";


    /*
    //Lúc đầu tạo key
    public void init(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
        } catch (Exception ignored) {
        }
    }
     */

    public void initFromStrings(){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
        }catch (Exception ignored){}
    }


    public void printKeys(){
        System.out.println("Public key\n"+ encode(publicKey.getEncoded()));
    }

    public String encrypt(String message) {
        try {
            byte[] messageToBytes = message.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(messageToBytes);
            return encode(encryptedBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e){
            return "false";
        }
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }


/*
    public static void main(String[] args) {
        KhoaRSA rsa = new KhoaRSA();
        rsa.initFromStrings();

        String encryptedMessage = rsa.encrypt("chào hưng");

        System.out.println("Encrypted:\n"+encryptedMessage);
    }
 */

}
