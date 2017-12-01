package registro;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import application.FXMLDocumentController;
import application.Sismo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import servicio.EnviadorMail;
import servicio.FXMLServicioController;
import servicio.Persona;

/**
 * Se encarga del registro sismos y de controlar la ventana donde éste se registra.
 * Se definen como atributos los componentes visuales de la ventana de registro.
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 */
public class FXMLRegistroController {

	@FXML private TextField entry_Fecha;
	@FXML private TextField entry_Instante;
	@FXML private TextField entry_Profundidad;
	@FXML private TextField entry_Origen;
	@FXML private TextField entry_Magnitud;
	@FXML private TextField entry_Provincia;
	@FXML private TextField entry_Latitud;
	@FXML private TextField entry_Longitud;
	@FXML private TextArea entry_Descripcion;
	@FXML private Button button_RegistrarSismo;
	@FXML private Label label_MensajeError;
	@FXML private Label label_MensajeRegistrado;
	@FXML private Label label_MensajeCorreoSms;

	/**
	 * El metodo se encarga de registrar un sismo por medio de un evento.
	 * Presenta validaciones para que los campos "TextField" no esten vacios y tengan el tipo de dato correcto..
	 * ..en el momento de registrar el sismo. Se inicializa la clase "FXMLDocumentController" para registar el sismo..
	 * ..junto con las asignaciones en los "TextField" con la informacion de dicho sismo. Además, se registra el nuevo..
	 * ..sismo en archivo "registroSismo.csv". Por último, se inicialliza el metodo "activarServicioNotificacion"..
	 * ..enviadole como parametro algunos datos del sismo con el fin de enviar un correo electronico.
	 * @param event, evento asiganado al boton "button_RegistrarSismo" con el fin de registrar el sismo.
	 */
	@FXML
	public void registrarSismo(ActionEvent event){ // Obtiene lo ingresado e escribe en al archivo .csv

		// Valida para que todos lo campos no esten vacios
		if(entry_Fecha.getText().equals("") || entry_Instante.getText().equals("") || entry_Profundidad.getText().equals("") ||
				entry_Origen.getText().equals("") || entry_Magnitud.getText().equals("") || entry_Provincia.getText().equals("") ||
				entry_Latitud.getText().equals("") || entry_Longitud.getText().equals("") || entry_Descripcion.getText().equals("")){

			label_MensajeError.setText("*Compruebe que todos los campos estén rellenos!");
		}else{
			try{
				FXMLDocumentController registro = new FXMLDocumentController();
			    Sismo sismo = new Sismo();

			    sismo.setFecha(entry_Fecha.getText());
			    sismo.setInstanteOcurrido(entry_Instante.getText());
			    sismo.setProfundidad(entry_Profundidad.getText());
			    sismo.setOrigen(entry_Origen.getText());
			    sismo.setMagnitud(entry_Magnitud.getText());
			    sismo.setLatitud(entry_Latitud.getText());
			    sismo.setLongitud(entry_Longitud.getText());
			    sismo.setDescripcion(entry_Descripcion.getText());
			    sismo.setProvincia(entry_Provincia.getText());

			    double magnitud = Double.parseDouble(sismo.getMagnitud()); // Solo para verificar si se ingresó en tipo de correcto
			    double profundidad = Integer.parseInt(sismo.getProfundidad());//..si no, envia una excepcion
			    double latitud = Double.parseDouble(sismo.getLatitud());
			    double longitud = Double.parseDouble(sismo.getLongitud());

			    if(entry_Provincia.getText().equals("Marítimo")||entry_Provincia.getText().equals("Maritimo")||
			    		entry_Provincia.getText().equals("marítimo")||entry_Provincia.getText().equals("maritimo")){

			    	sismo.setEsMaritimo(true);
			    }

			    FXMLDocumentController.registrarSismo(sismo);

			    sismo.fecha.set(entry_Fecha.getText());
			    sismo.instanteOcurrido.set(entry_Instante.getText());
			    sismo.profundidad.set(entry_Profundidad.getText());
			    sismo.origen.set(entry_Origen.getText());
			    sismo.magnitud.set(entry_Magnitud.getText());
			    sismo.provincia.set(entry_Provincia.getText());
			    sismo.latitud.set(entry_Latitud.getText());
			    sismo.longitud.set(entry_Longitud.getText());
			    sismo.descripcion.set(entry_Descripcion.getText());

			    CSVWriter writer; // Insertar el nuevo sismo en el archivo
				try {
					writer = new CSVWriter(new FileWriter("registroSismos.csv", true));
					String[] entries = (sismo.getFecha()+"#"+sismo.getInstanteOcurrido()+"#"+sismo.getProfundidad()+"#"+
										sismo.getOrigen()+"#"+sismo.getMagnitud()+"#"+sismo.getLatitud()+"#"+sismo.getLongitud()+"#"+
										sismo.getDescripcion()+"#"+sismo.getProvincia()).split("#");

					writer.writeNext(entries);
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

			    FXMLDocumentController.sismos.add(sismo);

			    button_RegistrarSismo.setDisable(true);
			    label_MensajeError.setText("");
			    label_MensajeRegistrado.setText("El sismo ha sido registrado correctamente!");

			    if(FXMLDocumentController.getEstaImportado() != false){ // Al estar importado, actomaticamnete se envia correo
			    	activarServicioNotificacion(sismo.getProvincia(), sismo.getFecha(), sismo.getInstanteOcurrido(), sismo.getMagnitud(), sismo.getDescripcion());
			    }

			}catch(NumberFormatException e){ // En caso de ingresar letras en textField magnitud
	    		//e.printStackTrace();
				label_MensajeError.setText("*Verifique que los campos 'Profundidad', 'Magnitud', 'Latitud' o 'Longitud' contengan solo numeros!");
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * El metodo se encarga del envio de correos electronicos, recibe algunos parametros de un sismo de los cuales son enviados por este medio.
	 * Para el envio del correo se realiza una comparacion de la provincia/zona del sismo registrado con la zona del usuario..
	 * ..de ser la comparacion verdadera se inicializa el metodo "EnviadorMail" con los parametros del sismo para enviar el correo.
	 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
	 * @param pProvincia,  paremetro que envia el metodo registrarSismo cuando se realizan los setters
	 * @param pFecha,  paremetro que envia el metodo registrarSismo cuando se realizan los setters
	 * @param pInstante,  paremetro que envia el metodo registrarSismo cuando se realizan los setters
	 * @param pMagnitud,  paremetro que envia el metodo registrarSismo cuando se realizan los setters
	 * @param pDescripcion,  paremetro que envia el metodo registrarSismo cuando se realizan los setters
	 * @throws IOException, en caso de no ser posible el envio del correo electronico
	 */
	public void activarServicioNotificacion(String pProvincia, String pFecha, String pInstante, String pMagnitud, String pDescripcion) throws IOException{

		CSVReader readerUsuarios = new CSVReader(new FileReader("registroUsuarios.csv"), ',' , '"' , 0);
		String usuario = null;
		String[] nextLineUsuario;
		boolean seEnvio = false;
		int contadorEnviados = 0;

		while((nextLineUsuario = readerUsuarios.readNext()) != null){
	    	if (nextLineUsuario != null) {
	    		usuario = (Arrays.toString(nextLineUsuario));
				String[] arrayUsuario = usuario.split(","); // Permite manejar strings de la forma: "### , ### , ###"

				String correo = arrayUsuario[1];
				String correoB = correo.substring(1, arrayUsuario[1].length());

				String provincia = arrayUsuario[3];
				String provinciaB = provincia.substring(1, arrayUsuario[3].length()-1);

				if(pProvincia.equals(provinciaB) && !correoB.equals("")){
					EnviadorMail EnviadorMail = new EnviadorMail(correoB, "Se ha registrado un nuevo sismo en su zona", "-- "+pProvincia+" --\n"+
							"El sismo sucedio el día: "+pFecha+ " a eso de las: "+pInstante+" con una magnitud de: "+pMagnitud+".\n"+
							pDescripcion);
					seEnvio = true;
					contadorEnviados += 1;

				}if(seEnvio == true){
					label_MensajeCorreoSms.setText("Se ha enviado un correo/sms a "+contadorEnviados+" persona(as)");
				}

	    	 }
	     }
	}
}
