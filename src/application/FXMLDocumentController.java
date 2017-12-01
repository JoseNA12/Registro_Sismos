package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;

import actualizar.FXMLActualizarController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import registro.FXMLRegistroController;
import servicio.Persona;

/**
 * Clase que se encarga de manipular, en gran parte el programa junto con la ventana principal.
 * Tiene como parametros los componentes visuales de la ventana principal.
 * Hereda de la clase de Java "Initializable", con el fin de ser ejecutada
 * @author José Navarro, Hans Fernadez, Fabricio Elizondo
 */
public class FXMLDocumentController implements Initializable { // Control sobre la ventaba principal
	// public: Cuando se registra un sismo se necesitan
	@FXML public TextField TextField_Profundidad; // Cada variable es el ID perteneciente a cada parte de la interfaz
	@FXML public TextField TextField_Origen;
	@FXML public TextField TextField_Hora;
	@FXML public TextField TextField_Latitud;
	@FXML public TextField TextField_Longitud;
	@FXML public TextField TextField_Magnitud;
	@FXML public TextField TextField_Fecha;
	@FXML public TextField TextField_Zona; // Provincia
	@FXML public TextArea text_Descripcion;
	@FXML private Label label_MensajeActualizar;
	@FXML private Button button_Actualizar;
	@FXML private MenuBar menuBarra;
	@FXML private ImageView image_MapaSismo;
	@FXML private Button button_AceptarError; // Indica si el servicio de notificacion está activado
	@FXML public CheckBox check_EsMaritimo;
	@FXML private TableView<Sismo> tableView_Sismos;
	@FXML private TableColumn tableColum_Sismo;
	@FXML private TableColumn tableColum_Fecha;

	public static ObservableList<Sismo> sismos;
	public static ArrayList<Sismo> listaSismos;
	public static ArrayList<Persona> personas;

	public static boolean estaImportado = false; // True: Está importado
	public static int posicionSismoEnTabla;
	private static int totalSismos;

    private Stage stageRegistrarSismo = new Stage();
    private Stage stageAnalisisTabular = new Stage();
    private Stage stageServicioNotificacion = new Stage();
    private Stage stageActualizarInformacion = new Stage();


    // Constructor //
    public FXMLDocumentController(){
    	listaSismos = new ArrayList<Sismo>();
    }

    /**
     * Se encarga de registrar un sismo en el ArrayList "listaSismos", llevando a la vez un contador
     * @param pSismo
     */
    public static void registrarSismo(Sismo pSismo){ // Relacion de agregacion con la clase Sismo
    	listaSismos.add(pSismo);
    	totalSismos += 1;
    }

    /**
     * Método utilizado con el fin de conocer si el servicio de notificación está importado o no.
     * @param pEstaImportado, se recibe como la parametro la respuesta enviada por la clase "FXMLServicioController"
     */
    public static void setEstaImportado(boolean pEstaImportado){
    	estaImportado = pEstaImportado;
    }
    public static boolean getEstaImportado(){
    	return estaImportado;
    }

    /**
     * Método encargado de mostrar la ventana de registrar sismo, siendo leido el archivo ".fxml"
     * @param event, evento asignado al componente visual "MenuBar"
     * @throws IOException, en caso de no ser posible mostrar la ventana
     */
	public void mostrarVentanaRegistrarSismo(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/registro/FXMLRegistrarSismo.fxml"));
		Scene scene = new Scene(parent);

		stageRegistrarSismo.setTitle("Registrar sismo");
		stageRegistrarSismo.setScene(scene);
		stageRegistrarSismo.setResizable(false);
		stageRegistrarSismo.show(); label_MensajeActualizar.setText("");
	}

    /**
     * Método encargado de mostrar la ventana de analisis tabular, siendo leido el archivo ".fxml"
     * @param event, evento asignado al componente visual "MenuBar"
     * @throws IOException, en caso de no ser posible mostrar la ventana
     */
	public void mostrarVentanaAnalisisTabular(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/analisis/FXMLAnalisisTabular.fxml"));
		Scene scene = new Scene(parent);

		stageAnalisisTabular.setTitle("Analisis tabular");
		stageAnalisisTabular.setScene(scene);
		stageAnalisisTabular.setResizable(false);
		stageAnalisisTabular.show(); label_MensajeActualizar.setText("");
	}

    /**
     * Método encargado de mostrar la ventana del servicio de notiticación, siendo leido el archivo ".fxml"
     * @param event, evento asignado al componente visual "MenuBar"
     * @throws IOException, en caso de no ser posible mostrar la ventana
     */
	public void mostrarVentanaServicioNotificacion(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/servicio/FXMLServicioNotificacion.fxml"));
		Scene scene = new Scene(parent);

		stageServicioNotificacion.setTitle("Servicio de notificacion");
		stageServicioNotificacion.setScene(scene);
		stageServicioNotificacion.setResizable(false);
		stageServicioNotificacion.show(); label_MensajeActualizar.setText("");
	}

    /**
     * Método encargado de mostar la ventana de actualizar información dde un sismo, siendo leido el archivo ".fxml"
     * @param event, evento asignado al componente visual "button_Actualizar"
     * @throws IOException, en caso de no ser posible mostrar la ventana
     */
	public void mostrarVentanaActualizarInformacion(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/actualizar/FXMLActualizarDatos.fxml"));
		Scene scene = new Scene(parent);

		if(TextField_Zona.getText().equals("")){
			label_MensajeActualizar.setText("*Selecione un sismo primero!");
		}else{
			label_MensajeActualizar.setText("");
			stageActualizarInformacion.setTitle("Actualizar sismo");
			stageActualizarInformacion.setScene(scene);
			stageActualizarInformacion.setResizable(false);
			stageActualizarInformacion.show();
		}
	}


	/**
	 * Método encargado de manejar la lista sismos, mediante la tabla "selectorTablaSismo" e inicializar..
	 * ..el método "ponerSismoSeleccionado"
	 */
	private final ListChangeListener<Sismo> selectorTablaSismo = new ListChangeListener<Sismo>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Sismo> c) {

                    ponerSismoSeleccionado();
                }
            };

    /**
     * Función encargada de obtener la casilla de la tabla donde es selecionada
     * @return retorna "null" en caso de que la tabla esté vacia
     */
    public Sismo getTablaSismosSelecionados(){
    	if(tableView_Sismos != null){

    		List<Sismo> tabla = tableView_Sismos.getSelectionModel().getSelectedItems();
    		if(tabla.size() == 1){

    			final Sismo competicionSelecionada = tabla.get(0);
    			return competicionSelecionada;
    		}
    	}
    	return null;
    }

    /**
     * Metodo encargado para insertar en los TextFiels la tupla(sismo) que se seleciona mediante la función..
     * .. "getTablaSismosSelecionados".
     * Además, se inicializa el método "InsertarLocalizacionGeografica" con los paremetros definidos cuando..
     * .. se inicializó el objeto sismo
     */
    private void ponerSismoSeleccionado(){
    	final Sismo sismo = getTablaSismosSelecionados();
    	posicionSismoEnTabla = sismos.indexOf(sismo);
    	try{
	    	if(sismo != null){
	    		String escala = null;

	    		double magnitud = Double.parseDouble(sismo.getMagnitud()); // Se convierte a double

	    		if(magnitud >= 2.0 && magnitud <= 6.9){
	    			escala = "Ml";} // Richter
	    		else{
	    			if(magnitud > 6.9){
	    				escala = "Mw";}
	    		}

	    		// Se hacen los .get() de la clase sismo

	    		TextField_Fecha.setText(sismo.getFecha());
	    		TextField_Profundidad.setText(sismo.getProfundidad()+" Km");
	    		TextField_Origen.setText(sismo.getOrigen());
	    		TextField_Hora.setText(sismo.getInstanteOcurrido());
	    		TextField_Latitud.setText(sismo.getLatitud());
	    		TextField_Longitud.setText(sismo.getLongitud());
	    		TextField_Magnitud.setText(sismo.getMagnitud() + " - " + escala);
	    		TextField_Zona.setText(sismo.getProvincia());
	    		text_Descripcion.setText(sismo.getDescripcion());

	    		if(TextField_Zona.getText().equals("Marítimo")||TextField_Zona.getText().equals("Maritimo")||
	    				TextField_Zona.getText().equals("marítimo")||TextField_Zona.getText().equals("maritimo")){

			    	sismo.setEsMaritimo(true);
			    }

	    		check_EsMaritimo.setSelected(sismo.getEsMaritimo());
	    		InsertarLocalizacionGeografica(sismo.getLatitud(),sismo.getLongitud());

	    	}
    	}catch(NumberFormatException e){ // En caso de ingresar letras en textField magnitud
    		e.printStackTrace();
    	}
    }

    //
    /**
     * Método encargado de inicializar las columnas de la tabla con un valor (id) en la celda de la tabla sismos "tableView_Sismos"
     */
    @SuppressWarnings("unchecked")
    private void inicializarTablaSismo(){										// ("atributo del sismo")
    	tableColum_Sismo.setCellValueFactory(new PropertyValueFactory<Sismo, String>("provincia"));
    																			// provincia porque es el atributo que va a almacenar
    	tableColum_Fecha.setCellValueFactory(new PropertyValueFactory<Sismo, String>("fecha"));

    	sismos = FXCollections.observableArrayList();
    	tableView_Sismos.setItems(sismos);
    }

    /**
     * Establece la localizacion geografica de un sismo, conectandose a la vez con el servicio de internet
     * @param latitud, se envia como parametro cuando se registra un sismo
     * @param longitud, se envia como parametro cuando se registra un sismo
     */
	public void InsertarLocalizacionGeografica(String latitud, String longitud){
		String URL;
		String ApiKey = "AIzaSyCoywJRqLQWBit4DNeOoJ1v4w6Wx-Mb0U8";
		URL = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitud + "," + longitud + "&zoom=13&size=284x335&maptype=roadmap&markers=color:red%7Clabel:C%7C" + latitud + "," + longitud + "&key=" + ApiKey;
		Image Imagen = new Image(URL);
		image_MapaSismo.setImage(Imagen);

	}

	/**
	 * Método que cumple la función inicializar la tabla "tableView_Sismos" al inicio del programa.
	 * Siendo leido al archivo "registroSismos.csv" cada vez que se inicie el programa, creando así..
	 * ..cada objeto de tipo sismo que encuentre adjuntandolos a la table "tableView_Sismos" en debida columna.
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) { // Auto-inicializa metodos y atributos de la clase (Primero en ejecutarse)

    	this.inicializarTablaSismo();// Se inicializa la tabla

    	final ObservableList<Sismo> tablaSismoCel = tableView_Sismos.getSelectionModel().getSelectedItems();
    	tablaSismoCel.addListener(selectorTablaSismo); // Seleciona las tuplas de la tabla sismos

    	// Lee el archivo csv de sismos
    	CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("registroSismos.csv"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
        String [] nextLine;
        int contadorSismos = 1;

        try {
			while ((nextLine = reader.readNext()) != null) {
			   /*System.out.println("\n-SISMO: " + contadorSismos);
			   System.out.println(" Fecha: " + nextLine[0] +"\n Instante: "+ nextLine[1] +"\n Profundidad: "+ nextLine[2] +"\n Origen: "+ nextLine[3]
					   + "\n Magnitud: " + nextLine[4] + "\n Latitud: " + nextLine[5] + "\n Longitud: " + nextLine[6] + "\n Descripción: " + nextLine[7]
							   + "\n Provincia: " + nextLine[8]);
*/
			   String pFecha=nextLine[0]; String pInstante=nextLine[1]; String pProfundidad=nextLine[2]; String pOrigen=nextLine[3]; String pMagnitud=nextLine[4];
			   String pLatitud=nextLine[5]; String pLongitud=nextLine[6]; String pDescripcion=nextLine[7]; String pProvincia=nextLine[8];

			   Sismo objetoSismo = new Sismo(); // Se definen los atributos al objeto

			   objetoSismo.setFecha(pFecha);	// *Se envian sin constructor por un tema de insercion a la tabla (genereba problemas y repeticion de codigo)
			   objetoSismo.setInstanteOcurrido(pInstante);
			   objetoSismo.setProfundidad(pProfundidad);
			   objetoSismo.setOrigen(pOrigen);
			   objetoSismo.setMagnitud(pMagnitud);
			   objetoSismo.setLatitud(pLatitud);
			   objetoSismo.setLongitud(pLongitud);
			   objetoSismo.setDescripcion(pDescripcion);
			   objetoSismo.setProvincia(pProvincia);

			   registrarSismo(objetoSismo);

			   contadorSismos += 1;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

    	for(Sismo sismo: listaSismos){ //Inicializa la tabla con los datos del sismo (Provincia)

    		sismo.provincia.set(sismo.getProvincia());
    		// Se estable como atributo provincia porque en el atributo que se quiere mostrar
    		sismo.fecha.set(sismo.getFecha());

    		sismos.add(sismo);
    	}
    }




}
