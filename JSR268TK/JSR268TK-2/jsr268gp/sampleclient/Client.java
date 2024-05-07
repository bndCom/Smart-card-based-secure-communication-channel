package jsr268gp.sampleclient;

import java.sql.Date;
import java.time.LocalDate;
public class Client {

    private long cardNumber ;
    private String expiringDate ;
    private String firstName;

    private String lastName;

    private String userAdress ;


    private byte[] publicKey;

    private byte[] serverPrivateKey ;


    public Client (Long cardNumber , String cardExpiringDate , String firstName , String lastName , String userAdress , byte[] publicKey , byte[] serverPrivateKey) {
        this.cardNumber = cardNumber ;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.userAdress = userAdress ;
        this.publicKey = publicKey;
        this.expiringDate = cardExpiringDate;
        this.serverPrivateKey = serverPrivateKey ;

    }



    public Client (Long cardNumber , String firstName , String lastName , String userAdress) {
        this.cardNumber = cardNumber ;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.userAdress = userAdress ;
        setExpiringDate();
        publicKey = new byte[256];
        serverPrivateKey = new byte [256] ;
    }





    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber ;
    }

    public void setExpiringDate() {
        this.expiringDate = String.valueOf(LocalDate.now().plusYears(5));
    }

    public void setExpiringDate(String string) {
        this.expiringDate = string;
    }


    public String getExpiringDate() {
        return expiringDate ;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }


    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setServerPrivateKey(byte[] serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }


    public byte[] getServerPrivateKey() {
        return serverPrivateKey;
    }

}