package servicio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;

import application.FXMLDocumentController;
import application.Sismo;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 * Clase que se encarga de controlar la ventana del servicio de notificacion, estableciendo como atributos los componentes visuales de ésta.
 *
 */
public class FXMLServicioController {

	private Stage stageVentanaEmergente = new Stage();
	private Stage stageVentanaError = new Stage();

	@FXML private TableView<Persona> tableView_Usuarios;
	@FXML private TableColumn tableColumn_Nombre;
	@FXML private TableColumn tableColumn_Provincia;
	@FXML private TableColumn tableColumn_Telefono;
	@FXML private TableColumn tableColumn_Correo;
	@FXML private Button button_Importar;
	@FXML private Button button_Aceptar;
	@FXML private Label label_MensajeError;

    ObservableList<Persona> personas_Observable;
	//public static ArrayList<Persona> personas;

    private int posicionPersonaEnTabla;


    public FXMLServicioController(){
    	FXMLDocumentController.personas = new ArrayList<Persona>();
    }

    /**
     * La función de escarga de registrar en un Arraylist los objeto de tipo "Persona" de la clase "FXMLDocumentController"..
     * ..que se crean cuando dicho objeto es instanceado
     * @param pPersona, objeto de tipo "Persona"
     */
    public void registrarPersona(Persona pPersona){ // Relacion de agregacion con la clase Persona
    	FXMLDocumentController.personas.add(pPersona);
    }

    /**
     * Hace posible mostrar una ventana emergente con un aviso
     * Se realiza la lectura del archivo "FXMLVentanaEmergente.fxml" con el fin de mostrar la ventana
     * @throws IOException, en caso de no ser posible leer el archivo "FXMLVentanaEmergente.fxml"
     */
	public void mostrarVentanaEmergente() throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/servicio/FXMLVentanaEmergente.fxml"));
		Scene scene = new Scene(parent);

		stageVentanaEmergente.setTitle("Atencion!");
		stageVentanaEmergente.setScene(scene);
		stageVentanaEmergente.setResizable(false);
		stageVentanaEmergente.show();
	}

	/**
	 * Hace posible ocultar la ventana emergente.
	 * @param event, se le asigna al botón "button_Aceptar" con el fin de realizar dicho fin
	 * @throws IOException, en caso de no ser posible cerrar la ventana
	 */
	public void ocultarVentanaEmergente(ActionEvent event) throws IOException{
		Stage stage = (Stage) button_Aceptar.getScene().getWindow();
		stage.hide();
	}

	/**
	 * Se encarga de manipular las posiciones e indices de la tabla "tableView_Usuarios" herendo de la clase "Persona"..
	 * ..los atributos para ser visualizados. Además inicializa el método "ponerUsuarioSeleccionado"
	 */
    private final ListChangeListener<Persona> selectorTablaPersonas = new ListChangeListener<Persona>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Persona> c) {
                	ponerUsuarioSeleccionado();
                }
            };

    /**
     * Encargado de obtener los atributos de la clase "Persona" y mostrarlos en la tabla "tableView_Usuarios"
     * @return, retorna "null" en caso de no encontrar nada en la tabla "tableView_Usuarios"
     */
    public Persona getTablaPersonasSeleccionada() {
        if (tableView_Usuarios != null) {
            List<Persona> tabla = tableView_Usuarios.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Persona competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    /**
     * Cumple la funcion de poner los atributos obtenidos en el metodo "getTablaPersonasSeleccionada"..
     * ..en la posicion correspondiente a su indice asignandosele al "ObservableList"
     */
    private void ponerUsuarioSeleccionado() {
        final Persona persona = getTablaPersonasSeleccionada();
        posicionPersonaEnTabla = personas_Observable.indexOf(persona);

    }

    /**
     * Método que se encarga de inicializar mediante un identificador(value) la tabla "tableView_Usuarios", donde va..
     * ..la informacion correspondiente a cada celda
     */
    @SuppressWarnings("unchecked")
	private void inicializarTablaUsuarios() {
    	tableColumn_Nombre.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
    	tableColumn_Provincia.setCellValueFactory(new PropertyValueFactory<Persona, String>("provincia"));
    	tableColumn_Telefono.setCellValueFactory(new PropertyValueFactory<Persona, String>("telefono"));
    	tableColumn_Correo.setCellValueFactory(new PropertyValueFactory<Persona, String>("correoElectronico"));

    	personas_Observable = FXCollections.observableArrayList();
        tableView_Usuarios.setItems(personas_Observable);
    }

    /**
     * Método encargado de inicializar la función "inicializarTablaUsuarios", además de selecionar mediante una "ObservableList"..
     * ..de tipo "Persona" las tuplas de la tabla "tableView_Usuarios".
     * Se lee además el archivo "registroUsuarios.csv", con el fin instanciar la clase "Persona" con los atributos obtenidos en el..
     * ..parseo de dicho archivo, siendo éste registro en un ArrayList y acumulando un contador-
     * Seguidamente, se recorren los objetos de tipo "Persona con el fin de ser insertados en la lista observable "personas_Observable"
     */
    public void importarUsuarios() {
    	// Inicializamos la tabla
	    this.inicializarTablaUsuarios();

	    button_Importar.setDisable(true);

	    // Seleccionar las tuplas de la tabla de las personas
	    final ObservableList<Persona> tablaPersonaSel = tableView_Usuarios.getSelectionModel().getSelectedItems();
	    tablaPersonaSel.addListener(selectorTablaPersonas);

	    // Lee el archivo csv de los usuarios
		CSVReader reader = null;
		  try {
		    reader = new CSVReader(new FileReader("registroUsuarios.csv"));
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		String [] nextLine;
		int contadorUsuarios = 0;

		try {
			while ((nextLine = reader.readNext()) != null) {
				String pNombre=nextLine[0]; String pCorreo=nextLine[1]; String pTelefono=nextLine[2]; String pProvincia=nextLine[3];
				/*
			  System.out.println("\n-Usuario: " + contadorUsuarios);
				System.out.println(" Nombre: " + nextLine[0] +"\n Correo: "+ nextLine[1] +"\n Telefono: "+ nextLine[2] +"\n Provincia: "+ nextLine[3]);
*/
			    Persona objetoPersona = new Persona();
			    objetoPersona.setNombre(pNombre);
				objetoPersona.setCorreoElectronico(pCorreo);
				objetoPersona.setTelefono(pTelefono);
				objetoPersona.setProvincia(pProvincia);

				registrarPersona(objetoPersona);
				contadorUsuarios += 1;

		    }
		}catch (IOException e) {

			e.printStackTrace();
		}

	    // Inicializamos la tabla con algunos datos aleatorios
	    for(Persona usuario: FXMLDocumentController.personas){ //Inicializa la tabla con los datos del sismo (Provincia)

	        usuario.nombre.set(usuario.getNombre());
	        usuario.provincia.set(usuario.getProvincia());
	        usuario.telefono.set(usuario.getTelefono());
	        usuario.correoElectronico.set(usuario.getCorreoElectronico());

	        personas_Observable.add(usuario);

	    }
	       try {
	    	   if(FXMLDocumentController.getEstaImportado() != true){
	    		   mostrarVentanaEmergente();
	    	   }else{
	    		   label_MensajeError.setText("El servicio ya ha sido importado!");
	    	   }
			} catch (IOException e) {
				e.printStackTrace();
			}
	       FXMLDocumentController.setEstaImportado(true);
	 }


}
