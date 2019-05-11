package com.ManuFabri.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Repuesto {
	
	@Id
	@GeneratedValue
	private long idRepuesto;
	
	private String descripcion;
	private double costo;
	
	@OneToMany(mappedBy = "repuesto")
	private List<OrdenRepuesto> listaOrdenesRepuestosR;


	
	
	//GETTERS AND SETTERS
	public long getIdRepuesto() {
		return idRepuesto;
	}

	public void setIdRepuesto(long idRepuesto) {
		this.idRepuesto = idRepuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public List<OrdenRepuesto> getListaOrdenesRepuestosR() {
		return listaOrdenesRepuestosR;
	}

	public void setListaOrdenesRepuestosR(List<OrdenRepuesto> listaOrdenesRepuestosR) {
		this.listaOrdenesRepuestosR = listaOrdenesRepuestosR;
	}
	
	@Override 
	public String toString() {
		return this.descripcion;
	}
	
	
	
}
