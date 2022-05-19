package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.ControladorMVC;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;


public class MainApp {

	public static void main(String[] args) {
		System.out.println("Programa para la gestión de reservas de espacios del IES Al-Ándalus");

			
		IModelo modelo = procesarArgumentosModelo(args);
		IVista vista = procesarArgumentosVista(args);
		IControlador controlador = new ControladorMVC(modelo, vista);
		controlador.comenzar();
		
		
		 
	}

	private static IModelo procesarArgumentosModelo(String[] args) {
		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vficheros")) {
				modelo = new Modelo( FactoriaFuenteDatos.FICHEROS.crear());
			} else if (argumento.equalsIgnoreCase("-vmemoria")) {
				modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
			}
		}
		return modelo;
	}

	private static IVista procesarArgumentosVista(String[] args) {
		IVista vista = FactoriaVista.GRAFICA.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vgrafica")) {
				vista = FactoriaVista.GRAFICA.crear();
			} else if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();
			}

		}
		return vista;
	}

	


	
}
