package com.elyes.entities;

import com.j256.ormlite.field.DatabaseField;

public class Category {
	@DatabaseField(id=true)
	private int id;
	@DatabaseField
	private String Libelle;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return Libelle;
	}
	public void setLibelle(String libelle) {
		Libelle = libelle;
	}
	public Category(int id, String libelle) {
		super();
		this.id = id;
		Libelle = libelle;
	}
	public Category(){
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+"-"+Libelle;
	}
	

}
