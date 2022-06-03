package us.muit.fs.a4i.model.entities;

import java.util.Date;

public interface ReportItemI<T> {
	
	
	/**
	 * Consulta el nombre de la m�trica
	 * @return Nombre de la m�trica
	 */
	public String getName();
	
	/**
	 * Consulta el valor de la m�trica
	 * @return Medida
	 */
	public T getValue();
	
	/**
	 * Consulta cuando se obtuvo la m�trica
	 * @return Fecha de consulta de la m�trica
	 */
	public Date getDate();
	
	/**
	 * Obtiene la descripci�n de la m�trica
	 * @return Descripci�n del significado de la m�trica
	 */
	public String getDescription();
	
	/**
	 * Consulta la fuente de informaci�n
	 * @return Origen de la medida
	 */
	public String getSource();
	
	/**
	 * Consulta las unidades de medida
	 * @return la unidad usada en la medida
	 */
	public String getUnit();
	
	// getIndicator no existe, no sabemos si hay que crearla o como
	
	/***
	 * Establece la fuente de la informaci�n para la medida
	 * @param source fuente de informaci�n origen
	 */
	public void setSource(String source);
}