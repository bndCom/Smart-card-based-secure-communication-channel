package com.example.demo.tmpAcces;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class tmpData {
    @Id
    private long UID ;


    private String A  ; // le nombre calculer


    private String K ; // la cle de la RSA generer

//    private String B ; //the nombre premier that the client sends
//
//    private String p ;// le premier qui vas etre envoiyer par le client

    private int timestamp ; // pretty self explanatory
    
    private boolean isAdmin;

    public void setIsAdmin(boolean isAdmin){
    	this.isAdmin = isAdmin;
    }
    
   public void setUID (long AID){
        this.UID = AID ;
    }

   // public void setP(String P){this.p = P;};

    public void setK (String K_entry ) {
        this.K = K_entry;
   }

    public void setA(String A_entry){
        this.A = A_entry;
   }
    public void setTimestamp(int Timestamp){
        this.timestamp = Timestamp;
    }

    public boolean getIsAdmin(){
    	return this.isAdmin;
    }
    
    public long getUID(){
        return UID;
    }

    public String getA(){
        return A;
    }
    public String getK(){
        return K;

    }

//    public String getP(){
//        return p;
//    }
//
//    public String getB(){
//        return B;
//    }
//
//    public void setB(String b) {
//        B = b;
//    }

    public int getTimestamp() {
        return timestamp;
    }
}