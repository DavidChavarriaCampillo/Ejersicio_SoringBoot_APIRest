package com.springBoot.gestionBiblioteca.model;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
public class Prestamo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaPrestamo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaVencimineto;
	
	private static final int DIAS_PRESTAMO = 30;
	private static final int CONVERTIR_DIAS = 1000*60*60*24;
	private static final int MULTA = 500;
	private static final int SIN_MULTA = 0;
	
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Libro libro;
	
	@ManyToOne
	private Multa multa;

	public Prestamo() {
		this.fechaPrestamo = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPrestamo);
		calendar.add(Calendar.DATE, DIAS_PRESTAMO);
        this.fechaVencimineto = calendar.getTime();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Date getFechaVencimineto() {
		return fechaVencimineto;
	}

	public void setFechaVencimineto(Date fechaVencimineto) {
		this.fechaVencimineto = fechaVencimineto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}
	
	public void existeMulta(Date fecha, Multa multa) {
		long startTime = fecha.getTime() ;
	     long endTime = new Date().getTime();
	     long diasDesde = (long) Math.floor(startTime / CONVERTIR_DIAS); 
	     long diasHasta = (long) Math.floor(endTime / CONVERTIR_DIAS);
	     long dias = diasHasta - diasDesde;
	     if(dias >= DIAS_PRESTAMO) {
	    	 multa.setValor(MULTA);
	     }else {
	    	 multa.setValor(SIN_MULTA);
	     }
	}
}
