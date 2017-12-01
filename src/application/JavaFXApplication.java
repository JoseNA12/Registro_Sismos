package application;

import application.FXMLDocumentController.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * Clase encargada de inicializar el programa.
 * Se definen como atributos de clase los componentes principales de la ventana
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 *
 */
public class JavaFXApplication extends Application {

	private Stage primaryStage;
	private AnchorPane mainLayout;


	/**
	 * Método que inicializa la ventana principal.
	 * Además, llama la función "mostrarVentanaPrincipal()"
	 */
	@Override
	public void start(Stage pPrimaryStage) throws IOException {
		this.primaryStage = pPrimaryStage;
		this.primaryStage.setTitle("Proyecto programado 1");
		this.primaryStage.setResizable(false);

		mostrarVentanaPrincipal();

	}

	/**
	 * Se establen las propiedades de la ventana principal.
	 * Primeramente, lee el archivo ".fxml", para luego establecer las propiedades de dicha ventana..
	 * ..y ser ejecutada.
	 * @throws IOException, en caso de no ser posible abrir la ventana principal
	 */
	private void mostrarVentanaPrincipal() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(JavaFXApplication.class.getResource("/application/FXMLDocument.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	public static void main(String[] args) {
		launch(args);
	}

}