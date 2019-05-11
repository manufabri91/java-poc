package com.ManuFabri.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cliente {

	@Id
	@GeneratedValue
	private long idCliente;
	
	private String apellido, nombre, direccion;
	private long telefono;
	
	@OneToMany(mappedBy="cliente")
	private List<Orden> listaDeOrdenes;

	
	//GETTERS Y SETTERS
	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
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
