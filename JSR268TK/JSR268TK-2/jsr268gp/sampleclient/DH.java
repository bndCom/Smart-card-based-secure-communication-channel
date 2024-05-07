package jsr268gp.sampleclient;

import javax.crypto.interfaces.DHPublicKey;

import java.security.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import java.security.*;

public class DH {

	public static BigInteger generateRandomPrime(int bitLength) {
        SecureRandom secureRandom = new SecureRandom();
        return BigInteger.probablePrime(bitLength, secureRandom);
    }
	
	public static BigInteger generateRandom(int bitLength) {
		SecureRandom secureRandom = new SecureRandom();
        BigInteger randomNum = new BigInteger(bitLength, secureRandom);
        return randomNum;
	}
	
	// find modular exponentiation
	public static byte[] modularExponentiation(byte[] base, byte[] exponent, byte[] modulus) {
        // Convert byte arrays to BigIntegers
        BigInteger baseInt = new BigInteger(1, base);
        BigInteger exponentInt = new BigInteger(1, exponent);
        BigInteger modulusInt = new BigInteger(1, modulus);
        
        // Perform modular exponentiation
        BigInteger resultInt = baseInt.modPow(exponentInt, modulusInt);
        
        // Convert result back to byte array
        byte[] result = resultInt.toByteArray();
        
    	if(result.length!=modulus.length){ 
	    // Copy elements from PP starting from index 1 to modifiedArray
    		byte[] tmp = new byte[result.length - 1];
    		System.arraycopy(result, 1, tmp, 0, tmp.length);
    		return tmp;
    	}
        
        return result;
    }
	
	// convert long to byte array
	public static byte[] longToBytes(long numberLong){
		 // Example long number
        
        byte[] byteArray = new byte[8]; // Long is 8 bytes in Java
        
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (numberLong >> (i * 8));
        }
		
        return byteArray;
	}
	
	// convert byte array to long
	public static long byteArrayToLong(byte[] byteArray) {
	    if (byteArray.length < 8) {
	        // Create a new byte array with enough space for padding
	        byte[] paddedArray = new byte[8];
	        // Copy the original data to the padded array starting from index 8 - length of original array
	        System.arraycopy(byteArray, 0, paddedArray, 8 - byteArray.length, byteArray.length);
	        // Create a ByteBuffer and wrap the padded byte array
	        ByteBuffer buffer = ByteBuffer.wrap(paddedArray);
	        buffer.order(ByteOrder.LITTLE_ENDIAN);
	        // Get the long value from the ByteBuffer
	        return buffer.getLong();
	    } else {
	        // If the byte array already has 8 or more bytes, proceed as before
	        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
	        buffer.order(ByteOrder.LITTLE_ENDIAN);
	        return buffer.getLong();
	    }
	}
	
	// RSA sign using private key
	public static byte[] signData (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
		byte[] digitalSignature = null;
		
		try{
		// Création d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signatureIns = Signature.getInstance("SHA1withRSA");
	    
	    // Initialisation de l'objet Signature avec la clé privée
	    signatureIns.initSign(privateKey);
	    
	    // Ajout des données à signer
	    signatureIns.update(data);
	    
	    // Signature des données
	     digitalSignature = signatureIns.sign();
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return digitalSignature;
	}
	
	// RSA sign using private key from scratch
		public static byte[] signDataRSA (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
			byte[] encryptedData = null;
			
			try{
				Cipher cipher = Cipher.getInstance("RSA");
	            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	            encryptedData = cipher.doFinal(data);
			}catch(Exception e){
				e.printStackTrace();
			}
			return encryptedData;
		}
		
		// RSA sign using private key from scratch
				public static byte[] VerifySignRSA (byte[] data,PublicKey privateKey)throws NoSuchAlgorithmException{
					byte[] encryptedData = null;
					
					try{
						Cipher cipher = Cipher.getInstance("RSA");
			            cipher.init(Cipher.DECRYPT_MODE, privateKey);
			            encryptedData = cipher.doFinal(data);
					}catch(Exception e){
						e.printStackTrace();
					}
					return encryptedData;
				}
				public static byte[] decrypt(byte[] encryptedData, PublicKey privateKey) throws Exception {
			        // Create RSA Cipher instance
			        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			        
			        // Initialize the Cipher for decryption with the private key
			        cipher.init(Cipher.DECRYPT_MODE, privateKey);
			        
			        // Perform decryption
			        byte[] decryptedData = cipher.doFinal(encryptedData);
			        
			        return decryptedData;
			    }

	
	public static boolean verifySignature(byte[] data, byte[] signature,PublicKey publicKey) {
		boolean isVerified = false;
		try{
		// Création d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signatureIns = Signature.getInstance("SHA1withRSA");
	    
	    // Initialisation de l'objet Signature avec la clé privée
	    signatureIns.initVerify(publicKey);
	    
	    // Ajout des données à signer
	    signatureIns.update(data);
	    
	    // Signature des données
	    isVerified = signatureIns.verify(signature);
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return isVerified;
   }
	
	// masque function
	public static byte[] masqueFunction(byte[] secret){
		// using md5
		byte[] out = new byte[16];
		System.arraycopy(hash(secret), 0, out, 0, out.length);
		return out;
	}
	
	public static byte[] hash(byte[] secret){
		try {
	        // Use SHA-256 as the hash function
	        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

	        // Compute the SHA-256 hash of the shared secret
	        byte[] sha256Hash = sha256.digest(secret);

	        // Truncate the hash to 128 bits (16 bytes)
	        byte[] aesKeyBytes = new byte[32];
	        System.arraycopy(sha256Hash, 0, aesKeyBytes, 0, aesKeyBytes.length);
	        return aesKeyBytes;

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	// print byte array
	public static void printByteArray(byte[] byteArray) {
        System.out.print("[");
        for (int i = 0; i < byteArray.length; i++) {
            System.out.print(String.format("%02X", byteArray[i]));
            if (i < byteArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
	
	// concatenate two byte arrays
	public static byte[] concat(byte[] A, byte[] B){
		
		byte[] AB = new byte[A.length + B.length];
		System.arraycopy(A, 0, AB, 0, A.length);
		System.arraycopy(B, 0, AB, A.length, B.length);
		return AB;
		
	}
	
	// delete added bytes
	public static byte[] adjustArray(byte[] arr, int size){
		if(arr.length != size){
			byte[] tmp = new byte[size];
			System.arraycopy(arr, 1, tmp, 0, size);
    		return tmp;
			
		}
		return arr;
	}
}