package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import application.FXMLDocumentController;

public class Sismo {
	public SimpleStringProperty fecha = new SimpleStringProperty(); // dia, mes ,año
	public SimpleStringProperty instanteOcurrido = new SimpleStringProperty(); // Hora, minuto, segundo
	public SimpleStringProperty profundidad = new SimpleStringProperty(); // (Km)
	public SimpleStringProperty origen = new SimpleStringProperty(); // Subduccion, Choque, falla local, intra o demormacion
	public SimpleStringProperty magnitud = new SimpleStringProperty(); // Segun escala Richter
	public SimpleStringProperty latitud = new SimpleStringProperty(); // En grados
	public SimpleStringProperty longitud = new SimpleStringProperty(); // En grados
	public SimpleStringProperty descripcion = new SimpleStringProperty();
	public SimpleStringProperty provincia = new SimpleStringProperty();
	public boolean esMaritimo = false;

/*
	public Sismo(String pFecha, String pInstanteOcurrido, String pProfuncidad, String pOrigen, String pMagnitud, String pLatitud, String pLongitud, String pDescripcion, String pProvincia){
		this.fecha = new SimpleStringProperty(pFecha);
		this.instanteOcurrido = new SimpleStringProperty(pInstanteOcurrido);
		this.profundidad = new SimpleStringProperty(pProfuncidad);
		this.origen = new SimpleStringProperty(pOrigen);
		this.magnitud = new SimpleStringProperty(pMagnitud);
		this.latitud = new SimpleStringProperty(pLatitud);
		this.longitud = new SimpleStringProperty(pLongitud);
		this.descripcion = new SimpleStringProperty(pDescripcion);
		this.provincia = new SimpleStringProperty(pProvincia);
	}
*/


	public String getFecha() {
		return fecha.get();
	}
	public void setFecha(String pFecha) {
		this.fecha.set(pFecha);
	}

	public String getInstanteOcurrido() {
		return instanteOcurrido.get();
	}
	public void setInstanteOcurrido(String pInstanteOcurrido) {
		this.instanteOcurrido.set(pInstanteOcurrido);
	}

	public String getProfundidad() {
		return profundidad.get();
	}
	public void setProfundidad(String pProfundidad) {
		this.profundidad.set(pProfundidad);
	}

	public String getOrigen() {
		return origen.get();
	}
	public void setOrigen(String pOrigen) {
		this.origen.set(pOrigen);
	}

	public String getMagnitud() {
		return magnitud.get();
	}
	public void setMagnitud(String pMagnitud) {
		this.magnitud.set(pMagnitud);
	}

	public String getLatitud() {
		return latitud.get();
	}
	public void setLatitud(String pLatitud) {
		this.latitud.set(pLatitud);
	}

	public String getLongitud() {
		return longitud.get();
	}
	public void setLongitud(String pLongitud){
		this.longitud.set(pLongitud);
	}

	public String getDescripcion() {
		return descripcion.get();
	}
	public void setDescripcion(String pDescripcion) {
		this.descripcion.set(pDescripcion);
	}

	public String getProvincia() {
		return provincia.get();
	}
	public void setProvincia(String pProvincia) {
		this.provincia.set(pProvincia);
	}

	public boolean getEsMaritimo() {
		return esMaritimo;
	}
	public void setEsMaritimo(boolean pEsMaritimo) {
		this.esMaritimo = pEsMaritimo;
	}



}