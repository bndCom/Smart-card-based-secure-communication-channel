package com.example.demo.tmpAcces;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class online {
    @Id
    private String UID ;

    private String K ;

    private int Timestamp ;

    public int getTimestamp() {
        return Timestamp;
    }

    public String getK() {
        return K;
    }

    public String getUID() {
        return UID;
    }

    public void setK(String k) {
        K = k;
    }

    public void setTimestamp(int timestamp) {
        Timestamp = timestamp;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}

