package jsr268gp.sampleclient;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import java.security.*;
import java.math.BigInteger;

public class diffietest {
    public static void main(String[] args) {
        try {
            // Generate Diffie-Hellman key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
            keyPairGenerator.initialize(2048);

            // Get a SecureRandom instance
            SecureRandom random = new SecureRandom();

            // Initialize the key pair generator with the SecureRandom object
            keyPairGenerator.initialize(2048, random);

            // Generate key pair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Extract the public key
            PublicKey publicKey = keyPair.getPublic();

            // Cast the public key to DHPublicKey to access generator and prime modulus
            DHPublicKey dhPublicKey = (DHPublicKey) publicKey;

            // Get the prime modulus (p) and the generator (g) from the public key
            BigInteger primeModulus = dhPublicKey.getParams().getP();
            BigInteger generator = dhPublicKey.getParams().getG();

            // Print the prime modulus and the generator
            System.out.println("Prime Modulus (p): " + primeModulus);
            System.out.println("Generator (g): " + generator);

            // Print the public key
            //System.out.println("Public Key: " + publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

