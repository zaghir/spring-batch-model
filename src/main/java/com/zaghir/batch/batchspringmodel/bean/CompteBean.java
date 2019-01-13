package com.zaghir.batch.batchspringmodel.bean;

import java.io.Serializable;

public class CompteBean implements Serializable{
	
	private Integer id ;
	private String nom;
	private String prenom;
	private String numeroCompte ;
	
	public CompteBean() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	@Override
	public String toString() {
		return "CompteBean [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", numeroCompte=" + numeroCompte + "]";
	}
	
	

}
