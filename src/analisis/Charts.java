package analisis;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import application.Sismo;
import application.FXMLDocumentController;

/**
 * @author Hans Fernandez
 * Clase que se encarga de hacer diferentes gráficas para todos los sismos.
 */
public class Charts {

	ArrayList<Sismo> listaSismos; //Todos los sismos registrados en el programa.

	/**
	 * Constructor de la clase Charts.
	 * @param pSismos Los sismos registrados en el programa.
	 */
	Charts(ArrayList<Sismo> pSismos)
	{
		setSismos(pSismos);
	}

	Charts(){

	}

	/**
	 * Crea en pantalla un cuadro en el que aparecen la cantidad de sismos por tipo de origen.
	 * De no tener ningun sismo, crea el cuadro con valores en 0.
	 */
	public void sismosPorTipoDeOrigen()
	{
		int deformacionInterna= 0;
		int subduccion= 0;
		int choquePlacas= 0;
		int tectonicoFallaLocal= 0;
		int intraPlaca= 0;

		for(Sismo sismo: FXMLDocumentController.listaSismos)
		{

			if(sismo.getOrigen().equals("Deformación Interna"))
				deformacionInterna++;
			else
			{
				if(sismo.getOrigen().equals("Subducción"))
					subduccion++;
				else
				{
					if(sismo.getOrigen().equals("Choque de Placas"))
						choquePlacas++;
					else
					{
						if(sismo.getOrigen().equals("Tectónico por Falla Local"))
							tectonicoFallaLocal++;
						else
							intraPlaca++;
					}
				}
			}
		}



		// Fuente de Datos
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Deformación Interna", deformacionInterna);
        data.setValue("Subducción", subduccion);
        data.setValue("Choque de Placas", choquePlacas);
        data.setValue("Tectónico por Falla Local", tectonicoFallaLocal);
        data.setValue("Intra Placa", intraPlaca);


        // Creando el Grafico
        JFreeChart chart = ChartFactory.createPieChart(
         "Sismos por Tipo de Origen",
         data,
         true,
         true,
         false);

        // Mostrar Grafico
        ChartFrame frame = new ChartFrame("Sismos por Tipo de Origen", chart);
        frame.pack();
        frame.setVisible(true);
	}

	/**
	 * Crea en pantalla un cuadro en el que aparecen la cantidad de sismos por provincia.
	 * De no tener ningún sismo, crea el cuadro con todos los valores en 0.
	 */
	public void cantidadDeSismosPorProvincia()
	{
		int provincias[]={0, 0, 0, 0, 0, 0, 0};
		String provinciasStr[]={"San José","Cartago","Alajuela","Heredia","Limón","Puntarenas","Guanacaste"};

		for(Sismo sismo : FXMLDocumentController.listaSismos)
		{
			int indice=0;
			String provincia=sismo.getProvincia();
			while(indice<7)
			{
				if(provincia.equals(provinciasStr[indice])) // .equals
				{
					provincias[indice]++;
					break;
				}
				indice++;
			}
		}

		// Fuente de Datos
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        int indiceData=0;
        while(indiceData<7)
        {
        	data.addValue(provincias[indiceData], "Sismos", provinciasStr[indiceData]);
        	indiceData++;
        }


        // Creando el Grafico
        JFreeChart chart =ChartFactory.createBarChart(
        		"Sismos por Provincia",
        		"Provincia",
        		"Sismos",
        		data,
        		PlotOrientation.VERTICAL,
        		true,
        		true,
        		false);

        // Mostrar Grafico
        ChartFrame frame = new ChartFrame("Sismos por Tipo de Origen", chart);
        frame.pack();
        frame.setVisible(true);
	}

	/**
	 * Crea en pantalla un cuadro en el que aparecen la cantidad de sismos al año que se indica.
	 * De no tener ningún sismo, creal el cuadro con todos los valores en 0.
	 * @param anno Es el año al que se quieren consultar la cantidad de sismos dividido en meses.
	 */
	public void cantidadDeSismosAlAnnoMes(int anno)
	{
		ArrayList<Sismo> sismosAnno= new ArrayList<Sismo>();//Lista de todos los sismos en ese año.
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar= Calendar.getInstance();
		Date fecha = null;
		int meses[]= {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		String mesesStr[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};

		for(Sismo sismo : FXMLDocumentController.listaSismos)
		{
			try {
				fecha = formatoDeFecha.parse(sismo.getFecha());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.setTime(fecha);
			int annoSismo=calendar.get(Calendar.YEAR);
			if(annoSismo==anno)
			{
				sismosAnno.add(sismo);
			}
		}

		for(Sismo sismo : sismosAnno)
		{
			try {
				fecha = formatoDeFecha.parse(sismo.getFecha());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.setTime(fecha);
			int mes= calendar.get(Calendar.MONTH);
			meses[mes]+=1;
		}

		// Fuente de Datos
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        for(int indice=0 ; indice<12 ; indice++)
        {
        	data.addValue(meses[indice], "Sismos", mesesStr[indice]);
        }



        // Creando el Grafico
        JFreeChart chart = ChartFactory.createBarChart3D(
          "Sismos por Año",
          "Mes",
          "Sismos",
          data,
          PlotOrientation.VERTICAL,
          true,
          true,
          false);

        // Mostrar Grafico
        ChartFrame frame = new ChartFrame("Sismos por Año", chart);
        frame.pack();
        frame.setVisible(true);
	}

	/**
	 * Crea en pantalla un cuadro en el que aparecen los sismos entre el rango de fechas indicado.
	 * De no haber ningún sismo, aparece vacío.
	 * @param fechaInicio Fecha inicio del rango de fechas.
	 * @param fechaFin Fecha final del rango de fechas.
	 */
	public void sismosEnRangoDeFechas(Date fechaInicio, Date fechaFin)
	{
		ArrayList<Sismo> sismosEnRango=new ArrayList<Sismo>();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;
		for(Sismo sismo : FXMLDocumentController.listaSismos)
		{
			try {
				fecha = formatoDeFecha.parse(sismo.getFecha());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(pruebaRangoFechas(fecha, fechaInicio, fechaFin))
			{
				sismosEnRango.add(sismo);
			}
		}




		JFrame frame = new JFrame();

	    Object rowData[][] = new Object[sismosEnRango.size()][];
	    Object columnNames[] = { "Fecha", "Hora", "Magnitud", "Profundidad", "Origen", "Provincia", "Latitud", "Longitud" };
	    int indice=0;
	    for(Sismo sismo : sismosEnRango)
	    {
	    	ArrayList<String> dato= new ArrayList<String>();
	    	dato.add(sismo.getFecha());
	    	dato.add(sismo.getInstanteOcurrido());
	    	dato.add(sismo.getMagnitud());
	    	dato.add(sismo.getProfundidad());
	    	dato.add(sismo.getOrigen());
	    	dato.add(sismo.getProvincia());
	    	dato.add(sismo.getLatitud());
	    	dato.add(sismo.getLongitud());
	    	rowData[indice]=dato.toArray();
	    	indice++;
	    }


	    JTable table = new JTable(rowData, columnNames);

	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(600, 300);
	    frame.setVisible(true);
	}

	/**
	 * Crea en pantalla un cuadro en el que aparecen los sismos con el tipo de sismos que son, según su magnitud.
	 */
	public void sismosPorMagnitud()
	{
		JFrame frame = new JFrame();

	    Object rowData[][] =   new Object[(FXMLDocumentController.listaSismos).size()][];
	    Object columnNames[] = { "Fecha", "Magnitud", "Profundidad", "Origen", "Tipo" };
	    int indice=0;
	    for(Sismo sismo : FXMLDocumentController.listaSismos)
	    {
	    	ArrayList<String> dato= new ArrayList<String>();
	    	dato.add(sismo.getFecha());
	    	dato.add(sismo.getMagnitud());
	    	dato.add(sismo.getProfundidad());
	    	dato.add(sismo.getOrigen());
	    	dato.add(tipoMagnitud(Float.parseFloat(sismo.getMagnitud())));
	    	rowData[indice]=dato.toArray();
	    	indice++;
	    }


	    JTable table = new JTable(rowData, columnNames);

	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(600, 300);
	    frame.setVisible(true);
	}


	/**
	 * Método set para el atributo sismos.
	 * @param pSismos Lista que contiene objetos de tipo Sismos.
	 */
	private void setSismos(ArrayList<Sismo> pSismos)
	{
		FXMLDocumentController.listaSismos=pSismos;
	}

	/**
	 * Dice si la fecha está entre el rango de fechas
	 * @param fecha La fecha a evaluar
	 * @param fechaInicio La fecha donde inicia el rango
	 * @param fechaFin La fecha donde termina el rango
	 * @return Retorna un valor booleano, verdadero es que sí está en el rango
	 */
	private boolean pruebaRangoFechas(Date fecha, Date fechaInicio, Date fechaFin)
	{
		return !(fecha.before(fechaInicio) || fecha.after(fechaFin));
	}

	/**
	 * Define el tipo de sismo por su magnitud
	 * @param magnitud Magnitud del sismo
	 * @return Retorna un string con el nombre de la magnitud
	 */
	private String tipoMagnitud(float magnitud)
	{
		if (magnitud < 2.0 )
			return "Micro";
		if (magnitud >= 2.0 & magnitud <= 3.9)
			return "Menor";
		if (magnitud >= 4.0 & magnitud <= 4.9)
			return "Ligero";
		if (magnitud >= 5.0 & magnitud <= 5.9)
			return "Moderado";
		if (magnitud >= 6.0 & magnitud <= 6.9)
			return "Fuerte";
		if (magnitud >= 7.0 & magnitud <= 7.9)
			return "Mayor";
		if (magnitud >= 8.0 & magnitud <= 9.9)
			return "Gran";
		return "Épico";
	}

}
