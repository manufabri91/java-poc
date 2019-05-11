package com.ManuFabri.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Mecanico {
	@Id
	@GeneratedValue
	private long idMecanico;
	
	private String apellido, nombre;
	
	
	@OneToMany(mappedBy="mecanico")
	private List<Orden> listaDeOrdenes;

	//GETTERS Y SETTERS
	public long getIdMecanico() {
		return idMecanico;
	}


	public void setIdMecanico(long idMecanico) {
		this.idMecanico = idMecanico;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<Orden> getListaDeOrdenes() {
		return listaDeOrdenes;
	}


	public void setListaDeOrdenes(List<Orden> listaDeOrdenes) {
		this.listaDeOrdenes = listaDeOrdenes;
	}
	
	@Override 
	public String toString() {
		return this.nombre + " "+ this.apellido;
	}
	
	
}
	