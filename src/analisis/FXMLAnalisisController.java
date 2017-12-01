package analisis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLAnalisisController {

	@FXML private Button button_porTipoOrigen;
	@FXML private Button button_porProvincica;
	@FXML private Button button_PorMagnitud;
	@FXML private Button button_PorAno;
	@FXML private Button button_RangoFecha;
	@FXML private TextField entry_ano;
	@FXML private TextField entry_FechaUno;
	@FXML private TextField entry_FechaDos;


	public void mostrarPorTipoDeOrigen(ActionEvent event){
		Charts origen = new Charts(FXMLDocumentController.listaSismos);
		origen.sismosPorTipoDeOrigen();
	}

	public void mostrarPorProvincia(ActionEvent event){
		Charts provincia = new Charts(FXMLDocumentController.listaSismos);
		provincia.cantidadDeSismosPorProvincia();
	}

	public void mostrarPorMagnitud(ActionEvent event){
		Charts magnitud = new Charts(FXMLDocumentController.listaSismos);
		magnitud.sismosPorMagnitud();
	}

	public void mostrarPorRangoDeFecha(ActionEvent event){
		String annoUno_entry = entry_FechaUno.getText();
		String annoDos_entry = entry_FechaDos.getText();
		Date annoUno = null;
		Date annoDos = null;

		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");

		try {
			annoUno = formatoDeFecha.parse(annoUno_entry);
			annoDos = formatoDeFecha.parse(annoDos_entry);
		} catch (ParseException e) {
			System.out.println("El formato de fechas es incorrecto!");
		}


		Charts rangoFecha = new Charts(FXMLDocumentController.listaSismos);
		rangoFecha.sismosEnRangoDeFechas(annoUno, annoDos);
	}

	public void mostrarPorAnno(ActionEvent event){
		String anno = entry_ano.getText();
		int annoInt = Integer.parseInt(anno);

		Charts rangoAnno = new Charts(FXMLDocumentController.listaSismos);
		rangoAnno.cantidadDeSismosAlAnnoMes(annoInt);
	}


}
