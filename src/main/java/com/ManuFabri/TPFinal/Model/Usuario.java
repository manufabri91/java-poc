package com.ManuFabri.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private long idUsuario;
	
	private String user, password;
	
	@OneToMany(mappedBy="usuario")
	private List<Orden> ordenesCerradas;

	//GETTERS Y SETTERS

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public String getUser() {
		return user;
	}


	public String getPassword() {
		return password;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public List<Orden> getOrdenesCerradas() {
		return ordenesCerradas;
	}

	public void setOrdenesCerradas(List<Orden> ordenesCerradas) {
		this.ordenesCerradas = ordenesCerradas;
	}

	@Override
	public String toString() {
		return this.user;
	}
	
	
	
	
}
