package jsr268gp.sampleapplet;


import javacard.security.AESKey;
import javacard.security.KeyBuilder;
import javacardx.crypto.Cipher;

class AES {
	//DECLARING THE ATTRIBUTES THAT ARE NEEDED FOR IT TO BASICALLY FUNCTION CORRECTLY 
	Cipher cipher ; 
	AESKey aesKey;
	byte [] IV = new byte[(short) 16 ];
	
	// function that sets up the key value ! 
    //this value requires the existance of the key variable with the name KeyValue
	    public  void keySetUp(byte[] KeyValue){
	    	this.aesKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_128, false);
	    	this.aesKey.setKey(KeyValue, (short) 0);	
            this.cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
            //setting up the IV too 
        	short i;
        	for( i = 0 ; i < 16 ; i++){
        		IV[i] = (byte) 0x00;
        	}

	    }
	    
	    //AES encryption function REQUIRES : Cipher cipher variable that already exists 
	    //takes the data array to encrypt and the array to decrypt 
	    //should take note that the padding should be taken care off separately 
	    //THE FUNCTION DOES NOT PAD , FORMAT THE DATA BEFORE 
	    //THE LENGTH HAS TO BE A MULTIPLE OF 16 So either 16*n
	    
	    public  void AesEncryption(byte [] DataEncrypt , byte[] Data , short length ){
	    	cipher.init(aesKey, Cipher.MODE_ENCRYPT, IV, (short) 0 ,(short) 16 );
            this.cipher.doFinal(Data, (short) 0 , (short) length,DataEncrypt , (short) 0 );	
            //cipher.doFinal(DataIncoming, (short) 0 , (short) 0x10,DataCipher , (short) 0 );


	    }
	    
	    //READ The encryption instructions first since they are pretty similair 
	    
	    
	    public void AesDecrypt(byte[] DataEncrypt , byte[] DataDecrypt , short length){
            //this.cipher.init(aesKey, Cipher.MODE_DECRYPT ,);
	    	this.cipher.init(aesKey, Cipher.MODE_DECRYPT, IV, (short) 0 , (short) 16 );
            this.cipher.doFinal(DataEncrypt, (short) 0 , length ,DataDecrypt, (short) 0 );

	    }
	
}