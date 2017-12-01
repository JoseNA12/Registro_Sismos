package actualizar;

import java.io.IOException;

import application.FXMLDocumentController;
import application.Sismo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 * Es la clase que se encarga de controlar la ventana de actualizar sismo, estableciendo como atributos los componentes visuales de ésta.
 */
public class FXMLActualizarController {
	// public: porque cuando se inicializa la ventana, automaticamente se necesitan en la clase FXMLDocumentController
	@FXML public Label label_idSismo;
	@FXML public TextField entry_FechaAct;
	@FXML public TextField entry_InstanteAct;
	@FXML public TextField entry_ProfundidadAct;
	@FXML public TextField entry_OrigenAct;
	@FXML public TextField entry_MagnitudAct;
	@FXML public TextField entry_ProvinciaAct;
	@FXML public TextField entry_LatitudAct;
	@FXML public TextField entry_LongitudAct;
	@FXML public TextArea entry_DescripcionAct;
	@FXML public Button button_Aceptar;
	@FXML public Button button_Actualizar;
	@FXML public Label label_MensajeAviso;
	@FXML public Label label_MensajeRegistrado;


	/**
	 * Método encargado de modificar toda la información de un sismo selecionado. Existen validaciones para que no se permita..
	 * ..dejar ningún espacio en blanco y para que los TextField reciban el tipo de dato correcto.
	 * Se crea un objeto sismo donde se le asignan los atributos establecidos en los TextFiled.
	 * Además, se obtiene el índice del sismo selecionado para luego, actualizar la información
	 * @param event, evento asignado al botón button_Actualizar, con el fin de ejecutar el método
	 */
	@FXML
    private void modificarInformacionSismo(ActionEvent event) {

    	// Valida para que todos lo campos no esten vacios
    	if(entry_FechaAct.getText().equals("") || entry_InstanteAct.getText().equals("") || entry_ProfundidadAct.getText().equals("") ||
    			entry_OrigenAct.getText().equals("") || entry_MagnitudAct.getText().equals("") || entry_ProvinciaAct.getText().equals("") ||
    			entry_LatitudAct.getText().equals("") || entry_LongitudAct.getText().equals("") || entry_DescripcionAct.getText().equals("")){

    		label_MensajeAviso.setText("*Compruebe que todos los campos estén rellenos!");

    	}else{
    		try{
		        Sismo sismo = new Sismo();
		        FXMLDocumentController control = new FXMLDocumentController();

		        // Asignacion a los atributos
			    sismo.fecha.set(entry_FechaAct.getText());
			    sismo.instanteOcurrido.set(entry_InstanteAct.getText());
			    sismo.profundidad.set(entry_ProfundidadAct.getText());
			    sismo.origen.set(entry_OrigenAct.getText());
			    sismo.magnitud.set(entry_MagnitudAct.getText());
			    sismo.provincia.set(entry_ProvinciaAct.getText());
			    sismo.latitud.set(entry_LatitudAct.getText());
			    sismo.longitud.set(entry_LongitudAct.getText());
			    sismo.descripcion.set(entry_DescripcionAct.getText());

			    double magnitud = Double.parseDouble(sismo.getMagnitud());
			    double profundidad = Integer.parseInt(sismo.getProfundidad());
			    double latitud = Double.parseDouble(sismo.getLatitud());
			    double longitud = Double.parseDouble(sismo.getLongitud());

			    FXMLDocumentController.sismos.set(FXMLDocumentController.posicionSismoEnTabla, sismo);

			    button_Actualizar.setDisable(true);
			    label_MensajeAviso.setText("");
			    label_MensajeRegistrado.setText("*Se ha actualizado la información del sismo correctamente!");

    		}catch(NumberFormatException e){ // En caso de ingresar letras en textField magnitud
	    		//e.printStackTrace();
    			label_MensajeAviso.setText("*Verifique que los campos 'Profundidad', 'Magnitud', 'Latitud' o 'Longitud' contengan solo numeros!");
	    	}
    	}
    }

	/**
	 * Método encargado de ocultar la ventana de actualizar sismo
	 * @param event, evento asignado al button_Aceptar
	 * @throws IOException, en caso de no ser posible llevar acabo dicha acción
	 */
    public void ocultarVentana(ActionEvent event) throws IOException{
		Stage stage = (Stage) button_Aceptar.getScene().getWindow();
		stage.hide();
	}


}
