package jsr268gp.sampleapplet;

import javacard.framework.Util;
import javacard.security.CryptoException;
import javacard.security.KeyBuilder;
import javacard.security.MessageDigest;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;
import javacardx.crypto.Cipher;

public class jcDH {
	
	public static final short MODULUS_SIZE = (short)128;
	public static final short HASH_SIZE = (short)32;

	
	// create public key from byte arrays
	public static RSAPublicKey createPublicKey(byte[] modulus, byte[] exponent) {
	
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
		publicKey.setModulus(modulus, (short)0, MODULUS_SIZE);
		publicKey.setExponent(exponent, (short)0, (short)exponent.length);

		return (RSAPublicKey) publicKey;
	}
	
	// create private key from byte arrays
	public static RSAPrivateKey createPrivateKey(byte[] modulus, byte[] exponent) {
		
		RSAPrivateKey privateKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_1024, false);
		privateKey.setModulus(modulus, (short)0, MODULUS_SIZE);
		privateKey.setExponent(exponent, (short)0, (short)exponent.length);

		return (RSAPrivateKey) privateKey;
	}   
	

	static public byte[] modPowRSA(byte[] msg, byte[] rsaPublicKeyExponent, byte[] rsaPrivateKeyModulus){

		Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
        RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
        publicKey.setExponent(rsaPublicKeyExponent, (short) 0, (short) rsaPublicKeyExponent.length);
        publicKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
        cipher.init(publicKey, Cipher.MODE_ENCRYPT);
        byte[] encryptedMsg = new byte[MODULUS_SIZE];
        cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
        
        return encryptedMsg;
    }

	//Compute the SHA-256 hash of the data
	static public byte[] hash(byte[] data) {
	    try {
	        
	    	MessageDigest sha256 = MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
	        sha256.reset();
	        byte[] aesKeyBytes = new byte[HASH_SIZE];
	        sha256.doFinal(data, (short) 0,(short)data.length,aesKeyBytes,(short) 0);
	        return aesKeyBytes;
	
	    } catch (CryptoException e) {
	        return null;
	    }
	}

	// verify signature using rsa public key
	static public boolean verifySignRSA(byte[] data, byte[] msg, byte[] rsaPublicKeyExponent, byte[] rsaPrivateKeyModulus){
		
		Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
		publicKey.setExponent(rsaPublicKeyExponent, (short) 0, (short) rsaPublicKeyExponent.length);
		publicKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
		cipher.init(publicKey, Cipher.MODE_DECRYPT);
		byte[] encryptedMsg = new byte[MODULUS_SIZE];
		cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
		byte[] supposedABHash = new byte[HASH_SIZE];
		Util.arrayCopyNonAtomic(encryptedMsg, (short)96, supposedABHash, (short)0, (short)supposedABHash.length);
		boolean isVerified = Utils.areEqual(hash(data), supposedABHash);
	    return isVerified;
	}
	
	// sign data using rsa private key
	static public byte[] signRSA(byte[] msg, byte[] rsaPrivateKeyExponent, byte[] rsaPrivateKeyModulus){
	
		Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
		RSAPrivateKey privateKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_1024, false);
		privateKey.setExponent(rsaPrivateKeyExponent, (short) 0, (short) rsaPrivateKeyExponent.length);
		privateKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
		cipher.init(privateKey, Cipher.MODE_ENCRYPT);
		byte[] encryptedMsg = new byte[MODULUS_SIZE];
		cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
	    return encryptedMsg;
	}
}
