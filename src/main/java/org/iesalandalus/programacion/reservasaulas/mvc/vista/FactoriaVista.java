package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {

	TEXTO {

		@Override
		public IVista crear() {
			return new VistaTexto();
		}

	},

	GRAFICA {

		@Override
		public IVista crear() {
			return new VistaGrafica();
		}

	};

	public abstract IVista crear();

}
