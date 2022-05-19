
package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.FactoriaFuenteDatosMemoria;

/**
 * @author laura
 *
 */
public enum FactoriaFuenteDatos {

	MEMORIA(){
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMemoria();
		}
	},
	
	FICHEROS(){
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};
	

	public abstract IFuenteDatos crear();
	
}
