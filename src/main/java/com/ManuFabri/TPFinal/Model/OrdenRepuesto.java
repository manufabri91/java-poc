package com.ManuFabri.TPFinal.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrdenRepuesto {
	
	@Id
	@GeneratedValue
	private long idOrdenRepuesto;
	
	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;
	
	@ManyToOne
	@JoinColumn(name="idRepuesto")
	private Repuesto repuesto;
	
	private int cantidad;
	private double cantHoras;
	
	
	
	
	
	//GETTERS Y SETTERS
	public long getIdOrdenRepuesto() {
		return idOrdenRepuesto;
	}
	public void setIdOrdenRepuesto(long idOrdenRepuesto) {
		this.idOrdenRepuesto = idOrdenRepuesto;
	}
	public Orden getOrden() {
		return orden;
	}
	public void setOrden(Orden orden) {
		this.orden = orden;
	}
	public Repuesto getRepuesto() {
		return repuesto;
	}
	public void setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getCantHoras() {
		return cantHoras;
	}
	public void setCantHoras(double cantHoras) {
		this.cantHoras = cantHoras;
	}

	
	
	
}
