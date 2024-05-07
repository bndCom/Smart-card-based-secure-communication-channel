package com.example.demo.tmpAcces;


public class PatientDto {

	private Long id;
	
    private String nom;
   
    private String prenom;
    
    private int age;
    private String adresse;
    private int numdoss;
    private int nbseances;
    
    public PatientDto() {
		
	}

    public PatientDto(String nom, String prenom, int age, String adresse, int numdoss) {
		this.nom = nom;
		this.prenom = prenom;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
