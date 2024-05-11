package com.example.demo.tmpAcces.models;
import jakarta.persistence.*;

@Entity

public class online {
    @Id
    private long UID ;

    private String K ;

    private long Timestamp ;

    public long getTimestamp() {
        return Timestamp;
    }

    public String getK() {
        return K;
    }

    public long getUID() {
        return UID;
    }

    public void setK(String k) {
        K = k;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }
}

