package servicio;

import javafx.beans.property.SimpleStringProperty;

public class Persona {

	public SimpleStringProperty nombre = new SimpleStringProperty();
	public SimpleStringProperty correoElectronico = new SimpleStringProperty();
	public SimpleStringProperty telefono = new SimpleStringProperty();
	public SimpleStringProperty provincia = new SimpleStringProperty();


	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String pNombre) {
		this.nombre.set(pNombre);
	}

	public String getCorreoElectronico() {
		return correoElectronico.get();
	}
	public void setCorreoElectronico(String pCorreoElectronico) {
		this.correoElectronico.set(pCorreoElectronico);
	}

	public String getTelefono() {
		return telefono.get();
	}
	public void setTelefono(String pTelefono) {
		this.telefono.set(pTelefono);
	}

	public String getProvincia() {
		return provincia.get();
	}
	public void setProvincia(String pProvincia) {
		this.provincia.set(pProvincia);
	}
}
