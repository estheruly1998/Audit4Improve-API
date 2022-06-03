/**
 * 
 */
package us.muit.fs.a4i.model.entities;

import java.io.IOException;
import java.time.LocalDateTime;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Collection;

import us.muit.fs.a4i.config.Context;
import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.model.entities.Indicator.IndicatorBuilder;

/**
 * @author Isabel Román
 *
 */
public class ReportItem<T> {
	private Indicator<T> indicator = null;
	private static Logger log=Logger.getLogger(ReportItem.class.getName());
	/**
	 * Obligatorio
	 */
	private String name;
	/**
	 * Obligatorio
	 */
	private T value;
	/**
	 * Obligatorio
	 * Fecha en la que se tomó la medida (por defecto cuando se crea el objeto)
	 */
	private Date date;
	
	private String description;
	private String source;
	private String unit;

	/**
	 * Construye un objeto ReportItem a partir de un constructor, previamente configurado
	 * Sólo lo utiliza el propio constructor, es privado, nadie, que no sea el constructor, puede crear una ReportItem
	 * @param builder Constructor del ReportItem
	 */
	private ReportItem(ReportItemBuilder<T> builder){
		
		this.description=builder.description;
		this.name=builder.name;
		this.value=builder.value;
		this.source=builder.source;
		this.unit=builder.unit;
		this.date=builder.date;
	}
	
	/**
	 * Obtiene la descripción del ReportItem
	 * @return Descripción del significado del ReportItem
	 */
	public String getDescription() {
		return description;
	}
	
	
	/**
	 * Consulta el nombre del ReportItem
	 * @return Nombre del ReportItem
	 */
	public String getName() {
		return name;
	}
	/**
	 * Consulta el valor del ReportItem
	 * @return ReportItem
	 */
	public T getValue() {
		return value;
	}
	/**
	 * Consulta la fuente de información
	 * @return Origen del ReportItem
	 */
	public String getSource() {
		return source;
	}
	/***
	 * Establece la fuente de la información para el ReportItem
	 * @param source fuente de información origen
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * Consulta las unidades del ReportItem
	 * @return la unidad usada en el ReportItem
	 */
	public String getUnit() {
		return unit;
	}


	/**
	 * Consulta el indicador de ReportItem
	 * @return indicador de ReportItem
	 */
	public Indicator<T> getIndicator() {
		return indicator;
	}

	
	/**
	 * Consulta cuando se obtuvo el ReportItem
	 * @return Fecha de consulta del ReportItem
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * <p>Clase para construir ReportItem. Verifica los ReportItem antes de crearlos</p>
	 *
	 */
	public static class ReportItemBuilder<T>{
		private String description;
		private String name;
		private Date date;
		private T value;
		private String source;
		private String unit;
		private Collection<ReportItem<T>> metrics;
		private ReportItem<T> metric;
		private IndicatorBuilder<T> state;
		public ReportItemBuilder(String reportItemName, T reportItemValue) throws MetricException {
			HashMap<String,String> reportItemDefinition=null;
			//el nombre incluye java.lang, si puede eliminar si se manipula la cadena
			//hay que quedarse sólo con lo que va detrás del último punto o meter en el fichero el nombre completo
			//Pero ¿y si se usan tipos definidos en otras librerías? usar el nombre completo "desambigua" mejor
			log.info("Verifico el ReportItem de nombre "+reportItemName+" con valor de tipo "+reportItemValue.getClass().getName());
			try {
			reportItemDefinition=Context.getContext().getChecker().definedReportItem(reportItemName,reportItemValue.getClass().getName());
					
			if(reportItemDefinition!=null) {				
				this.name=reportItemName;
				this.value=reportItemValue;			
				this.date=Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
				this.description=reportItemDefinition.get("description");
				this.unit=reportItemDefinition.get("unit");
			}else {
				throw new MetricException("M�trica "+reportItemName+" no definida o tipo "+reportItemValue.getClass().getName()+" incorrecto");
			}
			}catch(IOException e) {
				throw new MetricException("El fichero de configuraci�n de ReportItem no se puede abrir");
			}
			
			
		}
		/**
		 * <p>Establece la descripción del ReportItem</p>
		 * @param description Breve descripción del significado del ReportItem
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> description(String description){
			this.description=description;
			return this;
		}


		/**
		 * <p>Establece la fecha del ReportItem</p>
		 * @param date Fecha del ReportItem
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> date(Date date){
			this.date=date;
			return this;
		}


		/**
		 * <p>Establece el estado del ReportItem</p>
		 * @param state Estado del ReportItem
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> state(IndicatorBuilder<T> state){
			this.state=state;
			return this;
		}


		/**
		 * <p>Establece la métrica del ReportItem</p>
		 * @param metric Métrica del ReportItem
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> metric(ReportItem<T> metric){
			this.metric=metric;
			return this;
		}


		/**
		 * <p>Establece la fuente de información</p>
		 * @param source Fuente de la que se extrajeron los datos
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> source(String source){
			this.source=source;
			return this;
		}
		/**
		 * <p>Establece las unidades de medida</p>
		 * @param unit Unidades de medida del ReportItem
		 * @return El propio constructor
		 */
		public ReportItemBuilder<T> unit(String unit){
			this.unit=unit;
			return this;
		}
		public ReportItem<T> build(){
			return new ReportItem<T>(this);			
		}
	}
	
	@Override
	public String toString() {
		String info;
		info="ReportItem para "+description+", con valor=" + value + ", source=" + source
				+ ", unit=" + unit +" fecha de la medida=  "+ date;
		return info;
	}
	
}
