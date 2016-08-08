/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.olimpo.model.commons;

import com.proyecto.olimpo.commons.StatusPeticion;
import java.io.Serializable;

/**
 *
 * @author Infinity-PC
 */
public class ContenedorResponse<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long codigo;//
	private String descripcion;
	private boolean isError;//true o false
	private T data;
	private StatusPeticion status;//
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public StatusPeticion getStatus() {
		return status;
	}
	public void setStatus(StatusPeticion status) {
		this.status = status;
	}
}
	