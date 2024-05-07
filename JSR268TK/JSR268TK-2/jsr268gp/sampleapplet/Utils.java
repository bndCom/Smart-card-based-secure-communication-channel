package jsr268gp.sampleapplet;

import javacard.framework.ISOException;
import javacard.framework.Util;
import javacard.security.CryptoException;
import javacard.security.RandomData;

public class Utils {
	
	public static final short AES_KEY_SIZE = (short)16;
	public static final short MODULUS_SIZE = (short)128;

	
	static public boolean areEqual(byte[] array1, byte[] array2) {
	    // compare two byte arrays
	    if (array1 == null || array2 == null || array1.length != array2.length) {
	        return false;
	    }
	    return (Util.arrayCompare(array1, (short) 0,array2, (short) 0, (short) array2.length)==0);
	}
	
	// concatenate two byte arrays
	static public byte[] concat(byte[] A, byte[] B){
		
		byte[] AB = new byte[(short) (A.length + B.length)];
		Util.arrayCopyNonAtomic(A, (short)0, AB, (short)0, (short)A.length);
		Util.arrayCopyNonAtomic(B, (short)0, AB, (short)A.length, (short)B.length);
		return AB;
		
	}
	
	// apply masque function on a byte array ( reduce the size )
	static public byte[] masqueFunction(byte[] data){
		
		byte[] out = new byte[AES_KEY_SIZE];
		Util.arrayCopyNonAtomic(jcDH.hash(data), (short)0, out, (short)0	, (short)out.length);
		return out;
	}
	
	// generate random number
	static public byte[] generateRandomNumber() {

        byte[] data = new byte[MODULUS_SIZE];
        try {
    		RandomData randomData = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);      
            randomData.generateData(data, (short) 0, (short) MODULUS_SIZE);

        } catch (CryptoException e) {
            short reason = e.getReason();
            ISOException.throwIt(reason);
        }
        return data;    
    }
}
