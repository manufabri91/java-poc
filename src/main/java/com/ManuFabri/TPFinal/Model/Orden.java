package com.ManuFabri.TPFinal.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Orden {
	
	public static double PRECIO_HORA = 350.00;
	
	@Id
	@GeneratedValue
	private long idOrden;
	
	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "idMecanico")
	private Mecanico mecanico;
	
	@OneToMany(mappedBy = "orden")
	private List<OrdenRepuesto> listaOrdenesRepuestosO;
	
	private String vehiculo, patente, fallaDeclarada, fIngreso;
	
	private Boolean ordenAbierta;
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	//GETTERS Y SETTERS
	public long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}
	
	public boolean isOrdenAbierta() {
		return ordenAbierta;
	}
	public void setOrdenAbierta(boolean ordenAbierta) {
		this.ordenAbierta = ordenAbierta;
	}
	public List<OrdenRepuesto> getListaOrdenesRepuestosO() {
		return listaOrdenesRepuestosO;
	}

	public void setListaOrdenesRepuestosO(List<OrdenRepuesto> listaOrdenesRepuestosO) {
		this.listaOrdenesRepuestosO = listaOrdenesRepuestosO;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getFallaDeclarada() {
		return fallaDeclarada;
	}

	public void setFallaDeclarada(String fallaDeclarada) {
		this.fallaDeclarada = fallaDeclarada;
	}

	public String getfIngreso() {
		return fIngreso;
	}

	public void setfIngreso(String fIngreso) {
		this.fIngreso = fIngreso;
	}

	public static double getPrecioHora() {
		return PRECIO_HORA;
	}
	
	
	public Boolean getOrdenAbierta() {
		return ordenAbierta;
	}

	public void setOrdenAbierta(Boolean ordenAbierta) {
		this.ordenAbierta = ordenAbierta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//FUNCIONES PROPIAS
	//CALCULAR MONTO TOTAL DE UNA ORDEN
	public double getTotalOrden() {
		double total = 0.0;
		//PARA CADA ITEM DE ORDEN_REPUESTO EN ESTA ORDEN
		for (int i = 0; i < this.getListaOrdenesRepuestosO().size(); i++) {
			//SUMO EL COSTO DEL REPUESTO
			total += this.getListaOrdenesRepuestosO().get(i).getRepuesto().getCosto() * this.getListaOrdenesRepuestosO().get(i).getCantidad();
			//SUMO EL PRECIO DE LA MANO DE OBRA
			total += this.getListaOrdenesRepuestosO().get(i).getCantHoras() * Orden.PRECIO_HORA;
		}
		//DEVUELVO EL TOTAL
		return total;
	}
	//CALCULAR TIEMPO INCURRIDO
	public double getTiempoTrabajado() {
		double total = 0.0;
		//PARA CADA ITEM DE ORDEN_REPUESTO EN ESTA ORDEN
		for (int i = 0; i < this.getListaOrdenesRepuestosO().size(); i++) {
			//SUMO LA CANTIDAD DE HORAS
			total += this.getListaOrdenesRepuestosO().get(i).getCantHoras();
		}
		//DEVUELVO EL TOTAL
		return total;
	}
	//ADICIONAL POR MANO DE OBRA
	public double getAdicionalManoObra() {
		double total = 0.0;
		//PARA CADA ITEM DE ORDEN_REPUESTO EN ESTA ORDEN
		for (int i = 0; i < this.getListaOrdenesRepuestosO().size(); i++) {
			//SUMO EL PRECIO DE LA MANO DE OBRA
			total += this.getListaOrdenesRepuestosO().get(i).getCantHoras() * Orden.PRECIO_HORA;
		}
		//DEVUELVO EL TOTAL
		return total;
	}
	
}
