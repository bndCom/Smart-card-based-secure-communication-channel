package jsr268gp.sampleclient;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class AesCBC{
    private static final String MODE2 ="AES/CBC/NoPadding";
    private static final String ALGORITHM = "AES";



    public static byte [] encrypt_CBC ( byte [] CipherText , byte[] Key) throws Exception{
        //setting up the iv
        byte [] IvBytes = new byte[16];
        for (int i = 0 ; i < IvBytes.length;i++){
            IvBytes[i] = 0x00;
        }
        IvParameterSpec Iv = new IvParameterSpec(IvBytes);
        //setting up the key
        SecretKeySpec SecretKey = new SecretKeySpec(Key, ALGORITHM);

        //dealing with the cipher
        Cipher cipher = Cipher.getInstance(MODE2);
        cipher.init(Cipher.ENCRYPT_MODE,SecretKey,Iv);

        //the encrypted good stuff
        byte[] encryptedBytes = cipher.doFinal(CipherText);

        return encryptedBytes;




    }

    public static byte [] decrypt_CBC (byte[] CipheredText , byte[] Key)throws Exception{
        // Setting up the IV
        byte [] IvBytes = new byte[16];
        for (int i = 0 ; i < IvBytes.length;i++){
            IvBytes[i] = 0x00;
        }
        //new IvParameterSpec(IvBytes);
        IvParameterSpec Iv = new IvParameterSpec(IvBytes);

        //setting up the key
        SecretKey secretKey = new SecretKeySpec(Key,ALGORITHM);

        //intializing the cipher
        Cipher cipher = Cipher.getInstance(MODE2);

        //cipher.init(Cipher.DECRYPT_MODE,secretKey);
        cipher.init(Cipher.DECRYPT_MODE,secretKey,Iv);


        //doing the decryption
        byte[] decryptedBytes = cipher.doFinal(CipheredText);
        //returnong the result
        return decryptedBytes;


    }



}