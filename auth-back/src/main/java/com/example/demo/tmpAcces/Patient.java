package com.example.demo.tmpAcces;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId ;
    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private int numdoss;
    private int nbseances;
    
    public Patient() {
		
	}

    public Patient(String firstName, String lastName, int age, String adresse, int numdoss) {
		this.nom = firstName;
		this.prenom = lastName;
		this.age = age;
		this.adresse = adresse;
		this.numdoss = numdoss;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String firstName) {
		this.nom = firstName;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String lastName) {
		this.prenom = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getNumdoss() {
		return numdoss;
	}

	public void setNumdoss(int numdoss) {
		this.numdoss = numdoss;
	}

	public int getNbseances() {
		return nbseances;
	}

	public void setNbseances(int nbseances) {
		this.nbseances = nbseances;
	}


}