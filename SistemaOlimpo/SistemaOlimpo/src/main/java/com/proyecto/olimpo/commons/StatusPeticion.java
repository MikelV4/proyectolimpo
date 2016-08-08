package com.proyecto.olimpo.commons;

public enum StatusPeticion {
	EXITO("OK"),
	ERROR("ERROR");
	
	private String descripcion="";
	
	private StatusPeticion(String description){
		this.descripcion = description;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
